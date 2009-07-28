package edss.model.commands;

import java.awt.Point;
import java.util.List;

import edss.model.Schematic;
import edss.model.Wire;

public class RemoveWireCommand implements Command {
	Schematic scheme;
	Wire wire;
	String id;
	List<? extends Point> points;
	
	public RemoveWireCommand(Schematic scheme, String id) {
		this.scheme = scheme;
		this.id = id;
	}
	
	@Override
	public void execute() {
		wire = scheme.getWires().get(id);
		points = wire.getPoints();
		scheme.removeWireWithoutUndo(id);
	}

	@Override
	public void reExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unExecute() {
		scheme.addWireWithoutUndo(wire.getPin1().getPiece().getId(), wire.getPin1().getId(),
				                  wire.getPin2().getPiece().getId(), wire.getPin2().getId(), points);
		//scheme.getMediator().addWireToCanvas(wire);
	}
}
