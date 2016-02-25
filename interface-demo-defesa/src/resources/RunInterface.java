package resources;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunInterface {
	FrmPrincipal frame;

	public RunInterface() {
		frame = new FrmPrincipal();
		frame.setVisible(true);
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Iniciando o processo e escuta por um cliente...");  
		ouvindo();
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		RunInterface r = new RunInterface();
	}

	@SuppressWarnings("resource")
	public void ouvindo()  
	{  
		Socket socket = null;  

		try  
		{  
			ServerSocket serverSocket = new ServerSocket(250);  

			while (true)  
			{
				System.out.println("Aguardando um conexão cliente");  
				socket = serverSocket.accept();  
				DataInputStream istream = new DataInputStream(socket.getInputStream()); 
				//ObjectInputStream ois = new ObjectInputStream(istream);

				//MensagemSupervisorio ms = (MensagemSupervisorio) ois.readObject();

				//frame.setAgentStatus(ms);

				String aux = istream.readUTF();
				String[] splited = aux.split("\\s+");
				MensagemSupervisorio ms = new MensagemSupervisorio();
				ms.maquina = Integer.parseInt(splited[0]);
				ms.status = Integer.parseInt(splited[1]);
				frame.setAgentStatus(ms);
			}
			
		}  
		catch (Exception e)  
		{
			e.printStackTrace();  
			if  (socket != null)
			{
				try  
				{ 
					socket.close();
				} 
				catch (IOException ex) 
				{}
			}
		}  
	}  
}
