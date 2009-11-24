package edss.canvas.states;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.w3c.dom.events.Event;

import edss.canvas.CanvasImpl;

public class State
{
	CanvasImpl canvas;

	public State(CanvasImpl canvas)
	{
		this.canvas = canvas;
	}

	public void getMouseDownElementListener(Event evt)
	{
	}

	public void getMousePressedCanvasListener(MouseEvent arg0)
	{
	}

	public void getMouseExitedCanvasListener(MouseEvent arg0)
	{
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0)
	{
	}

	public void getMouseDraggedListener(MouseEvent arg0)
	{
	}

	public void getMouseEnteredCanvasListener(MouseEvent arg0)
	{
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0)
	{
	}

	public void getMouseMovedListener(MouseEvent arg0)
	{
	}

	public void getMouseClickElementListener(Event evt)
	{
	}

	public void getMouseUpElementListener(Event evt)
	{
	}

	public void getMouseOutElementListener(Event evt)
	{
	}

	public void getMouseOverElementListener(Event evt)
	{
	}

	public void getKeyTypedListener(KeyEvent arg0)
	{
	}

}
