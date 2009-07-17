package Model.Commands;

import java.io.Serializable;

import Model.Schematic;

public class RotateComponentCommand implements Command, Serializable {
	Schematic scheme;
	String id;
	int r;
	
	public RotateComponentCommand(Schematic scheme, String id, int r){
		this.scheme = scheme;
		this.id = id;
		this.r = r;
	}
	
	@Override
	public void execute() {
		scheme.getComponents().get(id).setRotated(r);
	}

	@Override
	public void unExecute() {
		scheme.getComponents().get(id).setRotated(-r);
	}

}
