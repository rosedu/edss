package Model.Commands;

import java.io.Serializable;

import Model.Piece;
import Model.Schematic;

public class RemoveComponentCommand implements Command, Serializable {
	Schematic scheme;
	String id;
	Piece piece;
	
	public RemoveComponentCommand(Schematic scheme, String id) {
		this.scheme = scheme;
		this.id = id;
	}
	
	@Override
	public void execute() {
		piece = scheme.getComponents().get(id);
		scheme.removeComp(id);
	}

	@Override
	public void unExecute() {
		scheme.addComp(piece);
	}

}
