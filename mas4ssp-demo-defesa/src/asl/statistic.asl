/* BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.;
 * 
 * Resumo: este agente faz um relatorio dos dados da producao. Quantidades produzidas, defeitos.
 *  
*/

// Agent statistic in project mas4ssp

//Inclusao de primitivas para gestao da organizacao - Moise
{include("common.asl")}

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

/* Planos Organizacionais */

@adoptRoleStatistics
+!adoptRole(Group,Role) :	true
	<- 	lookupArtifact(Group, OrgArt);
		adoptRole(Role) [artifact_id(OrgArt)];
		focus(OrgArt).

//Identificado um defeito pelo inspector que avisa o statistic para incrementar a contagem

@updateDefectsStatitics
+!updateDefectsCount : true
	<- println("Defects counter ++").

@writeBatchStatistics		
+!g8 : true
	<- .println("GOAL g8: write production statistics").

@writeBatchErrors
+!g9 : true
	<- .println("GOAL g9: write products errors list").

/* Planos Organizacionais */
 
//A seguir estao os planos para acompanhar o comprometimento com o esquema proposto pelo agente Planner
//Quando as metas referentes a missao com a qual se comprometeu forem atingidas o esquema eh satisfeito

@goalG8[atomic]
+goalState(Scheme, g8, _, _, satisfied):
	.my_name(Me) & commitment(Me, statistics, Scheme)
<-
	.println("GOAL g8 satisfied - Mission statistics").
		
@goalG9[atomic]
+goalState(Scheme, g9, _, _, satisfied):
	.my_name(Me) & commitment(Me, statistics, Scheme)
<-
	.println("GOAL g9 satisfied - Mission statistics");
	!quit_mission(statistics, Scheme).


			
