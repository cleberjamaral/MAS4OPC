/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: este agente tem como objetivo definir qual serah a sequencia de produtos a serem produzidos
 * a partir do inicio da operacao do Sistema Multiagente. Ele recebe um produto
 * a ser produzido selecionado pelo usuario na interface. O agente deverah criar uma
 * lista ordenada dos produtos (quando varios) e apos enviar o produto a ser produzido para o agente configurador no
 * momento oportuno.
 *  
 */

// Agent planner in project mas4ssp

//Inclusao de primitivas para gestao da organizacao - Moise
{include("common.asl")}

/* Initial beliefs and rules */

//Lista de maquinas a serem criadas baseadas no experimento da linha do LabElectron
machineList([ machine("L1","artifacts.machines.Loader"),
			  machine("PP1","artifacts.machines.PastePrinter"),
			  machine("PaP1","artifacts.machines.PickAndPlaceStation"), 
			  machine("U1","artifacts.machines.Unloader"),
			  machine("VS1","artifacts.machines.VisionSystem"),
			  machine("CI","artifacts.machines.Interface"), //CamelInterface
			  machine("RO1","artifacts.machines.ReflowOven")
]).

//Criacao da lista de links entre as maquinas da linha do Labelectron
//o agente Assembler por meio de um artefato Assembler eh que inicia e termina a lista 
//pois este artefato (e o agente) eh o responsável pelo carregamento do lote e acompanhar a sua conclusao (descarregamento)
list_id_link([  [001,[ link("L1","assembler","PP1"), link("PP1","L1","PaP1"),link("PaP1","PP1","U1"), link("U1","PaP1","assembler"), link("assembler","U1","L1") ]],
			   	[002,[ link("L1","assembler","PP1"), link("PP1","L1","PaP1"),link("PaP1","PP1","RO1"),link("RO1","PaP1","VS1"), link("VS1","RO1","U1"), link("U1","VS1","assembler"), link("assembler","U1","L1") ]], //link com VisionSystem
			   	[003,[ link("L1","assembler","PP1"), link("PP1","L1","PaP1"),link("PaP1","PP1","U1"), link("U1","PaP1","assembler"), link("assembler","U1","L1") ]],
			   	[004,[ link("L1","assembler","PP1"), link("PP1","L1","PaP1"),link("PaP1","PP1","RO1"),link("RO1","PaP1","CI"), link("CI","RO1","U1"), link("U1","CI","assembler"), link("assembler","U1","L1") ]] //link Camel Interface
			   	]).

/* Initial goals */

//Este eh a meta para criar a organizacao baseada no framework Moise
//a partir dos diagramas estrutural, de missao e normativo o arquivo .XML eh criado e a organizacao seguirah
//a modelagem presente no XML
!create_organization.

//Este objetivo eh a criacao de uma interface grafica do modelo de implementacao com o operador
//a interface grafica com o usuario pode ser criada de diferentes formas, como artefato da plataforma JaCaMo
//ou como um sistema separado onde se sugere o uso de Web Service como mecanismo de interacao entre o sistema
//legado e o MAS4SSP - para a defesa se criou um aplicativo java separado que usa sockets para comunicar com o MAS4SSP
!create_GUI. 


/* Plans */

