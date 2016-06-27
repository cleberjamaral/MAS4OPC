/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato constroi a interface entre o sistema multiagente e o
 * operador humano. Ap��s o SMA ser instanciado e a infraestrutura criada a interface
 * apresenta uma lista de produtos cadastrados, nesta lista o operador seleciona
 * os tipos a serem produzidos, envia para o agente planner que ordena segundo crit��rios 
 * escolhidos pelo usu��rio (tempo de produ����o do lote, tamanho do lote, similariedades entre 
 * os produtos). Com a lista ordenada o agente planner envia o primeiro a ser produzido para
 * o agente configurator preparar a linha de produ����o que envia mensagem para a interface 
 * informando que est�� pronto para produzir o lote X. O operador pode ent��o iniciar a produ����o, 
 * no final do lote, as estat��sticas s��o apresentadas e a����es de melhoria podem ser executadas.
 * O pr��ximo lote (Y) pode ser produzido, e assim at�� o final da lista de produ����o.
 *   
 * 2013-05-06 - MAS initial infrastructure for SSP 
 * 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

import com.summit.camel.opc.Opcda2Component;
import camelartifact.ArtifactComponent;
import camelartifact.CamelArtifact;
import cartago.ARTIFACT_INFO;
import cartago.OUTPORT;

/**
 * Artifact out ports
 */
@ARTIFACT_INFO(outports = { @OUTPORT(name = "out-1"), @OUTPORT(name = "in-1") })
/**
 * This artifact should be linked with many other artifacts making their comunication using camel possible. So this
 * interface is setting the camel route and has linkedoperations
 */
public class Interface extends CamelArtifact {

	/**
	 * TODO Cleber: Give a way to set this parameters out from this app These parameters should be accessible in a
	 * easier form, this approach via code is not very interesting for future maintenance
	 */
	static String domain = "localhost";
	static String user = "cleber";
	static String password = "MAS4opc2016";
	static String clsid = "f8582cf2-88fb-11d0-b850-00c0f0104305";
	static String host = "192.168.0.107";

	void init() {
		// TODO Cleber: Remove webservices call, now initialization must be kept
		// to avoid problems in other classes
		System.out.println("artifacts.interface Inicia a conexao...");
/*
		final CamelContext camel = new DefaultCamelContext();
		// This simple application has only one component receiving messages from the route and producing operations
		camel.addComponent("artifact", new ArtifactComponent(this.getIncomingOpQueue(), this.getOutgoingOpQueue()));
		camel.addComponent("opcda2", new Opcda2Component());
*/
		/**
		 * Creating routes, in this project we have information generated by artifacts that should update OPC tags
		 * (devices) and in the other hand, OPC tags are changing all the time probably, so it must update the
		 * informations that the artifacts have about the real world. After that we start camel.
		 * 
		 * TODO Cleber: Create the route from artifacts to opcda
		 */
		/*

		try {
			camel.addRoutes(new RouteBuilder() {
				@Override
				public void configure() {
					String uriString = "opcda2:Matrikon.OPC.Simulation.1?delay=1000&host=" + host + "&clsId=" + clsid
							+ "&username=" + user + "&password=" + password + "&domain=" + domain;
					from(uriString).process(new Processor() {
						@SuppressWarnings("unchecked")
						public void process(Exchange exchange) throws Exception {
							System.out.println("Processing new messages...");

							Map<String, Map<String, Object>> receivedData = new HashMap<String, Map<String, Object>>();
							receivedData = exchange.getIn().getBody(Map.class);

							Map<String, Object> throwData = new HashMap<String, Object>();
							for (String tagName : receivedData.keySet()) {
								// Just testing, I am filtering to have only two
								// tags Here I think we gotta have some known
								// messages, or I can just give to the artifacts
								// everything I got from the route and they do
								// the filtering process
								if (tagName.equals("Bucket Brigade.String"))
									throwData.put("writeinputAr", "tst");
							}

							exchange.getIn().setHeader("ArtifactName", "assembler");
							exchange.getIn().setHeader("OperationName", "writeinputAr");
							exchange.getIn().setBody(throwData);

						}
					}).to("artifact:cartago");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
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
*/
	}
}
