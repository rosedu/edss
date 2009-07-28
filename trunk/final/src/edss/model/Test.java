package edss.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		Schematic scheme = new Schematic();
		List<Point> l = new LinkedList<Point>();
		l.add(new Point(30,30));
		l.add(new Point(30, 40));
		
		scheme.addComponent(new Piece(scheme, "basic", "resistor", "1K", 0, 0));
		scheme.addComponent(new Piece(scheme, "transistors", "bjt_npn", "generic", 10, 10));
		scheme.addComponent(new Node(scheme, 30,30));
		scheme.addComponent(new Piece(scheme, "transistors", "mos_dn", "generic", 50, 50));
		//scheme.addWire("R1", "pin1", "N1", "pin1");
		//scheme.addWire("Q1", "pin2", "R1", "pin2");
		//scheme.addWire("Q2", "pin3", "Q1", "pin1");
		
		scheme.addWire("Q2", "pin3", "Q1", "pin1", l);
		scheme.save("schema.sch");
		scheme.removeComponent("R1");
		scheme.undo();
		System.out.println(scheme);
	}
}
