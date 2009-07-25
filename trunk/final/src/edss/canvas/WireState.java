import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;


public class WireState extends State {
	/** doar desenare fire
	 */

	static Wire wire;

	public static EventListener mouseDown = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
		}
	};

	public static EventListener mouseUp = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
		}
	};

	public static EventListener mouseClick = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Element pin = (Element) evt.getTarget();
			if (pin.getAttribute("id").contains("pin")) {
				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
				if (wire == null) {
					wire = new Wire(Constant.domFactory, mouseEvent.getClientX(), mouseEvent.getClientY(), null);
					wire.startPin = pin;
					return;
				} else {
					wire.endPin = pin;
					if (wire.startPin != wire.endPin) {
						wire.draw(mouseEvent.getClientX(), mouseEvent.getClientY());
						wire = null;
						return;
						// TODO eventual sa nu mai fac draw aici
					}
				}
			}
		}
	};

	public static EventListener mouseOut = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Element pin = (Element) evt.getTarget();
			if (pin.getAttribute("id").contains("pin")) {
				Constant.panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	public static EventListener mouseOver = new EventListener() {

		@Override
		public void handleEvent(Event evt) {
			Element pin = (Element) evt.getTarget();
			if (pin.getAttribute("id").contains("pin")) {
				Constant.panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
	};


	public static MouseMotionListener mouseMotionCanvas = new MouseMotionListener() {

		@Override
		public void mouseMoved(MouseEvent arg0) {
			if (wire != null) {
				wire.draw(arg0.getX(), arg0.getY());
			}
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}
	};

	public static MouseListener mouseCanvas = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
	};

	public EventListener getMouseDownElement()
	{
		return mouseDown;
	}
	public EventListener getMouseUpElement()
	{
		return mouseUp;
	}
	public EventListener getMouseClickElement()
	{
		return mouseClick;
	}

	public MouseListener getMouseListenerCanvas()
	{
		return mouseCanvas;
	}

	public MouseMotionListener getMouseMotionListenerCanvas()
	{
		return mouseMotionCanvas;
	}
}
