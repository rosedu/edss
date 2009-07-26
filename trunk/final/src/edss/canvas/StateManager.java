import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.w3c.dom.events.EventListener;


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
	

	public EventListener getMouseDownElement()
	{
		return crtState.getMouseDownElement();
	}
	public EventListener getMouseUpElement()
	{
		return crtState.getMouseUpElement();
	}
	public EventListener getMouseClickElement()
	{
		return crtState.getMouseClickElement();
	}
	public MouseListener getMouseListenerCanvas()
	{
		return crtState.getMouseListenerCanvas();
	}
	
	public MouseMotionListener getMouseMotionListenerCanvas()
	{
		return crtState.getMouseMotionListenerCanvas();
	}
}
