package edss.model;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wire implements Serializable {
	private Pin pin1;
	private Pin pin2;
	private String id;
	
	private List<? extends Point> points;
	
	public Wire(Pin p1, Pin p2, List<? extends Point> points){
		pin1 = p1;
		pin2 = p2;
		Map<String, Boolean> usedIDs = pin1.getPiece().getScheme().getWireInstances();
		
		int i;
		for (i = 1; ; i++)
			if (usedIDs.get("W" + i) == null)
				break;
		id = "W" + i;
		usedIDs.put(id, true);
		this.points = points;
	}
	
	public Wire(Wire w) {
		pin1 = w.getPin1();
		pin2 = w.getPin2();
		points = w.getPoints();
	}
	
	public String toString(){
		return "wire " + id + " between " + pin1.toString() + " and " + pin2.toString();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Pin getPin1() {
		return pin1;
	}
	
	public void setPin1(Pin pin1) {
		this.pin1 = pin1;
	}
	
	public Pin getPin2() {
		return pin2;
	}

	public void setPin2(Pin pin2) {
		this.pin2 = pin2;
	}

	public List<? extends Point> getPoints() {
		return points;
	}
}
