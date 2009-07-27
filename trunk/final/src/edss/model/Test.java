package edss.model;

public class Test {
	public static void main(String[] args) {
		Schematic scheme = new Schematic();
		
		scheme.addComponent(new Piece("basic", "resistor", "1K", 0, 0));
		scheme.addComponent(new Piece("transistors", "bjt_npn", "generic", 10, 10));
		scheme.addComponent(new Node(30,30));
		scheme.addWire("R1", "pin1", "N1", "pin1");
		scheme.addWire("Q1", "pin2", "R1", "pin2");
		scheme.addComponent(new Piece("transistors", "mos_dn", "generic", 50, 50));
		scheme.addWire("Q2", "pin3", "Q1", "pin1");
		
		scheme.save("schema.sch");
		scheme.removeComponent("R1");
		scheme.undo();
		System.out.println(scheme);
	}
}
