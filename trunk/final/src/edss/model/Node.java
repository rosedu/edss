package edss.model;

public class Node extends Piece {

	public Node(int x, int y) {
		super("", "node", "", x, y);
	}
	public String toString() {
		String s = "Node " + id + " connected to " + pins.get("pin1").listConnections() + "\n";
		
		return s;
	}
}
