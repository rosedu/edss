package edss.canvas;

import java.awt.event.KeyEvent;

import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.MouseEvent;

public class DeleteState extends State {

	public DeleteState(CanvasImpl canvas) {
		super(canvas);
	}

	public void getMouseDownElementListener(Event evt) {
	}

	public void getMousePressedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseExitedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseDraggedListener(MouseEvent arg0) {
	}

	public void getMouseEnteredCanvasListener(MouseEvent arg0) {
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0) {

	}

	public void getMouseMovedListener(MouseEvent arg0) {
	}

	public void getMouseClickElementListener(Event evt) {
		Element target = (Element) evt.getCurrentTarget();
		SchematicElement selectedElement ;
		if (!target.getNodeName().equals("svg")) {
			// fir
			selectedElement = new Wire(canvas, (Element) target.getFirstChild());
		} else {
			// piesa
			selectedElement = new Piece(canvas, target);
		}
		selectedElement.delete();
	}

	public void getMouseUpElementListener(Event evt) {
	}

	public void getMouseOutElementListener(Event evt) {
	}

	public void getMouseOverElementListener(Event evt) {
	}

	public void getKeyTypedListener(KeyEvent arg0) {
	}
}
