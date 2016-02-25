/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este artefato constroi a interface entre o sistema multiagente e o
 * operador humano. Após o SMA ser instanciado e a infraestrutura criada a interface
 * apresenta uma lista de produtos cadastrados, nesta lista o operador seleciona
 * os tipos a serem produzidos, envia para o agente planner que ordena segundo critérios 
 * escolhidos pelo usuário (tempo de produção do lote, tamanho do lote, similariedades entre 
 * os produtos). Com a lista ordenada o agente planner envia o primeiro a ser produzido para
 * o agente configurator preparar a linha de produção que envia mensagem para a interface 
 * informando que está pronto para produzir o lote X. O operador pode então iniciar a produção, 
 * no final do lote, as estatísticas são apresentadas e ações de melhoria podem ser executadas.
 * O próximo lote (Y) pode ser produzido, e assim até o final da lista de produção.
 *   
 * 2013-05-06 - MAS initial infrastructure for SSP 
 * 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts;

import resources.*;
import cartago.*;


public class Interface extends Artifact {
	void init() {
		defineObsProperty("count", 0);
		
		//TODO 
		/*Inicia a conexao.*/
		System.out.println("artifacts.interface Inicia a conexao...");  
		WebService.initWeb();
	}
	
	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		signal("tick");
	}
}

