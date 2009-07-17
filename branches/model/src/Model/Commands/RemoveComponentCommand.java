package Model.Commands;

import Model.PieceModel;
import Model.Schematic;

public class RemoveComponentCommand implements Command {
	Schematic scheme;
	String id;
	PieceModel piece;
	
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
