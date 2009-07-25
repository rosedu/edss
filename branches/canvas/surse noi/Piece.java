import java.awt.Cursor;
import java.awt.Point;
import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;


public class Piece extends SchematicElement {
	public Point crtPoint;
	
	public Piece(SVGDocument document, String fileName, String transform, String id) throws IOException {
		SVGDocument doc1 = (SVGDocument) Constant.saxFactory.createDocument(fileName);

		doc1.getRootElement().setAttribute("id", id);

		// adaug un tag group
		Element g = doc1.createElementNS(Constant.svgNS, "g");
		if (!transform.isEmpty()) {
			g.setAttribute("transform", transform);
		}
		g.appendChild(doc1.getRootElement());

		// aici se unesc
		Node x = document.importNode(g, true);
		document.getDocumentElement().appendChild(x);

		
		//TODO de inlocuit cu: doc1.getRootElement()
		EventTarget t = (EventTarget) document.getElementById(id);
		t.addEventListener("mousedown", new MouseDownEventListener(), true);
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				Element el = (Element) evt.getCurrentTarget();
//				System.out.println("Mouse down on image "
//						+ el.getAttribute("id"));
//				crtSelection = el;
////				pinSelected = el;
//				
//				Element pin = (Element) evt.getTarget();
//				System.out.println(pin.getAttribute("id"));
//				if (pin.getAttribute("id").contains("pin")) {
//					System.out.println("pin down");
//					pinSelected = pin;
//					
//		
//					
//				}
//			}
//		}, true);
		
		t.addEventListener("mouseup", new MouseUpEventListener(), true);
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				Element el = (Element) evt.getCurrentTarget();
//				Element pin = (Element) evt.getTarget();
////				if (pinSelected != pin) {
//					
//					System.out.println("mouseUUUp");
//					pinSelected = null;
////				}
//
//			}
//		}, true);
		
		t.addEventListener("mouseover", new MouseOverPinEventListener(), true);
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				Element pin = (Element) evt.getTarget();
//				if (pin.getAttribute("id").contains("pin")) {
//					System.out.println("schimba");
//					frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
//					pinSelected = pin;
//				}
//			}
//		}, true);
		
		t.addEventListener("mouseout", new MouseOutPinEventListener(), true);
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				Element pin = (Element) evt.getTarget();
//				if (pin.getAttribute("id").contains("pin")) {
//					System.out.println("unschimba");
//					frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//					pinSelected = pin;
//				}
//			}
//		}, true);

		t.addEventListener("click", new ClickEventListener(), true);
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				Element el = (Element) evt.getCurrentTarget();
//				System.out.println("Mouse click on image "
//						+ el.getAttribute("id"));
//				System.out.println(selected);
//				selected = ((Element) el.getParentNode());
//				System.out.println(selected);
//				
//			}
//		}, true);
	}
}
