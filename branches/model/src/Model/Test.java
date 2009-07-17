package Model;
import java.util.LinkedList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		Schematic scheme = new Schematic();
		
		scheme.addComp(new PieceModel(PieceModel.RESISTOR, 10, 10, "1K"));
		scheme.addComp(new PieceModel(PieceModel.TRANSISTOR_BJT, 10, 50, "NPN"));
		scheme.addComp(new PieceModel(PieceModel.GROUND, 70, 50, "G"));
		
		scheme.addComp(new Node(10, 20));
		scheme.addComp(new PieceModel(PieceModel.RESISTOR, 10, 10, "1K"));
		
		scheme.addWire("R1", "1", "Q1", "C");
		scheme.addWire("G1", "G", "Q1", "E");
		scheme.addWire("N1", "N", "R1", "2");
		scheme.addWire("N1", "N", "Q1", "B");
		scheme.addWire("N1", "N", "R2", "1");
		
		scheme.save("schema.sch");
		
		//scheme.removeComponent("N1");
		/*scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 50, 50, "1K"));
		scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 70, 70, "1K"));
		scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 90, 90, "1K"));
		scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 50, 110, "1K"));
		scheme.addComponent(new PieceModel(PieceModel.DIODE, 70, 30, ""));
		scheme.addComponent(new PieceModel(PieceModel.TRANSISTOR, 70, 110, "NPN"));
		scheme.addComponent(new PieceModel(PieceModel.DC_VOLTAGE_SRC, 10, 10, "5V"));
		
		scheme.removeComponent("R1");
		scheme.addComponent(new PieceModel(PieceModel.RESISTOR, 90, 90, "1K"));*/
		
		System.out.println(scheme);
		
		/*List<String> selected = new LinkedList<String>();
		selected.add("R1");
		selected.add("Q1");
		scheme.addBlackBox(selected);
		System.out.println(scheme);*/
	}
}
