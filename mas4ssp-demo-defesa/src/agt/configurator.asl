/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este agente tem como objetivo de se comunicar com cada equipamento
 * da linha de producao e realizar o SETUP de cada um de acordo com o arquivo de 
 * configuracao (XML) de cada equipamento. Este agente se comunica com cada 
 * equipamento de acordo com a especificacao fornecida.
 *  
 */

// Agent configurator in project mas4ssp

//Inclusao de primitivas para gestao da organizacao - Moise
{include("common.asl")}

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

//Este eh o plano organizacional do configurador
//O plano indica com qual grupo o configurador se compromete e entao recebe a missao com a qual deve se comprometer
@adoptRoleConfigurator
+!adoptRole(Group,Role)
	<- 	lookupArtifact(Group, OrgArt);
		adoptRole(Role) [artifact_id(OrgArt)];
		focus(OrgArt).

//Baseado no ID do produto, no tamanho do lote e na especificacao de qual linha utilizar
//o agente configurator cria a linha de producao para o produto selecionado que foi enviado para agente planner pelo usuario		
@prepareMachinesToProduction		
+!prepareMachines(P,LL,ML) : true
					 <- .nth(0,P,IdProduto);
						.nth(1,P,NumeroProduzir);
						+numero(NumeroProduzir);
						+idProduct(IdProduto);
						+linkList(LL);
						+machineList(ML);
						+count_idle(0);
						.println(" Code: ",IdProduto ," Batch size: ", NumeroProduzir," Machines list: ",LL).

//Aqui o agente cria dinamicamente a linha de producao
//Este eh um grande ganho do projeto pois o agente nao conhece apenas uma linha de maquinas, ele nao necessita conhecer,
//o que ele recebe sao duas listas de maquinas presentes no workspace PRODUCTION e como ele deve relaciona-las
//para cada tipo de produto, uma nova linha de producao pode ser criada, ou ate mesmo a ordem de producao de um produto
//pode ser alterada. Por exemplo, pode algum momento ser melhor realizar a inspecao antes do forno, ou vice-versa
	
+!g3 : true
	<- .println("GOAL g3: select and create machines and links");
		!createInterface;
		!createDyn;
		.wait({+allLinked(1)}); //O agente espera ate criar o link de todas as maquinas da linha espeficada
		-+allLinked(0);
		.send([inspector],achieve,observeVisionSystem);. 
		
/* ATENCAO: neste modelo o SETUP estah sendo feito depois de uma percepcao de mudanca de estado */

//Aqui o setup das maquinas para o produto selecionado eh realizado
+!g4 : idProduct(Prod)
	<- .println("GOAL g4 : setup line machines to produce: ", Prod);
		!refresh_gui.

//Avisa a interface que o status da linha deve ser atualizado, as maquinas foram configuradas para producao do lote
@updateGUI
+!refresh_gui <- .print("Refreshing GUI - SETUP: OK").	//Pode ser implementado em um artefato INTERFACE em java para demonstracao
	
/* Planos Organizacionais */

//A seguir estao os planos para acompanhar o comprometimento com o esquema do agente configurator
//Quando as metas referentes a missao com a qual se comprometeu forem atingidas o esquema eh satisfeito

@goalG3[atomic]
+goalState(Scheme, g3, _, _, satisfied):
	.my_name(Me) & commitment(Me, setup, Scheme)
<-
	.println("GOAL g3 satisfied - Mission setup").

@goalG4[atomic]
+goalState(Scheme, g4, _, _, satisfied):
	.my_name(Me) & commitment(Me, setup, Scheme)
<-
	.println("GOAL g4 satisfied - Mission setup");
	!quit_mission(setup, Scheme).
		
/* Criacao dinamica do artefatos das maquinas */

//Este plano cria a linha de producao para o produto selecionado baseado nas especificacoes
//de tipo de maquinas e forma como devem ser ligadas para formar a linha de producao

@createLine
+!createDyn : machineList(X) & .list(X) 
						<- .println("Machine List created");
							for(.member(Y,X)){!createMachine(Y)}.
     								 
+!createMachine(M) : M = machine(N,T) 
						<-	.println("Creating machine >> Name: ",N," type: ",T);
					  		makeArtifact(N,T,[],C); 
					  		focus(C);
					  		naming(N)[artifact_name(N)]. //metodo que nomeia e espera o STOPPED
					  									  	
+!createInterface	 <- 
				makeArtifact("INT","artifacts.Interface",[],C);
				focus(C). 
			  			        								 
/* Realiza o setup das maquinas para a produto selecionado */

+status(V)[artifact_name(Id,N)] : V =="STOPPED" & numero(Num) & idProduct(IDP)
			<- println("Setup Machine: ",Id);
			   setup(Num,IDP)[artifact_id(Id)].	// numero a produzir eh parametro recebido do usuario pelo planner

/*Verificar se todas as maquinas foram criadas foram criadas no workspace de artefato*/	   							

+status(V)[artifact_name(Id,N)] : V =="IDLE" & count_idle(A) <- .findall(machine(N,T),status("IDLE")[artifact_name(O,N),artifact_type(O,T)],L);
						   .wait(100);
						   .println("Number of Machines in IDLE: ");
						   for(.member(Z,L)){.println(Z)};
						   .println(A);
						   -+count_idle(A+1); //Contador de maquinas em IDLE
							?allDone(L). 

// Caso tudo tenha sido corretamente criado, as maquinas estao configuradas e prontas para operar	   							
	   							
+?allDone(L) : count_idle(X) & X == 6 <- .println("Ready to run!");
											!linkArtifacts.

-?allDone(L) : machineList(X) <- true.
								 //.println("oiiii").
//				 				 .println("machines : ");
//								 for(.member(Z1,X)){.println(Z1)}.

//Neste plano a maquina para um estado onde jah estah apta para realizar a sua operacao
@runMachine
+!run(M) : M = machine(N,T) <- println("Run Machine: ",N);
	   			 			   run[artifact_name(N)].   			 			   
	   			 			   
//Neste plano os artefatos sao criados no CArtAgO e viculados com o SCADA para aquisicao dos dados da linha de producao 	   			 			   
@linkMachinesArtifacts
+!linkArtifacts : linkList(LL) & machineList(ML) & numero(Num)
					<- for(.member(Y,LL)){!createLink(Y)};
                       for(.member(X,ML)){!run(X)};
                       -+allLinked(1);
                       .send(assembler,tell,start(Num,ML)).
				
//Para cumprir o plano anterior cada maquina eh ligada com a maquina anterior e posterior na linha especificada
@createLinkBetweenMachines
+!createLink(L) : L = link(N,In,Out) 
					<-	 lookupArtifact(N,NId);
						 lookupArtifact(In,InId);
						 lookupArtifact(Out,OutId);
						 linkArtifacts(NId,"in-1" , InId);
						 linkArtifacts(NId,"out-1",OutId);
						 .println("Creating link >> Name: ",N," in: ",In," out: ",Out).
   		   			