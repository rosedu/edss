package edss.model.commands;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import edss.model.Schematic;
import edss.model.Wire;



public class AddWireCommand implements Command, Serializable {
	Schematic scheme;
	String piece1;
	String pin1;
	String piece2;
	String pin2;
	List<? extends Point> points;
	Wire w;
	
	public AddWireCommand(Schematic scheme, String piece1, String pin1, String piece2, String pin2,
																	List<? extends Point> points) {
		this.scheme = scheme;
		this.piece1 = piece1;
		this.pin1 = pin1;
		this.piece2 = piece2;
		this.pin2 = pin2;
		this.points = points;
	}
	
	@Override
	public void execute() {
		w = scheme.addWireWithoutUndo(piece1, pin1, piece2, pin2, points);
	}
	
	@Override
	public void reExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unExecute() {
		scheme.removeWireWithoutUndo(piece1, pin1, piece2, pin2);
		//scheme.getMediator().removeWireFromCanvas(w.getId());
	}
}
