package Model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Wire implements Serializable {
	private Pin pin1;
	private Pin pin2;
	private String id;
	public static Map<String, Boolean> usedIDs = new HashMap<String, Boolean>();
	
	public Wire(Pin p1, Pin p2){
		pin1 = p1;
		pin2 = p2;
		
		int i;
		for (i = 1; ; i++)
			if (usedIDs.get("W" + i) == null)
				break;
		id = "W" + i;
		usedIDs.put(id, true);
	}
	
	public Wire(Wire w){
		pin1 = w.getPin1();
		pin2 = w.getPin2();
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
}
