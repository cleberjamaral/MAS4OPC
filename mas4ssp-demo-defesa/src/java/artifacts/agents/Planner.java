/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo:  este artefato é a forma como o agente planner interage com o ambiente.
 * O agente tem como objetivo definir qual será a sequência de produtos a serem produzidos
 * a partir do início da operação do Sistema Multiagente. Ele receberá uma lista de produtos
 * a serem produzidos selecionados pelo usuário na interface do ScadaBR. O agente deverá criar uma
 * lista ordenada dos produtos e após enviar o produto a ser produzido para o agente configurador no
 * momento oportuno.
 *  
 * 2013-05-06 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.agents;

import cartago.*;

public class Planner extends Artifact {
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

