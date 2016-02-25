/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato representa o equipamento onde o lote de placas nuas a 
 * serem fabricadas Ã© acondicionado pelo operador antes de iniciar a produÃ§Ã£o.
 * Este artefato deve emular o recebimento de informaÃ§Ã£o se o magazine de placas 
 * estÃ¡ carregado e quantas placas nuas foram carregadas. SÃ³ entÃ£o pode avisar 
 * ao agente configurador se o SETUP estÃ¡ correto ou nÃ£o. Na linha real de produÃ§Ã£o 
 * a informaÃ§Ã£o de carregado ou nÃ£o Ã© um BIT que vem do CLP que controla este 
 * equipamento, e o nÃºmero de placas Ã© um INT que tambÃ©m Ã© informado pelo CLP.
 * A cada saÃ­da de placa o BUFFER Ã© decrementado atÃ© o final do lote.
 *   
 * ESTA Ã‰ A PRIMEIRA MÃ�QUINA DA LINHA DE PRODUÃ‡ÃƒO
 *   
 * 2013-05-06 - MAS initial infrastructure for SSP 
 */

/**
 * 
 */

package artifacts.machines;

import java.util.Stack;

import resources.Product;
import cartago.ARTIFACT_INFO;
import cartago.OUTPORT;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;

@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
}
)public class Loader extends Machine {
	
	Stack<Product> currentBatch;
	
	void load(){
		await("readyToLoad");
		ObsProperty stats = getObsProperty("status");
		try{
			OpFeedbackParam<Stack<Product> > v=new OpFeedbackParam<Stack<Product> >();
			execLinkedOp("in-1","getBatch",v);
			currentBatch=v.get();
			loadEnabled=false;			
			status=Status.LOADED;				
			stats.updateValue("LOADED");
		} catch (Exception ex){
			log("{operate}@ call link unload");
		}
	}
	
	void unload(){
		ObsProperty stats = getObsProperty("status");
		try{				
			execLinkedOp("out-1","enableLoad");
			await("isUnloaded");
			if(!currentBatch.isEmpty()){
				status=Status.LOADED;
				stats.updateValue("LOADED");
			}else{
				log("Loader empty!!!");
				status=Status.WAIT;
				stats.updateValue("WAIT");
			}
			
		} catch (Exception ex){
			log("{operate}@ call link enableLoad");
		}
	}
	
	void operate(){
		ObsProperty stats = getObsProperty("status");
		currentProduct=currentBatch.pop();
		loaded=true;
		status=Status.READY;
		stats.updateValue("READY");
		//Manda msg para view da interface gráfica para mudar FLAG - lote iniciado
		//sendMsgBatch("Started");
	}
}
