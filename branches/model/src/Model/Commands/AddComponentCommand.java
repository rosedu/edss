package Model.Commands;
import java.io.Serializable;

import Model.Piece;
import Model.Schematic;



public class AddComponentCommand implements Command, Serializable {
	Schematic scheme;
	Piece piece;
	
	public AddComponentCommand(Schematic scheme, Piece piece) {
		this.scheme = scheme;
		this.piece = piece;
	}
	
	@Override
	public void execute() {
		scheme.addComp(piece);

	}

	@Override
	public void unExecute() {
		scheme.removeComp(piece.getId());
	}
}
