package Model.Commands;

import Model.Schematic;
import Model.Wire;

public class RemoveWireCommand implements Command {
	Schematic scheme;
	Wire wire;
	String id;
	
	public RemoveWireCommand(Schematic scheme, String id) {
		this.scheme = scheme;
		this.id = id;
	}
	
	@Override
	public void execute() {
		wire = scheme.getWires().get(id);
		scheme.removeWireWithoutUndo(id);
	}

	@Override
	public void unExecute() {
		scheme.addWireWithoutUndo(wire.getPin1().getPiece().getId(), wire.getPin1().getId(),
				                  wire.getPin2().getPiece().getId(), wire.getPin2().getId());
	}

}
