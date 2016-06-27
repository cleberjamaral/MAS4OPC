/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato modela as caracter��sticas comuns de todos os equipamentos
 * na linha de produ����o. Foi constru��do com o prop��sito de utilizar bem as caracter��sticas
 * de orienta����o a objetos que modela os artefatos e demais recursos do ambiente.
 *   
 * 2013-05-07 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.machines;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import com.summit.camel.opc.Opcda2Component;

import camelartifact.ArtifactComponent;
import camelartifact.CamelArtifact;
import resources.*;
import cartago.*;

@ARTIFACT_INFO(outports = { @OUTPORT(name = "out-1"), @OUTPORT(name = "in-1") })
public class Machine extends CamelArtifact {

	static boolean verboseDebug = true;

	final CamelContext camel = new DefaultCamelContext();
	static String domain = "localhost";
	static String user = "cleber";
	static String password = "MAS4opc2016";
	static String clsid = "f8582cf2-88fb-11d0-b850-00c0f0104305";
	static String host = "192.168.0.107";

	public enum Status {
		STOPPED, IDLE, WAIT, LOADED, READY, PAUSE, DEFECT
	}

	Status status;
	Status lastStatus;
	int lastReadStatus;
	boolean operating = false;
	boolean pauseFlag = false;
	boolean stopFlag = false;
	boolean loadEnabled = false;
	boolean loaded = false;
	boolean w_done = false;
	boolean s_done = false;
	int batchSize = 0;
	int id_prod = 0;
	Product currentProduct;

	String nome;

	void init() {

	}

