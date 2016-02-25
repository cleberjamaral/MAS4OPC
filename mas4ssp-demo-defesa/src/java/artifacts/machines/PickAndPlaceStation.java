
/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: após a inserção da pasta de solda a placa é direcionada para a 
 * máquina que insere os componentes na placa nua. Esta máquina é chamada
 * de Pick and Place. Assim como a máquina de pasta de solda este equipamento
 * possui um SETUP específico para cada tipo de produto a produzir. Antes de iniciar
 * a produção os operadores 'alimentam' os buffers da máquina com os rolos de 
 * CIs que são necessários seguindo um arquivo que é gerado para cada tipo de
 * placa. Este arquivo é também o SETUP do equipamento que baseado nele procura
 * pelo CI X no buffer Y e insere na placa na posição (i,j). Assim como a anterior
 * a configuração do equipamento é via um arquivo XML. Em operação a máquina disponibiliza
 * o status dela - online, idle, setup, fail, offline.
 * 
 * ESTA É A TERCEIRA MÁQUINA DA LINHA DE PRODUÇÃO
 * 
 * 2013-05-07 - MAS initial infrastructure for SSP 
 */

/**
 * 
 */
package artifacts.machines;

import cartago.ARTIFACT_INFO;
import cartago.OUTPORT;

/**
 * @author roloff
 *
 */
@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
}
)public class PickAndPlaceStation extends Machine {

}
