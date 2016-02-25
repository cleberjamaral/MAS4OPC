/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: o forno de refusão 'solda' os componentes inseridos pela 
 * PICK and PLACE tornando liquida a pasta de solda que foi aplicada
 * antes da insersora. O forno de refusão tem um SETUP via CLP onde para 
 * cada tipo de placa de circuito impresso é preciso configurar: numero de zonas,
 * temperatura de cada zona e a velocidade da esteira. Este artefato deve
 * permitir que estes dados sejam configurados pelo agente configurator
 * para cada tipo de produto.
 * 
 * ESTA EH A QUARTA MAQUINA DA LINHA DE PRODUCAO
 * 
 * 2013-05-07 - MAS initial infrastructure for SSP 
 */

// CArtAgO artifact code for project mas2ssp

package artifacts.machines;

import cartago.ARTIFACT_INFO;
import cartago.OUTPORT;

@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
})

public class ReflowOven extends Machine {
	
}

