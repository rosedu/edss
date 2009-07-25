package edss.model;
import java.io.Serializable;
import java.util.*;

public class Pin implements Serializable {
	protected int x, y;
	protected int globalX, globalY;
	protected String id;
	protected String name;
	
	protected List<Connection> connections;
	protected Piece piece;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Pin(String id, String name, Piece piece){
		this.id = id;
		this.piece = piece;
		this.name = name;
		
		connections = new LinkedList<Connection>();
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getGlobalX() {
		return globalX;
	}
	
	public void setGlobalX(int globalX) {
		this.globalX = globalX;
	}
	
	public int getGlobalY() {
		return globalY;
	}
	
	public void setGlobalY(int globalY) {
		this.globalY = globalY;
	}
	
	public List<Connection> getConnections() {
		return connections;
	}
	
	public void addConnection(Pin pin, Wire wire) {
		connections.add(new Connection(pin, wire));
	}
	
	public Wire getWire(Pin p){
		Iterator<Connection> it = connections.iterator();
		while (it.hasNext()){
			Connection c = it.next();
			if (c.getPin().equals(p)){
				return c.getWire();
			}
		}
		return null;
	}
	
	public void removeConnection(Pin pin){
		Iterator<Connection> it = connections.iterator();
		
		while (it.hasNext()){
			Connection c = it.next();
			if (pin.equals(c.getPin())){
				connections.remove(c);
				break;
			}
		}
	}
	
	public void removeAllConnections(){
		Iterator<Connection> it = connections.iterator();
		while (it.hasNext()){
			Connection c = it.next();
			Pin p = c.getPin();
			p.removeConnection(this);
		}
		connections.clear();
	}
	
	public String listConnections() {
		StringBuffer sb = new StringBuffer();

		if (connections.isEmpty())
			sb.append("floating");
		else {
			Iterator<Connection> itc = connections.iterator();

			while (itc.hasNext())
			{
				Connection con = itc.next();

				sb.append(con.getPin().toString());
				sb.append("(wire ").append(con.getWire().getId()).append(") ");
			}
		}
		
		return sb.toString();
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		/**/
		
		sb.append("[").append(getPiece().getId()).append(" ").append(id).append("-")
			.append(name).append("]");
		return sb.toString();
	}
}
