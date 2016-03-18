/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato modela as características comuns de todos os equipamentos
 * na linha de produção. Foi construído com o propósito de utilizar bem as características
 * de orientação a objetos que modela os artefatos e demais recursos do ambiente.
 *   
 * 2013-05-07 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.machines;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Stack;

import resources.*;
import cartago.*;

@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
}
)public class Machine extends Artifact {
	
	static boolean verboseDebug = false; 

	public enum Status {
		STOPPED, IDLE, WAIT, LOADED, READY, PAUSE, DEFECT
	}
	
	Status status;	
	Status lastStatus;
	boolean operating=false;
	boolean pauseFlag=false;
	boolean stopFlag=false;
	boolean loadEnabled=false;
	boolean loaded=false;
	boolean w_done=false;
	boolean s_done=false;
	int batchSize=0;
	int id_prod=0;
	Product currentProduct;
	
	String nome;
	
	void init() {
		//status=Status.STOPPED;
		//defineObsProperty("status", "STOPPED");
		//chamadaEscrita.writeTag(this.nome, "STOPPED");
		sendMsgToGUI(status);
	}
	
/**
 * Criei esse metodo para nomear os artefatos para que eu saiba qual o campo do SCADA cada um altera.
 */	
	@OPERATION
	void naming (String name){
		this.nome = name;
		System.out.println("Machine: Machine '" +name+"' was named successfully!");

/** 
 * Nesse caso espero a flag de stopped done. Fico lendo o valor que esta no status do sensor, se for stopped ele seta a flag, senao espera 1 segundo e tenta novamente.
 * 
 * Vale resaltar q ele so vai criar a proxima machine quando esse metodo for terminado, assim a sequencia esta, loader - PP1 - PaP1 ....
 * mesmo que a outra esteja em sttoped ela nao vai ser analisada.
 * 
 * TODO Cleber: Check with Roloff where exactly is the environment definition and even workspaces
 * This information is also useful for definition of contextpath of camel, where the first idea is
 * to have ://workspace/artifact?operations
 *  
 */
		while(!this.s_done)
		{
			String response = WebService.readTag(this.nome);
			if (verboseDebug) System.out.println("Machine: Response from stopped done: " +response+ " (name: "+this.nome+")");
			
			if(response.equals("STOPPED"))
			{
				this.status=Status.STOPPED;
				this.s_done=true;
				sendMsgToGUI(status);
			}			
			else{
				await_time(1000);
			}
		}
		this.s_done = false;
		
		defineObsProperty("status", "STOPPED");
		
		//sendMsgToGUI(status);
	}
	
	@OPERATION
	void setup(int _batchSize,int idp){
		ObsProperty stats = getObsProperty("status");
		await_time(1000);
		batchSize = _batchSize;
		id_prod = idp;

		/*Mesmo caso do Stopped*/
		while(!this.w_done)
		{
			String response = WebService.readTag(this.nome);
			if (verboseDebug) System.out.println("Machine: Response from stopped: " +response+ " (name: "+this.nome+")");
			
			if(response.equals("IDLE"))
			{
				this.status=Status.IDLE;
				stats.updateValue("IDLE");	
			
				this.w_done=true;
				sendMsgToGUI(status);
			}
			else{
				await_time(1000);
			}
		}
		this.w_done = false;
		//sendMsgToGUI(status);	
	}
	
	@OPERATION
	void run(){
		log("run invoked");
		ObsProperty stats = getObsProperty("status");		

		if(this.status==Status.IDLE){
			this.status=Status.WAIT;
			
			//TODO: Cleber: change to camel
			WebService.writeTag(this.nome, "WAIT");

			stats.updateValue("WAIT");
			operating=true;
			execInternalOp("stateMachine");
			sendMsgToGUI(status);
			
		}else if(status==Status.PAUSE){
			this.status=lastStatus;
			
			//TODO: Cleber: change to camel
			WebService.writeTag(this.nome, "PAUSE");
			
			stats.updateValue(lastStatus.toString());
			sendMsgToGUI(status);
		}
		else {
			await_time(1000);
		}
		//sendMsgToGUI(status);
	}
	
	@OPERATION
	void stop(){
		stopFlag=true;
	}
	
	@OPERATION
	void pause(){
		pauseFlag=true;
	}
	
	@INTERNAL_OPERATION 
	void stateMachine(){
		int op=0;
		while (operating){
			if(stopFlag){
				log("op="+op+" STOPPING!!!!");
				stopOp();
			}else if(pauseFlag){
				log("op="+op+" PAUSING!!!!");
				pauseOp();
			}

			/**
			 * Aqui poderia ser colocado uma leitura o campo, porem acho que criaria confurao, 
			 * pois nao sei de qual estado a maquina acabou de sair.
			 */
			if(status==Status.PAUSE){
				await("returnFromPause");
			}else if(status==Status.WAIT){
				log("op="+op+" status=WAIT");
				load();
			}else if(status==Status.LOADED){
				log("op="+op+" status=LOADED");				
				operate();
			}else if(status==Status.READY){				
				log("op="+op+" status=READY");				
				unload();			
			}
			op++;
			sendMsgToGUI(status);
		}
	}
	
	@GUARD boolean readyToLoad(){		
		return loadEnabled;
	}
	
	@GUARD boolean notReadyToLoad(){		
		return !loadEnabled;
	}
	
	@GUARD boolean isUnloaded(){		
		return !loaded;
	}
	
	@GUARD boolean returnFromPause(){
		return status!=Status.PAUSE | stopFlag;
	}
	
	@LINK(guard="notReadyToLoad") 
	void enableLoad(){
		log("    Allowed to load");
		loadEnabled=true;
	}
	
	@LINK
	void getProduct(OpFeedbackParam<Product> v){
		log("getProduct invoked");		
		v.set(currentProduct);
		loaded=false;
	}
	
	void load(){
		await("readyToLoad");
		ObsProperty stats = getObsProperty("status");
		try{
			OpFeedbackParam<Product> v=new OpFeedbackParam<Product>();
			execLinkedOp("in-1","getProduct",v);
			loadEnabled=false;
			currentProduct=v.get();			
			loaded=true;
			status=Status.LOADED;	
			
			//TODO: Cleber: change to camel
			WebService.writeTag(this.nome, "LOADED");
			
			await_time(1000);
			
			stats.updateValue("LOADED");
		} catch (Exception ex){
			log("{operate}@ call link unload");
		}
		sendMsgToGUI(status);
	}
	
	void unload(){
		log("unload invoked");
		ObsProperty stats = getObsProperty("status");
		try{				
			execLinkedOp("out-1","enableLoad");
			await("isUnloaded");
			status=Status.WAIT;
			
			//TODO: Cleber: change to camel
			WebService.writeTag(this.nome, "WAIT");
			
			await_time(1000);
			
			stats.updateValue("WAIT");
			log("status after unload= "+status.toString());
		} catch (Exception ex){
			log("{operate}@ call link enableLoad");
		}
		sendMsgToGUI(status);
	}
	
	void operate(){
		ObsProperty stats = getObsProperty("status");
		currentProduct.operate("Op "+this.getClass().getName()+": Ok");
		status=Status.READY;
		
		//TODO: Cleber: change to camel
		WebService.writeTag(this.nome, "READY");
		
		await_time(1000);
		
		stats.updateValue("READY");
		signal("opDone");
		sendMsgToGUI(status);
	}
	
	void pauseOp(){
		pauseFlag=false;
		log("actual status= "+status.toString());
		ObsProperty stats = getObsProperty("status");
		lastStatus=status;
		status=Status.PAUSE;
		
		//TODO: Cleber: change to camel
		WebService.writeTag(this.nome, "PAUSE");
		
		stats.updateValue("PAUSE");
		
		sendMsgToGUI(status);
	}
	
	void stopOp(){
		stopFlag=false;
		ObsProperty stats = getObsProperty("status");
		operating=false;
		status=Status.STOPPED;
		
		//TODO: Cleber: change to camel
		WebService.writeTag(this.nome, "STOPPED");
		
		stats.updateValue("STOPPED");
		
		sendMsgToGUI(status);
	}
	
	void sendMsgToGUI(Status status)  
	{  
		try  
		{
			//		if( args.length != 2 )  
			//		{  
			//			System.out.println("Passagem de argumentos para o servidor java <IP_host> <quantidade>");  
			//			return;  
			//		}  

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
			
			if (this instanceof Loader)
			{
				ms.maquina = 1;
			}
			else if (this instanceof PastePrinter)
			{
				ms.maquina = 2;
			}
			else if (this instanceof PickAndPlaceStation)
			{
				ms.maquina = 3;
			}
			else if (this instanceof ReflowOven)
			{
				ms.maquina = 4;
			}
			else if (this instanceof Unloader)
			{
				ms.maquina = 5;
			}
			else if (this instanceof VisionSystem)
			{
				ms.maquina = 6;
			}
			
			System.out.println("Machine:sendMsgToGUI: Open socket and stream created!");  
			String host = "localhost";//args[0];  
			Socket socket = new Socket (host, 250);  

			DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
			String mensagem = ms.maquina + " " + ms.status;
			ostream.writeUTF(mensagem);
//			ObjectOutputStream oos = new ObjectOutputStream(ostream);
//			oos.writeObject(ms);
			ostream.flush();
			ostream.close();
//			oos.flush();
//			oos.close();
			socket.close();
			
			//Thread.sleep(2000);

		}  
		catch(Exception e)  
		{ 
			System.err.println("Machine: sendMsgToGUI Error! "+e);  
		}  
	}  
		
	@LINK
	void writeinput(String v) {
		log("writeinput invoked");
		System.out.println("Link is working!!!! Received: "+v);
	}
}



