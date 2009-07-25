package edss.model.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edss.model.Connection;
import edss.model.Piece;
import edss.model.Pin;
import edss.model.Schematic;
import edss.model.Wire;

public class RemoveMultipleComponentsCommand implements Command, Serializable {
	Schematic scheme;
	List<Piece> pieces;
	List<Wire> wires;
	
	public RemoveMultipleComponentsCommand(Schematic scheme, List<String> selected){
		this.scheme = scheme;
		pieces = new LinkedList<Piece>();
		wires = new LinkedList<Wire>();
		
		
		Iterator<String> it = selected.iterator();
		
		while (it.hasNext()){
			String id = it.next();
			Wire w = null;
			Piece pc = null;
			if ((w = scheme.getWires().get(id)) != null){
				wires.add(w);
			} else {
				pc = scheme.getComponents().get(id);
				pieces.add(pc);
			}
		}
		
		Iterator<Piece> itp = pieces.iterator();
		
		while (itp.hasNext()){
			Piece pm = itp.next();
			Iterator<Pin> itpins = pm.getPins().values().iterator();
			
			while (itpins.hasNext()){
				Pin pin = itpins.next();
				Iterator<Connection> itc = pin.getConnections().iterator();
				
				while (itc.hasNext()){
					Wire wire = itc.next().getWire();
					if (!wires.contains(wire)){
						wires.add(wire);
					}
				}
			}
		}
	}
	
	@Override
	public void execute() {
		scheme.removeMultipleComponentsWithoutUndo(pieces, wires);
	}

	@Override
	public void unExecute() {
		Iterator<Piece> itp = pieces.iterator();
		while(itp.hasNext()){
			Piece pm = itp.next();
			scheme.addComp(pm);
		}
		
		Iterator<Wire> itw = wires.iterator();
		while(itw.hasNext()){
			Wire w = itw.next();
			scheme.addWireWithoutUndo(w);
		}
	}

}
