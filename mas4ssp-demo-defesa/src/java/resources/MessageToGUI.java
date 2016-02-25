package resources;

import java.io.Serializable;

import artifacts.machines.Machine.Status;

public class MessageToGUI implements Serializable
{
	public Status status;
	public int maquina;
}
