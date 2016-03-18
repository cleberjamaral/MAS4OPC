/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato representa o equipamento onde o lote de placas prontas sÃ£o
 * armazenadas no final da produÃ§Ã£o. Este artefato deve emular o recebimento de 
 * informaÃ§Ã£o se o magazine de placas estÃ¡ carregado, ou seja, todas as placas
 * foram produzidas. SÃ³ entÃ£o pode avisar ao agente assembler que a produÃ§Ã£o do lote
 * terminou. DESEJÃ�VEL: as placas podem ser retiradas da linha de produÃ§Ã£o por falhas ou
 * quebras, desta forma seria interessante pensar em como avisar a UNLOADER que ao 
 * invÃ©s de 30 unidades, deve esperar somente por 29. Na linha real de produÃ§Ã£o 
 * a informaÃ§Ã£o de carregado ou nÃ£o Ã© um BIT que vem do CLP que controla este 
 * equipamento, e o nÃºmero de placas Ã© um INT que tambÃ©m Ã© informado pelo CLP.
 *   
 * ESTA Ã‰ A SEXTA E ÃšLTIMA MÃ�QUINA DA LINHA DE PRODUÃ‡ÃƒO
 *   
 * 2013-05-06 - MAS initial infrastructure for SSP 
 */

/**
 * 
 */
package artifacts.machines;

import java.util.Stack;
import cartago.ARTIFACT_INFO;
import cartago.LINK;
import cartago.OUTPORT;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;
import resources.*;

/**
 * @author roloff, gloch
 *
 */

@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
}
)public class Unloader extends Machine {
	
	Stack<Product> currentBatch = new Stack<Product>();
	
	void operate(){		
		ObsProperty stats = getObsProperty("status");
		await_time(1000);
		currentBatch.add(currentProduct);
		log("batch size="+currentBatch.size());
		loaded=false;
		if(currentBatch.size()==batchSize){
			this.status=Status.READY;
			stats.updateValue("READY");
			
			/*Atualiza o numero de placas prontas.*/
			WebService.writePCB("Machines.Unloader.Unloader_NumOfFinished",currentBatch.size());
			
			
		}else{
			this.status=Status.WAIT;
			stats.updateValue("WAIT");
			
			/*Atualiza o numero de placas prontas.*/
			WebService.writePCB("Machines.Unloader.Unloader_NumOfFinished",currentBatch.size());
		}		
	}
	
	@LINK void getBatch(OpFeedbackParam<Stack<Product> > v){
		v.set(currentBatch);
		log("Finished production of "+currentBatch.size()+" Products");
		//Manda msg para view da interface gráfica para mudar FLAG - lote finalizado
		//sendMsgBatch("Finished");
	}

}
