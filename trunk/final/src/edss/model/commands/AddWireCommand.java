package edss.model.commands;
import java.io.Serializable;

import edss.model.Schematic;



public class AddWireCommand implements Command, Serializable {
	Schematic scheme;
	String piece1;
	String pin1;
	String piece2;
	String pin2;
	
	public AddWireCommand(Schematic scheme, String piece1, String pin1, String piece2, String pin2) {
		this.scheme = scheme;
		this.piece1 = piece1;
		this.pin1 = pin1;
		this.piece2 = piece2;
		this.pin2 = pin2;
	}
	
	@Override
	public void execute() {
		scheme.addWireWithoutUndo(piece1, pin1, piece2, pin2);
	}

	@Override
	public void unExecute() {
		scheme.removeWireWithoutUndo(piece1, pin1, piece2, pin2);
	}

}
