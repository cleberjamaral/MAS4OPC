/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este equipamento a inspeÃ§Ã£o visual automÃ¡tica das placas. A interaÃ§Ã£o com
 * ele Ã© mais elaborada pois ele tambÃ©m possui um arquivo de SETUP para cada tipo de 
 * produto via XML. AlÃ©m disso, apÃ³s realizar a inspeÃ§Ã£o ele gera um diagnÃ³stico em
 * um arquivo XML apresentando os resultados da inspeÃ§Ã£o - quais regiÃµes da placa foram
 * inspecionadas e se OK ou NOK e que tipo de defeito encontrados. Estas infos. devem
 * ser repassadas para o agente qualidade que consultando o sistema especialista
 * saberÃ¡ qual a origem do defeito e assim pode atuar na produÃ§Ã£o corrigindo o SETUP
 * da mÃ¡quina que produziu o defeito. Por exemplo, alterando a temperatura do forno.
 * 
 * ESTA Ã‰ A QUINTA MÃ�QUINA DA LINHA DE PRODUÃ‡ÃƒO
 * 
 * 2013-05-07 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.machines;

import cartago.ARTIFACT_INFO;
import cartago.INTERNAL_OPERATION;
import cartago.OUTPORT;
import cartago.ObsProperty;

@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
})

public class VisionSystem extends Machine {
	
	public enum Defects {
		NENHUM, COMPONENTE_FALTANDO, COMPONENTE_ERRADO, PLACA_EMPENADA
	}
	Defects defects;
	
	void init() {
		defects = Defects.NENHUM;
		defineObsProperty("defects", "NENHUM");
	}
	
	
	@INTERNAL_OPERATION void defect_found()
	{
		ObsProperty stats = getObsProperty("status");
		try{
			status=Status.DEFECT;				
			stats.updateValue("DEFECT");
			
		} catch (Exception ex){
			log("{operate}@ call link unload");
		}
	}

	
}

