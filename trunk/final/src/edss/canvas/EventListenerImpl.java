package edss.canvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;


public class EventListenerImpl {
	
	public static EventListener mouseDownListener = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Constant.stateManager.getMouseDownElementListener(evt);
		}
	};

	public static EventListener mouseUpListener = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Constant.stateManager.getMouseUpElementListener(evt);
		}
	};

	public static EventListener mouseClickListener = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Constant.stateManager.getMouseClickElementListener(evt);
		}
	};

	public static EventListener mouseOutListener = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Constant.stateManager.getMouseOutElementListener(evt);
		}
	};

	public static EventListener mouseOverListener = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Constant.stateManager.getMouseOverElementListener(evt);
		}
	};
	
	public static MouseMotionListener mouseMotionCanvas = new MouseMotionListener() {

		@Override
		public void mouseMoved(MouseEvent arg0) {
			Constant.stateManager.getMouseMovedListener(arg0);
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			Constant.stateManager.getMouseDraggedListener(arg0);
		}
	};

	public static MouseListener mouseCanvas = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {
			Constant.stateManager.getMouseReleasedCanvasListener(arg0);

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			Constant.stateManager.getMousePressedCanvasListener(arg0);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			Constant.stateManager.getMouseExitedCanvasListener(arg0);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Constant.stateManager.getMouseEnteredCanvasListener(arg0);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Constant.stateManager.getMouseClickedCanvasListener(arg0);
		}
	};
}