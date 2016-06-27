package resources;

import java.net.*;  
import java.io.*;  

public class Client  
{
	@SuppressWarnings("resource")
	void call()  
	{  
		String sumString = "";  
		try  
		{
			//		if( args.length != 2 )  
			//		{  
			//			System.out.println("Passagem de argumentos para o servidor java <IP_host> <quantidade>");  
			//			return;  
			//		}  

			System.out.println("Abrindo o socket e criando o stream.");  
			String host = "172.18.16.186";//args[0];  
			Socket socket = new Socket (host, 250);  

			DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());  
			DataInputStream istream = new DataInputStream(socket.getInputStream());  

			System.out.println("inicializando sum 0.");  
			ostream.writeUTF("set_sum");  
			ostream.flush();  
			sumString = istream.readUTF();  

			System.out.println("Incrementando.");  
			int count = new Integer(1000).intValue();  
			long startTime = System.currentTimeMillis();  

			for (int i = 0; i < count; i++)  
			{  
				ostream.writeUTF("incrementando");  
				ostream.flush();  
				sumString = istream.readUTF();  
			}  

			long stopTime = System.currentTimeMillis();  
			System.out.println ("AVG ping = " + ((stopTime - startTime) /   
					(float)count) + "msecs") ;  
			System.out.println("Contador = " + sumString);  
		}  
		catch(Exception e)  
		{ System.err.println(e);  
		}  
	}  
}