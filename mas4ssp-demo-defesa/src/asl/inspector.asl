/* Multiagent System to Small Series Production
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: o objetivo do agente inspector eh se comunicar com a maquina
 * de inspecao visual automatica (S2iAOI) para requerer a inspecao do produto
 * e aguardar por um diagnostico da S2iAOI (OK ou indicacao de defeitos). De posse
 * deste resultado o agente deve se comunicar com o agente de qualidade que apos
 * avaliar o defeito sugerirah alteracoes no SETUP dos equipamentos para que os 
 * defeitos nao ocorram novamente.
 *  
 */

// Agent inspector in project mas2ssp

//Inclusao de primitivas para gestao da organizacao - Moise
{include("common.asl")}

/* Initial beliefs and rules */

/* Initial goals */

//O agente deve focar na observacao do artefato do tipo Vision System que eh responsavel pela inspecao do item do lote
//!observeVisionSystem.

/* Plans */

/* Planos organizacionais */

//Plano organizacional para se comprometer com as missoes definidas no framework MOISE
@adoptRoleInspector
+!adoptRole(Group,Role)
	<- 	lookupArtifact(Group, OrgArt);
		adoptRole(Role) [artifact_id(OrgArt)];
		focus(OrgArt).
		
+!observeVisionSystem : true
	<- 	//.wait(10000);
		lookupArtifact("VS1",VSid);
		println("Watching ____________________________");
		focus(VSid).	
		//TODO Teste para simular um defeito run(1)[artifact_name("VS1")].

//Quando o artefato sistema de visao detecta uma falha no item do lote esta falha deve ser informada ao agente responsavel	
+status("DEFECT") : true
	<-	println("Item: Defect found!");
		!inform_qualifying.	
		
//Os defeitos encontrados pelo artefato sao listados		
+defects(D) : true
		<- print("Defect type: ",D).

//Uma mensagem eh enviada para o agente statistic que incrementarah a quantidade de defeitos encontrados		 
+!inform_statistic : true
	<- 	println("Sending MSG to statistic: update defects count!");
		.send([statistic],achieve,updateDefectsCount).

//Uma mensagem eh enviada para o agente qualifying que informarah a melhor acao frente ao defeito encontrado		 
+!inform_qualifying : true
	<- 	println("Sending MSG to qualifying: defect type");
		.send([qualifying],achieve,defectFound).

	
			
