package edss.canvas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

public class EventListenerImpl
{
	CanvasImpl canvas;

	public EventListenerImpl(CanvasImpl canvas)
	{
		this.canvas = canvas;
	}

	public EventListener mouseDownListener = new EventListener()
	{

		@Override
		public void handleEvent(Event evt)
		{
			canvas.stateManager.getMouseDownElementListener(evt);
		}
	};

	public EventListener mouseUpListener = new EventListener()
	{

		@Override
		public void handleEvent(Event evt)
		{
			canvas.stateManager.getMouseUpElementListener(evt);
		}
	};

	public EventListener mouseClickListener = new EventListener()
	{

		@Override
		public void handleEvent(Event evt)
		{
			canvas.stateManager.getMouseClickElementListener(evt);
		}
	};

	public EventListener mouseOutListener = new EventListener()
	{

		@Override
		public void handleEvent(Event evt)
		{
			canvas.stateManager.getMouseOutElementListener(evt);
		}
	};

	public EventListener mouseOverListener = new EventListener()
	{

		@Override
		public void handleEvent(Event evt)
		{
			canvas.stateManager.getMouseOverElementListener(evt);
		}
	};

	public MouseMotionListener mouseMotionCanvas = new MouseMotionListener()
	{

		@Override
		public void mouseMoved(MouseEvent arg0)
		{
			canvas.stateManager.getMouseMovedListener(arg0);
		}

		@Override
		public void mouseDragged(MouseEvent arg0)
		{
			canvas.stateManager.getMouseDraggedListener(arg0);
		}
	};

	public MouseListener mouseCanvas = new MouseListener()
	{
		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			canvas.stateManager.getMouseReleasedCanvasListener(arg0);
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			canvas.stateManager.getMousePressedCanvasListener(arg0);
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			canvas.stateManager.getMouseExitedCanvasListener(arg0);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			canvas.stateManager.getMouseEnteredCanvasListener(arg0);
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			canvas.stateManager.getMouseClickedCanvasListener(arg0);
		}
	};

	public KeyListener keyListener = new KeyAdapter()
	{
		@Override
		public void keyTyped(KeyEvent arg0)
		{
			canvas.stateManager.getKeyTypedListener(arg0);
		}
	};

}