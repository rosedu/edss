import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Schematic {
	private Map<String, PieceModel> components;
	private Map<String, Wire> wires;
	private double scaled = 1;
	
	public Schematic() {
		components = new HashMap<String, PieceModel>();
		wires = new HashMap<String, Wire>();
	}
	
	public void addComponent(PieceModel piece) {
		components.put(piece.getId(), piece);
	}
	
	public void removeComponent(String id){
		PieceModel piece = components.get(id);

		Iterator<Pin> it = piece.getPins().values().iterator();
		
		while (it.hasNext()) {
			Pin pin = it.next();
			
			while (!pin.getConnections().isEmpty()) {
				Connection c = pin.getConnections().get(0);
								
				removeWire(c.getWire().getId());
			}
		}
		
		components.remove(id);
		PieceModel.instances.get(piece.getType()).remove(id);

		System.out.println("Deleted: " + id);
	}

	public void addWire(String piece1, String pin1, String piece2, String pin2) {
		PieceModel pm1 = components.get(piece1);
		PieceModel pm2 = components.get(piece2);
		Pin p1 = pm1.getPins().get(pin1);
		Pin p2 = pm2.getPins().get(pin2);

		Wire w = new Wire(p1, p2);
		wires.put(w.getId(), w);
		
		p1.addConnection(p2, w);
		p2.addConnection(p1, w);
	}
	
	public void removeWire(String piece1, String pin1, String piece2, String pin2){
		PieceModel pm1 = components.get(piece1);
		PieceModel pm2 = components.get(piece2);

		Pin p1 = pm1.getPins().get(pin1);
		Pin p2 = pm2.getPins().get(pin2);
		
		Wire w = p1.getWire(p2);
		
		removeWire(w.getId());
	}
	
	public void removeWire(String id) {
		Wire w = wires.get(id);
		
		System.out.println("Deleted wire between " + w.getPin1().toString() + " and " + 
				w.getPin2().toString());
		
		w.getPin1().removeConnection(w.getPin2());
		w.getPin2().removeConnection(w.getPin1());
		wires.remove(id);
		Wire.usedIDs.remove(id);
	}
	
	public Map<String, PieceModel> getComponents() {
		return components;
	}
	
	public Map<String, Wire> getWires() {
		return wires;
	}
	
	public double getScaled() {
		return scaled;
	}
	
	public void setScaled(double scaled) {
		this.scaled = scaled;
	}
	
	/*public void addBlackBox(List<String> selected){
		List<PieceModel> list = new LinkedList<PieceModel>();
		
		Iterator<String> it = selected.iterator();
		while (it.hasNext()){
			list.add(components.get(it.next()));
		}
		
		BlackBox bb = new BlackBox(list);
		
		List<Wire> aux = new LinkedList<Wire>();
		Iterator<Pin> itp = bb.getPins().values().iterator();
		while (itp.hasNext()){
			Pin p = itp.next();
			Iterator<Connection> itc = p.getConnections().iterator();
			
			while(itc.hasNext()){
				Connection c = itc.next();
				aux.add(new Wire(c.getWire()));
			}
		}
		
		Iterator<PieceModel> it2 = list.iterator();
		while(it2.hasNext()){
			this.removeComponent(it2.next().getId());
		}
		
		this.addComponent(bb);
		
		Iterator<Wire> itw = aux.iterator();
		while(itw.hasNext()){
			Wire w = itw.next();
			this.addWire(w.getPin1().getPiece().getId(), w.getPin1().getId(), w.getPin2().getPiece().getId(), w.getPin2().getId());
		}
	}*/
	
	public String toString()
	{
		Iterator<PieceModel> itp = components.values().iterator();
		Iterator<Wire> itw = wires.values().iterator();
		StringBuffer sb = new StringBuffer();
		
		while (itp.hasNext()) {
			PieceModel pm = itp.next();
			
			sb.append(pm.toString()).append("\n");
		}
		
		while (itw.hasNext()) {
			Wire w = itw.next();
			sb.append(w.toString()).append("\n");
		}
		return sb.toString();
	}
}