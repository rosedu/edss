package Model;

public class Node extends PieceModel {

	public Node(int x, int y) {
		super(PieceModel.NODE, x, y, "");
	}
	
	public String toString() {
		String s = "Node " + id + " connected to " + pins.get("N").listConnections() + "\n";
		
		return s;
	}
}
