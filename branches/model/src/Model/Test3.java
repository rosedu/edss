package Model;

import java.util.LinkedList;
import java.util.List;

public class Test3 {
	public static void main(String[] args) {
		Schematic scheme = new Schematic();
		
		scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 10, 10, "1K"));
		scheme.addComponent(new PieceModel(PieceModel.TRANSISTOR_BJT, 10, 50, "NPN"));
		scheme.addComponent(new PieceModel(PieceModel.GROUND, 70, 50, "G"));
		
		scheme.addComponent(new Node(10, 20));
		scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 10, 10, "1K"));
		
		scheme.addWire("R1", "1", "Q1", "C");
		scheme.addWire("G1", "G", "Q1", "E");
		scheme.addWire("N1", "N", "R1", "2");
		scheme.addWire("N1", "N", "Q1", "B");
		scheme.addWire("N1", "N", "R2", "1");
		
//		scheme.addComponent(new PieceModel(PieceModel.GROUND, 70, 50, "G"));
//		scheme.removeWire(((Wire)scheme.getWires().values().toArray()[1]).getId());
//		
//		scheme.undo();
//		scheme.undo();
//		System.out.println("\n\n" + scheme + "\n\n");
//		scheme.redo();
		System.out.println("\n\n" + scheme);
		
		List<String> list = new LinkedList<String>();
		list.add("R1");
		list.add("R2");
		list.add("Q1");
		list.add("G1");
		
		scheme.removeMultipleComponents(list);
		System.out.println("\n\n" + scheme);
		
		scheme.undo();
		System.out.println("\n\n" + scheme);
	}
}
