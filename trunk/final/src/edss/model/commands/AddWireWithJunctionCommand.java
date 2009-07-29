package edss.model.commands;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import edss.model.Node;
import edss.model.Pin;
import edss.model.Schematic;
import edss.model.Wire;

public class AddWireWithJunctionCommand implements Command, Serializable {
	private Wire w;
	private Wire newWire;
	private Wire split1, split2;
	private Schematic scheme;
	private Node n;
	private int x, y;
	private String piece1;
	private String pin1;
	private List<? extends Point> points;
	private List<? extends Point> splitList1;
	private List<? extends Point> splitList2;
	
	public AddWireWithJunctionCommand(List<? extends Point> points, int x, int y, 
			     List<? extends Point> splitList1, List<? extends Point> splitList2,
			     Schematic scheme, String piece1, String pin1, String idWire) {
		w = scheme.getWires().get(idWire);
		this.scheme = scheme;
		this.x = x;
		this.y = y;
		this.piece1 = piece1;
		this.pin1 = pin1;
		this.points = points;
		this.splitList1 = splitList1;
		this.splitList2 = splitList2;
	}
	
	@Override
	public void execute() {
		List<? extends Point> list = w.getPoints();
		
		scheme.removeWireWithoutUndo(w.getId());
		
		n = new Node(scheme, x, y);
		Pin nodePin = (Pin)n.getPins().values().toArray()[0];
		
		split1 = new Wire(w.getPin1(), nodePin, splitList1);
		split2 = new Wire(w.getPin2(), nodePin, splitList2);
		newWire = new Wire(scheme.getComponents().get(piece1).getPins().get(pin1), 
						   nodePin, points);
		
		
		
		scheme.addComp(n);
		scheme.addWireWithoutUndo(split1);
		scheme.addWireWithoutUndo(split2);
		scheme.addWireWithoutUndo(newWire);
		
		scheme.getMediator().removeWire(w.getId());
		scheme.getMediator().addNode(x, y);
		scheme.getMediator().addWire(split1.getId(), split1.getPoints());
		scheme.getMediator().addWire(split2.getId(), split2.getPoints());
	}

	@Override
	public void reExecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unExecute() {
		// TODO Auto-generated method stub

	}

	public Wire getNewWire() {
		return newWire;
	}

}
