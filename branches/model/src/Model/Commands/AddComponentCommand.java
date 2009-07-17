package Model.Commands;
import Model.PieceModel;
import Model.Schematic;



public class AddComponentCommand implements Command {
	Schematic scheme;
	PieceModel piece;
	
	public AddComponentCommand(Schematic scheme, PieceModel piece) {
		this.scheme = scheme;
		this.piece = piece;
	}
	
	@Override
	public void execute() {
		scheme.addComp(piece);

	}

	@Override
	public void unExecute() {
		scheme.removeComponent(piece.getId());
	}
}
