package edss.canvas;

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
	
	public void getMouseDownElementListener(Event evt) {
		// TODO Auto-generated method stub
		
	}

	public void getMousePressedCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseExitedCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseDraggedListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseEnteredCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseMovedListener(MouseEvent arg0) {
		if (wire != null) {
			wire.draw(arg0.getX(), arg0.getY());
		}		
	}

	public void getMouseClickElementListener(Event evt) {
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

	public void getMouseUpElementListener(Event evt) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseOutElementListener(Event evt) {
		Element pin = (Element) evt.getTarget();
		if (pin.getAttribute("id").contains("pin")) {
			Constant.panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}		
	}

	public void getMouseOverElementListener(Event evt) {
		Element pin = (Element) evt.getTarget();
		if (pin.getAttribute("id").contains("pin")) {
			Constant.panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}		
	}
}
