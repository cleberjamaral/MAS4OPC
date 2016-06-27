/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato �� a forma como o agente assembler interage com o ambiente.
 * O agente tem como objetivo monitorar o processo de montagem
 * do produto. Ap��s todos os equipamentos estarem dispon��veis para produ����o
 * o lote de produtos pode ser carregado na linha de montagem. O agente envia 
 * a ordem de carregamento do lote de produtos no primeiro equipamento e 
 * supervisiona a produ����o at�� o armazenamento de todos os produtos produzidos
 * no ultimo equipamento. A qualquer momento ele sabe o status de cada um dos 
 * equipamentos (livre, ocupado, falha).
 *   
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.agents;

import java.util.Iterator;
import java.util.Stack;

import resources.*;
import cartago.*;

@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
}
)
public class Assembler extends Artifact {
	
	int count;
	boolean loadEnabled;
	int batch_size;
	
	
	void init() {
		defineObsProperty("count",0);
		count=1;
		batch_size=0;
		loadEnabled=false;
	}
	
	@OPERATION void run(int _batchSize){
		log("run Invoked");
		batch_size = _batchSize;
		execInternalOp("operate");
		
		/*Atualiza o numero de placas a serem produzidas e zera o numero de placas prontas.*/
		//TODO: Cleber replace: WebService.writePCB("Machines.Loader.Loader_NumOfPCB",_batchSize);
		//TODO: Cleber replace: WebService.writePCB("Machines.Unloader.Unloader_NumOfFinished",0);
	}
	
	@INTERNAL_OPERATION void operate(){
		Stack<Product> sP=new Stack<Product>();
		
		try{
			log("ready to insert");
			execLinkedOp("out-1","enableLoad");				
		} catch (Exception ex){
//			ex.printStackTrace();
			log("{insert}@ call link enableLoad");
		}
		
		await("readyForLoad");
		try{
			OpFeedbackParam<Stack<Product> > v=new OpFeedbackParam<Stack<Product> >();
			execLinkedOp("in-1","getBatch",v);
			sP=v.get();
//				execLinkedOp("in-1","test");
			loadEnabled=false;
			log("Product "+sP.size()+" is finished");
		} catch (Exception ex){
//				ex.printStackTrace();
			log("{extract}@ call link unload");
		}
		Iterator<Product> itr=sP.iterator();
		while(itr.hasNext()){
			Product p=itr.next();
			log(p.inspec());			
		}

	}
	
	@OPERATION
	void inc() {
		log("inc invoked");
		count++;
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		signal("tick");		
	}
	
	@GUARD boolean readyForLoad(){		
		return loadEnabled;
	}
	
	@LINK void getBatch( OpFeedbackParam<Stack<Product> > v){
		Stack<Product> lp = new Stack<Product>();
		
		for(int i=batch_size;i>0;--i){
			lp.add(new Product(i));
		}	
		v.set(lp);
		log("Started the production of "+lp.size()+" Products");		
	}
	
	@LINK void enableLoad(){
		log("    Allowed to load");
		loadEnabled=true;
	}

	@LINK
	void writeinputAr(String v) {
		System.out.println("Link is working!!!! Received: " + v +" opid:"+thisOpId.toString());
		log("writeinput invoked");
	}

}