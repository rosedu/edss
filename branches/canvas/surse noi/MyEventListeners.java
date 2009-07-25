import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

class ClickEventListener implements EventListener{
	@Override
	public void handleEvent(Event evt) {
//		selected = ((Element) evt.getCurrentTarget());
		DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
		Constant.stateManager.select((Element) evt.getCurrentTarget(), (Element) evt.getTarget(), mouseEvent.getClientX(), mouseEvent.getClientY());
	}
}
class MouseUpEventListener implements EventListener {
	
			@Override
			public void handleEvent(Event evt) {
//				Element el = (Element) evt.getCurrentTarget();
//				Element pin = (Element) evt.getTarget();
////				if (pinSelected != pin) {
//					
//					System.out.println("mouseUUUp");
//					pinSelected = null;
////				}

			}
}

class MouseOverPinEventListener implements  EventListener {

	@Override
	public void handleEvent(Event evt) {
		Element pin = (Element) evt.getTarget();
		if (pin.getAttribute("id").contains("pin")) {
			System.out.println("MouseOverPin");
		//	frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
}



class MouseOutPinEventListener implements EventListener {

	@Override
	public void handleEvent(Event evt) {
		Element pin = (Element) evt.getTarget();
		if (pin.getAttribute("id").contains("pin")) {
			System.out.println("MouseOutPin");
		//	frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}


class MouseDownEventListener implements EventListener {

	@Override
	public void handleEvent(Event evt) {
		Element element = (Element) evt.getCurrentTarget();
		System.out.println("Mouse down on image "
				+ element.getAttribute("id"));
//		crtSelection = target;

		Element pin = (Element) evt.getTarget();
		System.out.println(pin.getAttribute("id"));
		if (pin.getAttribute("id").contains("pin")) 
		{
			System.out.println("pin down");
//			pinSelected = pin;
			Constant.stateManager.draw(document, type, cursorX, cursorY, id, fileName);
		}
		
	}
}


