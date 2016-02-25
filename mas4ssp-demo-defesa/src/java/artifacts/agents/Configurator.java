/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato propicia que o agente configurator interaja com o ambiente.
 * O agente tem como objetivo de se comunicar com cada equipamento
 * da linha de produção e realizar o SETUP de cada um de acordo com o arquivo de 
 * configuração (XML) de cada equipamento. Este agente se comunicará com cada 
 * equipamento de acordo com a especificação fornecida.
 *  
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.agents;

import cartago.*;

public class Configurator extends Artifact {
	void init() {
		defineObsProperty("count", 0);
	}
	
	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		signal("tick");
	}
	

}

