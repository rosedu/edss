package edss.model;

public class Test {
	public static void main(String[] args) {
		Schematic scheme = new Schematic();
		
		scheme.addComponent(new Piece("basic", "resistor", 0, 0, "1K"));
		scheme.addComponent(new Piece("transistors", "bjt_npn", 10, 10, "generic"));
		scheme.addComponent(new Node(30,30));
		scheme.addWire("R1", "pin1", "N1", "pin1");
		scheme.addWire("Q1", "pin2", "R1", "pin2");
		scheme.addComponent(new Piece("transistors", "mos_dn", 50, 50, "generic"));
		scheme.addWire("Q2", "pin3", "Q1", "pin1");
		
		scheme.save("schema.sch");
		System.out.println(scheme);
	}
}
