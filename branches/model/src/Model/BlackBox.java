package Model;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class BlackBox extends PieceModel {
	List<PieceModel> components;
	List<Wire> wires;
	
	public BlackBox(List<PieceModel> selected) {
		components = new LinkedList<PieceModel>();
		wires = new LinkedList<Wire>();
		
		Iterator i = selected.iterator();
		while(i.hasNext()){
			Object o = i.next();
			if (o instanceof PieceModel) 
				components.add((PieceModel)o);
			else wires.add(new Wire((Wire)o));
			
		}
		
		pins = new HashMap<String, Pin>();
		System.out.println("Name for your BlackBox: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		id = name;
		int nr = 0;
		
		Iterator<PieceModel> it = components.iterator();
		while (it.hasNext()){
			PieceModel component = it.next();
			Map<String, Pin> pins = component.getPins();
			Iterator<Pin> itp = pins.values().iterator();
			
			while (itp.hasNext()){
				Pin pin = itp.next();
				Iterator<Connection> itp2 = pin.getConnections().iterator();
				
				while (itp2.hasNext()){
					Connection c = itp2.next();
					Pin p = c.getPin();
					if (!components.contains(p.getPiece())){
						nr++;
						Pin pinbb = new Pin(id + nr, this);
						this.addPin(pinbb);
						pin.addConnection(pinbb, new Wire(pin, pinbb));
						pin.removeConnection(p);
						pinbb.addConnection(p, new Wire(p, pinbb));
						break;
					}
				}
			}
		}
	}
}
