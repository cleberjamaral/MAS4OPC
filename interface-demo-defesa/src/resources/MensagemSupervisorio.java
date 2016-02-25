package resources;

import java.io.Serializable;

public class MensagemSupervisorio implements Serializable{
	public enum Status {
		STOPPED, IDLE, WAIT, LOADED, READY, PAUSE, DEFECT
	}
	
	public int status;
	public int maquina;
}
