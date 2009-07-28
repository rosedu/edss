package edss.model;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edss.interf.Model;
import edss.interf.WireInfo;
import edss.med.Mediator;

public class Test2 {
	public static void main(String[] args) {
		Mediator med = new Mediator();
		Model model = new ModelImpl(med);
		
		Schematic scheme = ((ModelImpl)model).getScheme();
		
		scheme.addComponent(new Piece(scheme, "basic", "resistor", "1K", 0, 0));
		scheme.addComponent(new Piece(scheme, "transistors", "bjt_npn", "generic", 10, 10));
		scheme.addComponent(new Node(scheme, 30,30));
		scheme.addComponent(new Piece(scheme, "transistors", "mos_dn", "generic", 50, 50));
		//scheme.addWire("R1", "pin1", "N1", "pin1");
		//scheme.addWire("Q1", "pin2", "R1", "pin2");
		//scheme.addWire("Q2", "pin3", "Q1", "pin1");
		
		model.addWire("Q2", "pin3", "Q1", "pin1", new LinkedList<Point>());
		model.addWire("Q2","pin1", "Q1", "pin2", new LinkedList<Point>());
		model.addWire("R1", "pin2", "Q2", "pin2", new LinkedList<Point>());
		
		List<WireInfo> l = model.getWiresInfo("Q2");
		Iterator<WireInfo> i = l.iterator();
		while (i.hasNext()) {
			WireInfo wi = i.next();
			System.out.println(wi.getWireId()+" "+wi.getEnd());
		}
	}
}