//Este plano busca criar a organizacao conforme o arquivo XML que eh resultado da modelagem com Prometheus AEOlus
@createOrganization
+!create_organization : true
	<-  
		/*Criar grupos e definir subgrupos*/
		.my_name(Me);
		.print("Organization: Creating groups and subgroups...");
		makeArtifact("company","ora4mas.nopl.GroupBoard",["src/org/org4ssp.xml","grCompany", false, true], GrpID);
		setOwner(Me);
		focus(GrpID);

		.print("Organization: Making manager artifact...");
		makeArtifact("sManagers","ora4mas.nopl.GroupBoard",["src/org/org4ssp.xml","grManagers", false, true], SGM);
		setOwner(Me);
		focus(SGM);
		setParentGroup("company")[artifact_id(SGM)];
		
		/*Planner adotar seu papel */
		.print("Organization: Planner is taking his role...");
		adoptRole("rPlanning")[artifact_id(SGM)];

		.print("Organization: Making shopfloor artifacts...");
		makeArtifact("sShopFloor","ora4mas.nopl.GroupBoard",["src/org/org4ssp.xml","grShopFloor", false, true], SGSF);
		setOwner(Me);
		focus(SGSF);
		setParentGroup("company")[artifact_id(SGSF)];
		
		/* Envio dos papeis dos outros agentes */
		// O agente Planner envia para os demais agentes quais sao os papeis que foram sugeridos para eles
		// Cada agente opta por adotar ou nao o papel sugerido pela organizacao
		.print("Organization: shopfloor and managers artifacts are taking their roles...");
		.send([assembler],achieve,adoptRole("sShopFloor",rAssembly));
		.send([configurator],achieve,adoptRole("sShopFloor",rSetup));
		.send([inspector],achieve,adoptRole("sShopFloor",rInspection));
		.send([qualifying],achieve,adoptRole("sManagers",rExpert));
		.send([statistic],achieve,adoptRole("sManagers",rStatistic));
		
		/* Criam-se os esquemas e estes sao adicionados aos grupos */
		makeArtifact("planProduction","ora4mas.nopl.SchemeBoard",["src/org/org4ssp.xml","production",false,true],SchID);		
     	
     	focus(SchID);
     	
		.print("Organization: Adding schemes...");
     	addScheme("planProduction") [artifact_id(SGM)];
     	addScheme("planProduction") [artifact_id(SGSF)];
     	addScheme("planProduction") [artifact_id(GrpID)];
     	.print("Organization: Created succesfully!").
     	

//Este plano cria a interface grafica neste caso que faz parte da plataforma JaCaMo
//O agente Planner recebe o produto a ser produzido da interface do usuário
@createGUI	
+!create_GUI : true
 	<-  .print("GUI... selecting the product");
		+product_received([1,10]). //Formato do produto [qual linha usar - list links,tamanho do lote]

//Este plano aguarda da interface grafica o produto a ser produzido
//O agente Planner recebe o produto a ser produzido da interface do usuario e passa as suas informacoes
@receiveProduct      
+!g1 : product_received(P)
	<-  .print("Product received from GUI: ",P);
		.nth(0,P,K); //desmembrar lista
		.nth(1,P,U); 
		+id_prod(K);
		+qtd_prod(U).

//Este plano cria uma lista de links que sao necessarios para criar a linha de producao
//para o produto a ser produzido, cada produto tem uma lista especifica, ou seja, tem uma sequencia de maquinas apropriada
@createLinksList			
+!g2 :  list_id_link(L_Id_L) & id_prod(I) & product_received(P) & machineList(ML)
	<-  .print("Links selected for product: ",L_Id_L);
		.length(L_Id_L,L);
		-+size(0);
		while(size(S) & S < L)
		{
			.nth(S,L_Id_L,ID_L);
			.nth(0,ID_L,Id);
			if(Id==I){
				.nth(1,ID_L,LL);
				+linkedLinks(LL);
				+identificador(Id);
				-+size(L);
				.print("________",Id,"________________",LL);
				.send([configurator],achieve,prepareMachines(P,LL,ML));
			}	
			else{-+size(S+1);}
		}.

/* Planos Organizacionais */
 
//A seguir estao os planos para acompanhar o comprometimento com o esquema do agente Planner
//Quando as metas referentes a missao com a qual se comprometeu forem atingidas o esquema eh satisfeito
  

@goalG1[atomic]
+goalState(Scheme, g1, _, _, satisfied):
	.my_name(Me) & commitment(Me, leadUp, Scheme)
<-
	.print("GOAL g1 satisfied - Mission leadUp").

//Como g2 somente ocorre depois de g1 - modelados como uma sequencia, se g2 for cumprida isto significa
//que as Metas foram cumpridas, missao cumprida, pode abandonar a missao

@goalG2[atomic]
+goalState(Scheme, g2, _, _, satisfied):
	.my_name(Me) & commitment(Me, leadUp, Scheme)
<-
	.print("GOAL g2 satisfied - Mission leadUp");
	!quit_mission(leadUp, Scheme).