	@OPERATION
	void createRoutes()
	{
		//TODO: Cleber fix it: sendMsgToGUI(status);

		// This simple application has only one component receiving messages from the route and producing operations
		camel.addComponent("artifact", new ArtifactComponent(this.getIncomingOpQueue(), this.getOutgoingOpQueue()));
		camel.addComponent("opcda2", new Opcda2Component());

		/* Create the routes */
		try {
			camel.addRoutes(new RouteBuilder() {
				@Override
				public void configure() {

					log("Receiving opc messages...");
					/**
					 * The Matrikon simulation server is sending a unique message continuously without any asking
					 * proccess Bellow this project is testing camel artifact producer with this feature of matrikon
					 * server
					 * 
					 * Using .to("log:CamelArtifactLoggerIn?level=info") I had following response:
					 * 
					 * 5757 [Camel (camel-1) thread #0 - opcda2://Matrikon.OPC.Simulation.1] INFO CamelArtifactLoggerIn
					 * - Exchange[ExchangePattern: InOnly, BodyType: java.util.TreeMap, Body:
					 * {#MonitorACLFile={value=true}, @Clients={value=[Ljava.lang.String;@68662676}, Bucket
					 * Brigade.ArrayOfReal8={value=[Ljava.lang.Double;@a674286}, Bucket
					 * Brigade.ArrayOfString={value=[Ljava.lang.String;@14070c0}, Bucket Brigade.Boolean={value=false},
					 * Bucket Brigade.Int1={value=1}, Bucket Brigade.Int2={value=2}, Bucket Brigade.Int4={value=4}, ...
					 * Write Only.UInt4={value=org.jinterop.dcom.core.VariantBody$EMPTY@1d3d888e}}]
					 */

					String uriString = "opcda2:Matrikon.OPC.Simulation.1?delay=2000&host=" + host + "&clsId=" + clsid
							+ "&username=" + user + "&password=" + password + "&domain=" + domain + "&diffOnly=false";
					from(uriString).process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							//This time it is fixed, when we have different tags for each machine a logic must test the origin and 
							//put the appropriated artifact name
							exchange.getIn().setHeader("ArtifactName", "L1"); 
							exchange.getIn().setHeader("OperationName", "updateTagStatus");
							Map<String, Map<String, Object>> body = exchange.getIn().getBody(Map.class);
							List<Object> listObj = new ArrayList<Object>();
							for (String tagName : body.keySet()) {
								Object value = body.get(tagName).get("value");
								log("Tag received: " + tagName + " = " + value.toString());
								/**
								 * For this test we are looking for Bucket Brigade.Int1 tag. It is simulating a
								 * receiving process of a tag, so this tagname and tagvalue are being added in the
								 * object list to be processed be producer
								 */
								if (tagName.equals("Bucket Brigade.Int1")) {
									log("Adding tag" + tagName + " = " + value.toString() + " in the queue");
									listObj.add("Bucket Brigade.Int1");
									listObj.add(value);
								}
							}
							exchange.getIn().setBody(listObj);
						}
					}).to("artifact:cartago");// to("log:CamelArtifactLoggerIn?level=info");

					from("artifact:cartago").process(new Processor() {
						public void process(Exchange exchange) throws Exception {

							log("Processing sending msgs...");

							// The expected format is something like: "Bucket Brigade.Int1={value=1}"
							Map<String, Map<String, Object>> data = new TreeMap<String, Map<String, Object>>();
							Map<String, Object> dataItems = new TreeMap<String, Object>();

							List<Object> params = exchange.getIn().getBody(List.class);
							dataItems.put("value", params.get(1));
							data.put(params.get(0).toString(), dataItems);

							exchange.getIn().setBody(data);

						}
					}).to(uriString).to("log:CamelArtifactLoggerOut?level=info");

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@OPERATION
	void startCamel()
	{
		// Start camel routing. The route should be running since the agent
		// configurator should instantiate this artifact "Interface". So it is
		// not necessary to have any extra thread, this artifact should be
		// maintained alive by JaCaMo.
		System.out.println("Starting camel...");
		try {
			camel.start();
			// this.setListenCamelRoute(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Ready!");
	}
	
	/**
	 * Criei esse metodo para nomear os artefatos para que eu saiba qual o campo do SCADA cada um altera.
	 */
	@OPERATION
	void naming(String name) {
		System.out.println("Machine '" + name + "' is going to be named and put on STOPPED status...");
		this.nome = name;

		/**
		 * Nesse caso espero a flag de stopped done. Fico lendo o valor que esta no status do sensor, se for stopped ele
		 * seta a flag, senao espera 1 segundo e tenta novamente.
		 * 
		 * Vale resaltar q ele so vai criar a proxima machine quando esse metodo for terminado, assim a sequencia esta,
		 * loader - PP1 - PaP1 .... mesmo que a outra esteja em sttoped ela nao vai ser analisada.
		 * 
		 * TODO Cleber: Check with Roloff where exactly is the environment definition and even workspaces This
		 * information is also useful for definition of contextpath of camel, where the first idea is to have
		 * ://workspace/artifact?operations
		 * 
		 */
		while (!this.s_done) {
			String response = readTag(this.nome);
			if (verboseDebug) {
				System.out.println("Machine: Response on naming: " + response + " (name: " + this.nome + ")");
				if (response == null)
					response = "STOPPED";
			}

			if (response.equals("STOPPED")) {
				this.status = Status.STOPPED;
				this.s_done = true;
				//TODO: Cleber fix it: sendMsgToGUI(status);
			} else {
				await_time(1000);
			}
		}
		this.s_done = false;

		//defineObsProperty("status", "STOPPED");
		System.out.println("Machine: Machine '" + name + "' was named successfully!");
	}

	@OPERATION
	void setup(int _batchSize, int idp) {
		ObsProperty stats = getObsProperty("status");
		await_time(1000);
		batchSize = _batchSize;
		id_prod = idp;

		/* Mesmo caso do Stopped */
		while (!this.w_done) {
			String response = readTag(this.nome);
			if (verboseDebug) {
				System.out.println("Machine: Response on setup: " + response + " (name: " + this.nome + ")");
				if (response == null)
					response = "STOPPED";
			}

			if (response.equals("IDLE")) {
				this.status = Status.IDLE;
				stats.updateValue("IDLE");

				this.w_done = true;
				//TODO: Cleber fix it: sendMsgToGUI(status);
			} else {
				await_time(1000);
			}
		}
		this.w_done = false;
	}

	@OPERATION
	void run() {
		log("run invoked");
		ObsProperty stats = getObsProperty("status");

		if (this.status == Status.IDLE) {
			this.status = Status.WAIT;

			writeTag(this.nome, "WAIT");

			stats.updateValue("WAIT");
			operating = true;
			execInternalOp("stateMachine");
			//TODO: Cleber fix it: sendMsgToGUI(status);

		} else if (status == Status.PAUSE) {
			this.status = lastStatus;

			writeTag(this.nome, "PAUSE");

			stats.updateValue(lastStatus.toString());
			//TODO: Cleber fix it: sendMsgToGUI(status);
		} else {
			await_time(1000);
		}
	}

	@OPERATION
	void stop() {
		stopFlag = true;
	}

	@OPERATION
	void pause() {
		pauseFlag = true;
	}

	@INTERNAL_OPERATION
	void stateMachine() {
		int op = 0;
		while (operating) {
			if (stopFlag) {
				log("op=" + op + " STOPPING!!!!");
				stopOp();
			} else if (pauseFlag) {
				log("op=" + op + " PAUSING!!!!");
				pauseOp();
			}

			/**
			 * Aqui poderia ser colocado uma leitura o campo, porem acho que criaria confurao, pois nao sei de qual
			 * estado a maquina acabou de sair.
			 */
			if (status == Status.PAUSE) {
				await("returnFromPause");
			} else if (status == Status.WAIT) {
				log("op=" + op + " status=WAIT");
				load();
			} else if (status == Status.LOADED) {
				log("op=" + op + " status=LOADED");
				operate();
			} else if (status == Status.READY) {
				log("op=" + op + " status=READY");
				unload();
			}
			op++;
			//TODO: Cleber fix it: sendMsgToGUI(status);
		}
	}

	@GUARD
	boolean readyToLoad() {
		return loadEnabled;
	}

	@GUARD
	boolean notReadyToLoad() {
		return !loadEnabled;
	}

	@GUARD
	boolean isUnloaded() {
		return !loaded;
	}

	@GUARD
	boolean returnFromPause() {
		return status != Status.PAUSE | stopFlag;
	}

	@LINK(guard = "notReadyToLoad")
	void enableLoad() {
		log("    Allowed to load");
		loadEnabled = true;
	}

	@LINK
	void getProduct(OpFeedbackParam<Product> v) {
		log("getProduct invoked");
		v.set(currentProduct);
		loaded = false;
	}

	void load() {
		await("readyToLoad");
		ObsProperty stats = getObsProperty("status");
		try {
			OpFeedbackParam<Product> v = new OpFeedbackParam<Product>();
			execLinkedOp("in-1", "getProduct", v);
			loadEnabled = false;
			currentProduct = v.get();
			loaded = true;
			status = Status.LOADED;

			writeTag(this.nome, "LOADED");

			await_time(1000);

			stats.updateValue("LOADED");
		} catch (Exception ex) {
			log("{operate}@ call link unload");
		}
		//TODO: Cleber fix it: sendMsgToGUI(status);
	}

	void unload() {
		log("unload invoked");
		ObsProperty stats = getObsProperty("status");
		try {
			execLinkedOp("out-1", "enableLoad");
			await("isUnloaded");
			status = Status.WAIT;

			writeTag(this.nome, "WAIT");

			await_time(1000);

			stats.updateValue("WAIT");
			log("status after unload= " + status.toString());
		} catch (Exception ex) {
			log("{operate}@ call link enableLoad");
		}
		//TODO: Cleber fix it: sendMsgToGUI(status);
	}

	void operate() {
		ObsProperty stats = getObsProperty("status");
		currentProduct.operate("Op " + this.getClass().getName() + ": Ok");
		status = Status.READY;

		writeTag(this.nome, "READY");

		await_time(1000);

		stats.updateValue("READY");
		signal("opDone");
		//TODO: Cleber fix it: sendMsgToGUI(status);
	}

	void pauseOp() {
		pauseFlag = false;
		log("actual status= " + status.toString());
		ObsProperty stats = getObsProperty("status");
		lastStatus = status;
		status = Status.PAUSE;

		writeTag(this.nome, "PAUSE");

		stats.updateValue("PAUSE");

		//TODO: Cleber fix it: sendMsgToGUI(status);
	}

	void stopOp() {
		stopFlag = false;
		ObsProperty stats = getObsProperty("status");
		operating = false;
		status = Status.STOPPED;

		writeTag(this.nome, "STOPPED");

		stats.updateValue("STOPPED");

		//TODO: Cleber fix it: sendMsgToGUI(status);
	}

	void sendMsgToGUI(Status status) {
		try {
			// if( args.length != 2 )
			// {
			// System.out.println("Passagem de argumentos para o servidor java <IP_host> <quantidade>");
			// return;
			// }

			MessageToGUI ms = new MessageToGUI();
			switch (status) {
			case STOPPED:
				ms.status = 0;
				break;
			case IDLE:
				ms.status = 1;
				break;
			case WAIT:
				ms.status = 2;
				break;
			case LOADED:
				ms.status = 3;
				break;
			case READY:
				ms.status = 4;
				break;
			case PAUSE:
				ms.status = 5;
				break;
			case DEFECT:
				ms.status = 6;
				break;

			}

			if (this instanceof Loader) {
				ms.maquina = 1;
			} else if (this instanceof PastePrinter) {
				ms.maquina = 2;
			} else if (this instanceof PickAndPlaceStation) {
				ms.maquina = 3;
			} else if (this instanceof ReflowOven) {
				ms.maquina = 4;
			} else if (this instanceof Unloader) {
				ms.maquina = 5;
			} else if (this instanceof VisionSystem) {
				ms.maquina = 6;
			}

			System.out.println("Machine:sendMsgToGUI: Open socket and stream created!");
			String host = "localhost";// args[0];
			Socket socket = new Socket(host, 250);

			DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
			String mensagem = ms.maquina + " " + ms.status;
			ostream.writeUTF(mensagem);
			// ObjectOutputStream oos = new ObjectOutputStream(ostream);
			// oos.writeObject(ms);
			ostream.flush();
			ostream.close();
			// oos.flush();
			// oos.close();
			socket.close();

			// Thread.sleep(2000);

		} catch (Exception e) {
			System.err.println("Machine: sendMsgToGUI Error! " + e);
		}
	}

	@LINK
	void writeinput(String v) {
		log("writeinput invoked");
		System.out.println("Link is working!!!! Received: " + v);
	}

	@LINK
	void inc1() {
		System.out.println("inc1-1 invoked.");
	}

	/* Escreve na tag de status */
	public void writeTag(String name, String estatus) {

		String tag = "";

		/* De acordo com o nome do artefato, seleciono a TAG do scada */
		if (name.equals("L1"))
			tag = "Bucket Brigade.Int1";
		else if (name.equals("PP1"))
			tag = "Bucket Brigade.Int2";
		else if (name.equals("PaP1"))
			tag = "Bucket Brigade.Int4";
		else if (name.equals("U1"))
			tag = "Bucket Brigade.Int4";
		else if (name.equals("VS1"))
			tag = "Bucket Brigade.Int4";
		else if (name.equals("RO1"))
			tag = "Bucket Brigade.Int4";

		Object Value = null;

		/* Trocar status para numero referente */
		if (estatus.equals("STOPPED"))
			Value = 1;
		else if (estatus.equals("IDLE"))
			Value = 2;
		else if (estatus.equals("WAIT"))
			Value = 3;
		else if (estatus.equals("LOADED"))
			Value = 4;
		else if (estatus.equals("READY"))
			Value = 5;
		else if (estatus.equals("PAUSE"))
			Value = 6; // Caso alguma falha ocorra em outra maquina, a maquina observada passa para um estado de PAUSA

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(tag);
			params.add(Value);
			sendMsg(this.nome, "", params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String readTag(String tagName) {
		String resposta = "";
		if (lastReadStatus == 2)
			resposta = "IDLE";
		else if (lastReadStatus == 3)
			resposta = "WAIT";
		else if (lastReadStatus == 4)
			resposta = "LOADED";
		else if (lastReadStatus == 5)
			resposta = "READY";
		else if (lastReadStatus == 6)
			resposta = "PAUSED";
		else
			resposta = "STOPPED";
			
		log("Tag " + tagName + "status read as: " + resposta);
		return resposta;
	}

	@INTERNAL_OPERATION
	void updateTagStatus(String tagName, int newStatus) {
		//tagName is not trully used at this time
		lastReadStatus = newStatus;
	}

}
