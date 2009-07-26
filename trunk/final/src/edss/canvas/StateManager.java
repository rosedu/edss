import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;


public class StateManager {
	private static WireState wireState = new WireState();
	private static PieceState pieceState = new PieceState();

	State crtState = pieceState;
	
	public void enterWireState() {
		crtState = wireState;
	}
	
	public void enterPieceState() {
		crtState = pieceState;
	}

	public void getMouseDownElementListener(Event evt) {
		crtState.getMouseDownElementListener(evt);
		
	}

	public void getMousePressedCanvasListener(MouseEvent arg0) {
		crtState.getMousePressedCanvasListener(arg0);
		
	}

	public void getMouseExitedCanvasListener(MouseEvent arg0) {
		crtState.getMouseExitedCanvasListener(arg0);		
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0) {
		crtState.getMouseReleasedCanvasListener(arg0);		
	}

	public void getMouseDraggedListener(MouseEvent arg0) {
		crtState.getMouseDraggedListener(arg0);		
	}

	public void getMouseEnteredCanvasListener(MouseEvent arg0) {
		crtState.getMouseEnteredCanvasListener(arg0);		
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0) {
		crtState.getMouseClickedCanvasListener(arg0);		
	}

	public void getMouseMovedListener(MouseEvent arg0) {
		crtState.getMouseMovedListener(arg0);		
	}

	public void getMouseOverElementListener(Event evt) {
		crtState.getMouseOverElementListener(evt);		
	}

	public void getMouseOutElementListener(Event evt) {
		crtState.getMouseOutElementListener(evt);		
	}

	public void getMouseClickElementListener(Event evt) {
		crtState.getMouseClickElementListener(evt);		
	}

	public void getMouseUpElementListener(Event evt) {
		crtState.getMouseUpElementListener(evt);		
	}
	
}
