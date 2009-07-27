package edss.canvas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.w3c.dom.events.Event;


public class StateManager {
	private static WireState wireState = new WireState();
	private static PieceState pieceState = new PieceState();
	private static InsertState insertState = new InsertState();

	State crtState = pieceState;
	
	public void enterWireState() {
		crtState = wireState;
	}
	
	public void enterPieceState() {
		crtState = pieceState;
	}
	
	public void enterInsertState() {
		crtState = insertState;
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

	public void getKeyTypedListener(KeyEvent arg0) {
		crtState.getKeyTypedListener(arg0);	
	}
	
}
