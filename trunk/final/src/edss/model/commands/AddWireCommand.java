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
		w = new Wire(scheme.getComponents().get(piece1).getPins().get(pin1),
					 scheme.getComponents().get(piece2).getPins().get(pin2),
					 points);
		scheme.addWireWithoutUndo(w);
		//scheme.addWireWithoutUndo(w);
	}
	
	@Override
	public void reExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unExecute() {
		scheme.removeWireWithoutUndo(w.getId());
		//scheme.getMediator().removeWireFromCanvas(w.getId());
	}
	
	public Wire getWire() {
		return w;
	}
}
