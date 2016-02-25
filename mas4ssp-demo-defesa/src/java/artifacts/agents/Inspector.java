/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato é a forma como o agente inspector interage com o ambiente.
 * O objetivo do agente inspector é se comunicar com a máquina
 * de inspeção visual automática (S2iAOI) para requerer a inspeção do produto
 * e aguardar por um diagnóstico da S2iAOI (OK ou indicação de defeitos). De posse
 * deste resultado o agente deve se comunicar com o agente de qualidade que após
 * avaliar os defeitos sugerirá alterações no SETUP dos equipamentos para que os 
 * defeitos não ocorram novamente.
 *  
 * 2013-05-06 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.agents;

import cartago.*;

public class Inspector extends Artifact {
	void init(int initialValue) {
		defineObsProperty("count", initialValue);
	}
	
	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		signal("tick");
	}
}

