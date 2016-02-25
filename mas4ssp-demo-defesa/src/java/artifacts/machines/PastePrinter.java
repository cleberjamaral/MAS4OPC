/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este equipamento realiza a aplicação da pasta de solda na placa nua.
 * Para cada tipo de produto é necessário um SETUP específico, como: espessura
 * da pasta de solda, velocidade da aplicação da pasta de solda, tipo de pasta, etc.
 * Este artefato deve ser configurado adequadamente pelo configurador para o tipo
 * de produto a ser produzido. No ambiente real esta configuração é um arquivo XML 
 * que deve ser salvo em diretório específico na máquina (baseada em PC). Assim que
 * o equipamento é posto para operar o software de controle carrega o arquivo XML
 * armazenado neste diretório para então realizar a operação.
 * 
 * ESTA É A SEGUNDA MÁQUINA DA LINHA DE PRODUÇÃO
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
 * @author gloch
 *
 */
@ARTIFACT_INFO(
outports = {
@OUTPORT(name = "out-1"),
@OUTPORT(name = "in-1")
}
)public class PastePrinter extends Machine {

}
