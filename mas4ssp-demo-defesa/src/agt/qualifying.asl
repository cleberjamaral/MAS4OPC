/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: de posse do resultado da inspecao, o agente de qualidade avalia os defeitos 
 * e sugere alteracoes no SETUP dos equipamentos para que os defeitos nao ocorram novamente. 
 * Este agente tambem pode armazenar os dados estatisticos da producao do lote.
 *  
 */

// Agent qualifying in project mas2ssp

//Inclusao de primitivas para gestao da organizacao - Moise
{include("common.asl")}

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

/* Planos Organizacionais */

@adoptRoleQualifying
+!adoptRole(Group,Role)
	<- 	lookupArtifact(Group, OrgArt);
		adoptRole(Role) [artifact_id(OrgArt)];
		focus(OrgArt).

/* Planos */

//Com o defeito recebido do agente inspector o agente pode indicar para o usuario
//as possiveis causas do defeito e que acoes tomar para evitar que o defeito se repita
@defectReceivedFromInspector		
+!defectFound : true
	<- println("Detect fault - Type: temperatura elevada do forno ").
