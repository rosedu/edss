package edss.model;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edss.model.commands.AddComponentCommand;
import edss.model.commands.AddWireCommand;
import edss.model.commands.CommandManager;
import edss.model.commands.RemoveComponentCommand;
import edss.model.commands.RemoveMultipleComponentsCommand;
import edss.model.commands.RemoveWireCommand;
import edss.model.commands.RotateComponentCommand;


public class Schematic implements Serializable {
	private Map<String, Piece> components;
	private Map<String, Wire> wires;
	private double scaled = 1;
	private CommandManager commMan;
	
	public Schematic() {
		components = new HashMap<String, Piece>();
		wires = new HashMap<String, Wire>();
		commMan = new CommandManager();
	}
	
	public void addComponent(Piece piece) {
		commMan.doCommand(new AddComponentCommand(this, piece));
	}
	
	public void addComp(Piece piece) {
		components.put(piece.getId(), piece);
	}
	
	public void removeComponent(String id) {
		commMan.doCommand(new RemoveComponentCommand(this, id));
	}
	
	public void removeComp(String id){
		Piece piece = components.get(id);

		Iterator<Pin> it = piece.getPins().values().iterator();
		
		while (it.hasNext()) {
			Pin pin = it.next();
			
			while (!pin.getConnections().isEmpty()) {
				Connection c = pin.getConnections().get(0);
								
				removeWireWithoutUndo(c.getWire().getId());
			}
		}
		
		components.remove(id);
		Piece.instances.get(piece.getType()).remove(id);

		System.out.println("Deleted: " + id);
	}
	
	public void removeMultipleComponents(List<String> selected){
		commMan.doCommand(new RemoveMultipleComponentsCommand(this, selected));
	}
	
	public void removeMultipleComponentsWithoutUndo(List<Piece> auxComps, List<Wire>auxWires){		
		Iterator<Piece> itp = auxComps.iterator();
		while (itp.hasNext()){
			removeComp(itp.next().getId());
		}
		
		Iterator<Wire> itw = auxWires.iterator();
		while (itw.hasNext()){
			Wire w = itw.next();
			if (wires.get(w.getId()) != null){
				removeWireWithoutUndo(w.getId());
			}
		}
	}

	public void addWire(String piece1, String pin1, String piece2, String pin2) {
		commMan.doCommand(new AddWireCommand(this, piece1, pin1, piece2, pin2));
	}
	
	public void addWireWithoutUndo(String piece1, String pin1, String piece2, String pin2) {
		Piece pm1 = components.get(piece1);
		Piece pm2 = components.get(piece2);
		Pin p1 = pm1.getPins().get(pin1);
		Pin p2 = pm2.getPins().get(pin2);

		Wire w = new Wire(p1, p2);
		wires.put(w.getId(), w);
		
		p1.addConnection(p2, w);
		p2.addConnection(p1, w);
	}
	
	public void addWireWithoutUndo(Wire w) {
		Pin p1 = w.getPin1();
		Pin p2 = w.getPin2();
		
		p1.addConnection(p2, w);
		p2.addConnection(p1, w);
		
		wires.put(w.getId(), w);
	}
	
	public void removeWire(String id) {
		commMan.doCommand(new RemoveWireCommand(this, id));
	}
	
	public void removeWireWithoutUndo(String piece1, String pin1, String piece2, String pin2){
		Piece pm1 = components.get(piece1);
		Piece pm2 = components.get(piece2);

		Pin p1 = pm1.getPins().get(pin1);
		Pin p2 = pm2.getPins().get(pin2);
		
		Wire w = p1.getWire(p2);
		
		removeWireWithoutUndo(w.getId());
	}
	
	public void removeWireWithoutUndo(String id) {
		Wire w = wires.get(id);
		
		System.out.println("Deleted wire between " + w.getPin1().toString() + " and " + 
				w.getPin2().toString());
		
		w.getPin1().removeConnection(w.getPin2());
		w.getPin2().removeConnection(w.getPin1());
		wires.remove(id);
		Wire.usedIDs.remove(id);
	}
	
	public void rotateComponent(String id, int r){
		commMan.doCommand(new RotateComponentCommand(this, id, r));
	}
	
	public Map<String, Piece> getComponents() {
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
	
	public void save(String filename) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Schematic load(String filename) {
		Schematic scheme = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			scheme = (Schematic)in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return scheme;
	}
	
	public void undo() {
		commMan.undo();
	}
	
	public void redo() {
		commMan.redo();
	}
	
	public String toString()
	{
		Iterator<Piece> itp = components.values().iterator();
		Iterator<Wire> itw = wires.values().iterator();
		StringBuffer sb = new StringBuffer();
		
		while (itp.hasNext()) {
			Piece pm = itp.next();
			
			sb.append(pm.toString()).append("\n");
		}
		
		while (itw.hasNext()) {
			Wire w = itw.next();
			sb.append(w.toString()).append("\n");
		}
		return sb.toString();
	}
}