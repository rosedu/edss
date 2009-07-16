import java.io.Serializable;


public class Connection implements Serializable {

	private Pin pin;
	private Wire wire;

	public Connection(Pin pin, Wire wire){
		this.pin = pin;
		this.wire = wire;
	}
	
	public Pin getPin() {
		return pin;
	}

	public void setPin(Pin pin) {
		this.pin = pin;
	}

	public Wire getWire() {
		return wire;
	}

	public void setWire(Wire wire) {
		this.wire = wire;
	}
}
