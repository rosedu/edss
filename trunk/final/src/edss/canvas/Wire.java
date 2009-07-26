package edss.canvas;

import org.w3c.dom.Element;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;

public class Wire extends SchematicElement{
	WirePoints points;
	Segment selectedSegment;
	Element line;
	Element startPin;
	Element endPin;
	
	
	public Wire(SVGDocument document,int cursorX, int cursorY, String id)
	{
		// add <g> <line ...>
		Element g = document.createElementNS(Constant.svgNS, "g");
		line = document.createElementNS(Constant.svgNS, "polyline");
		line.setAttributeNS(null, "stroke", "blue");
		line.setAttributeNS(null, "stroke-width", "2");
		line.setAttributeNS(null, "fill", "none");
		line.setAttributeNS(null, "id", id);
		g.appendChild(line);
		document.getRootElement().appendChild(g);
		
		//init pct start
		points = new WirePoints(cursorX, cursorY);
		
		EventTarget target = (EventTarget) g;
		target.addEventListener("mousedown", EventListenerImpl.mouseDownListener, true);
		target.addEventListener("mouseup", EventListenerImpl.mouseUpListener, true);
		target.addEventListener("click", EventListenerImpl.mouseClickListener, true);
		
		
/*		//event listener pe line
		NodeList nl = document.getRootElement().getElementsByTagName("polyline");
		EventTarget t = (EventTarget) nl.item(nl.getLength() - 1).getParentNode();
		
		
		
		
		// TODO : de verificat
//		EventTarget t = (EventTarget) g;
		t.addEventListener("click", new ClickEventListener(), true); 
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				System.out.println("click pa fir");
//				selected = ((Element) evt.getCurrentTarget());
//
//			}
//		}, true);

		t.addEventListener("mousedown", new MouseDownListener(), true); 
//				new EventListener() {
//
//			@Override
//			public void handleEvent(Event evt) {
//				System.out.println("fir selectat");
//				wireSelected = new WirePoints(((Element) evt.getCurrentTarget()).getAttribute("points"));
//				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
//				segmentSelected = wireSelected.getSegment(mouseEvent.getClientX(), mouseEvent.getClientY());
//			}
//		}, true);
*/		
	}


	public Wire(Element element) {
		super(element);
		points = new WirePoints(element.getAttribute("points"));

	}


	public void draw(int cursorX, int cursorY)
	{
		int roundX = MyMath.roundAtStep(cursorX / Constant.matrix.scale, PointMatrix.CELL_SIZE);
		int roundY = MyMath.roundAtStep(cursorY / Constant.matrix.scale, PointMatrix.CELL_SIZE);
		points.addPoint(roundX, roundY);
		line.setAttribute("points", points.toString());
	}



	@Override
	public void move(int... destination) {
//		if (selectedSegment == null) {
//			throw new Exception(displayError()); 
//		}
		// TODO : rotunjire destination[0]
		
		if (selectedSegment.direction == Segment.VERTICAL) {
			selectedSegment.move(destination[1] - selectedSegment.a.x);
		} else {
			selectedSegment.move(destination[0] - selectedSegment.a.y);
		}
		domElement.setAttributeNS(null, "points", points.toString());
	}

	
	@Override
	protected String displayError() {
		return "No wire selected";
	}
	
}
