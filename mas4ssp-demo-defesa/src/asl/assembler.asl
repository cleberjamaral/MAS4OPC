/* Multiagent System to Small Series Production - MAS4SSP
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este agente tem como objetivo monitorar o processo de montagem
 * do produto. Apos todos os equipamentos estarem disponiveis para producao
 * o lote de produtos pode ser carregado na linha de montagem. O agente envia 
 * o lote de produtos no primeiro equipamento (Loader) e 
 * supervisiona a producao ate o armazenamento de todos os produtos produzidos
 * no ultimo equipamento (Unloader). A qualquer momento ele sabe o status de cada um dos 
 * equipamentos (STOPPED, IDLE, WAIT, LOADED, READY, PAUSE, DEFECT). 
 */

// Agent assembler in project mas4ssp

//Inclusao de primitivas para gestao da organizacao - Moise
{include("common.asl")}

/* Initial beliefs and rules */

//NAO existe nenhum item do lote no equipamento final (Unloader), ou seja, nenhum produto finalizado do lote
termino(0).

/* Initial goals */

//Como o agente assembler eh o responsavel por carregar os itens do lote no primeiro equipamento (Loader),
//acompanhar o fluxo de producao e constatar que todos os itens do lote foram produzidos (Unloader)
//se optou por criar um artefato que auxilia no carregamento dos itens do lote e outras acoes diretas
//nos artefatos - a seguir o artefato baseado no agente eh criado

!createOwnArtifact.


/* Plans */

/* Planos */
										
//Este plano carrega o numero de itens do lote do produto (lote) selecionado no primeiro equipamento da linha construida
@loadBatchOnLine
+!g5 : numero(Num)
	<- .println("GOAL g5: load itens on loader and start");
		run(Num)[artifact_name("assembler")].

+!g5 : true
	<- .println("g5 fail event occurred!").

//Este plano acompanha a producao do lote - fluxo dos produtos pelas maquinas
@watchProduction
+!g6 : machinesList(ML)
	<- .println("GOAL g6: watch out production");
	   for(.member(X,ML)) {!observe(X)}; //Propriedade observavel de todos os artefatos da linha - verificar o status
	   .wait({+termino(1)}); //Quando o item do lote chega no ultimo equipamento este avisa que o item foi finalizado
	   -+termino(0).

+!g6 : true
	<- .println("g6 fail event occurred!").
	   
@endBatch
+!g7 : true
	<- .println("GOAL g7: end batch").
	
/* Planos Organizacionais */

//A seguir estao os planos para acompanhar o comprometimento com o esquema do agente assembler
//Quando as metas referentes a missao com a qual se comprometeu forem atingidas o esquema eh satisfeito

@goalG5[atomic]
+goalState(Scheme, g5, _, _, satisfied):
	.my_name(Me) & commitment(Me, assembly, Scheme)
<-
	.println("GOAL g5 satisfied - Mission assembly").

//Como g6 soh ocorre depois de g5 -> modelados como uma sequencia no diagrama de objetivos do sistema

@goalG6[atomic]
+goalState(Scheme, g6, _, _, satisfied):
	.my_name(Me) & commitment(Me, assembly, Scheme)
<-
	.println("GOAL g6 satisfied - Mission assembly").
	
//Como g7 soh ocorre depois de g6 - modelados como uma sequencia, se g7 for cumprida isto significa
//que as Metas foram cumpridas, a missao foi cumprida e o agente pode concluir a missao com a qual se comprometeu

@goalG7[atomic]
+goalState(Scheme, g7, _, _, satisfied):
	.my_name(Me) & commitment(Me, assembly, Scheme)
<-
	.println("GOAL g7 satisfied - Mission assembly");
	!quit_mission(assembly, Scheme).	
	
//Plano para o agente assembler se comprometer com as metas organizacionais	
@adoptRoleAssembler	
+!adoptRole(Group,Role)
	<- 	lookupArtifact(Group, OrgArt);
		adoptRole(Role) [artifact_id(OrgArt)];
		focus(OrgArt).

//Plano para a criacao do artefato para auxiliar o agente assembler nas acoes sobre o workspace PRODUCTION
@createAssemblerArtifact
+!createOwnArtifact <- makeArtifact("assembler","artifacts.agents.Assembler",[],D);focus(D).

//Iniciar a producao do item do lote do produto pelo usuario na interface grafica - ou definido em codigo
+start(NumeroProduzir,ML):true
			<- 	-start(NumeroProduzir);
				-+numero(NumeroProduzir);
				-+machinesList(ML).
				
//Observa o estado de uma determinada maquina da linha de producao criada
+!observe(X): X = machine(N,T)
			<-  lookupArtifact(N,MachineID);
				focus(MachineID).
				
//Quando o artefato ultima maquina (UnLoader) finaliza a operacao - produto chega na maquina - esta informa o agente				
+status("LOADED") [artifact_name(Id,"U1")] : true
			<-  .println("Item finished________________").	

//Quando o artefato ultima maquina (UnLoader) finaliza o ultimo item do lote e passa para um estado de READY
//isto indica que a producao do lote chegou ao fim			
+status("READY") [artifact_name(Id,"U1")] : true
			<-  .println("Batch production finished________________");
				-+termino(1).				

//Caso alguma falha ocorra em outra maquina, a maquina observada passa para um estado de PAUSA				
+status("PAUSE")[artifact_name(Id,N)]	: true 
			<- println("___________________PAUSED",N).
			
//Caso alguma falha ocorra na maquina observada esta passa para um estado de STOPPED							
+status("STOPPED")[artifact_name(Id,N)]	: true 
			<- println("___________________STOPPED",N).
			