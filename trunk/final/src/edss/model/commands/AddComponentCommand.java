package edss.model.commands;
import java.io.Serializable;

import edss.model.Piece;
import edss.model.Schematic;



public class AddComponentCommand implements Command, Serializable {
	Schematic scheme;
	Piece piece;
	/*int x;
	 * int y;
	 */
	public AddComponentCommand(Schematic scheme, Piece piece/*Mediator med, int x, int y*/) {
		this.scheme = scheme;
		this.piece = piece;
		/*this.x = x;
		 * this.y = y;
		 * piece = med.getModel().getPiece();
		 */
	}
	
	
	@Override
	public void execute() {
		scheme.addComp(piece);
		//scheme.getMediator().addPieceWithoutUndo(x, y);
	}
	
	@Override
	public void reExecute() {
		scheme.addComp(piece);
		//scheme.getMediator().addPieceToCanvas(piece);
		
		//canvas.addPieceToCanvas(x, y);
	}
	
	@Override
	public void unExecute() {
		scheme.removeComp(piece.getId());
		//scheme.getMediator().removePieceFromCanvas(piece);
	}
}
