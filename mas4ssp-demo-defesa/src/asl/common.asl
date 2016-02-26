/* Multiagent System to Small Series Production - MAS4SSP
 * BRAGECRIM 013/09 
 * Authors: LOCH G. N.; RIGOBELLO, T. F.; ROLOFF, M. L.; SOUZA, V. O.
 * 
 * Resumo: primitivas de suporte ao funcionamento da organizacao. 
 */

+obligation(Ag,Norm,committed(Ag,Mission,Scheme),DeadLine): .my_name(Ag)
	<- 	 .print("I am obliged to commit to ",Mission);
   		 commitMission(Mission)[artifact_name(Scheme)].
	
+obligation(Ag,Norm,achieved(Scheme,Goal,Ag),DeadLine): .my_name(Ag)
	<- 	.print("I am obliged to achieve goal ", Goal);
		!Goal[scheme(Scheme)];
		goalAchieved(Goal)[artifact_name(Scheme)].
	
+obligation(Ag,Norm,What,DeadLine):	.my_name(Ag)
	<-  .print("I am obliged to", What, ", but I don't know what to do!").
   	
+goalState(Sch,G,L1,L2,satisfied):	true
	<-	.print("## Goal ", G, " was satisfied!").

+schemes(L)
   <- for ( .member(S,L) ) {
         lookupArtifact(S,ArtId);
         focus(ArtId)
      }.
	
+!quit_mission(M,S):
	true
<-
	.print("Quitting mission: ", M, " em ", S);
	leaveMission(M) [artifact_name(S)].
	//.print("OK..............").
	
-!quit_mission(M,S) [error_msg(Msg)]:
	true
<-
	.print("Problem quitting mission: ", Msg).