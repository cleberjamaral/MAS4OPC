/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato é a forma como o agente qualifying interage com o ambiente.
 * Com o resultado da inspeção, o agente de qualidade avalia os defeitos 
 * e sugere alterações no SETUP dos equipamentos para que os defeitos não ocorram novamente. 
 * Este agente também armazena os dados estatísticos da produção do lote.
 *  
 * 2013-05-06 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.agents;

import cartago.*;

public class Qualifying extends Artifact {
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

