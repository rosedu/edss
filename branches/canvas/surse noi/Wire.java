import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;

public class Wire extends SchematicElement{
	WirePoints points;
	Segment selectedSegment;
	Element line;
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
		
		
		//event listener pe line
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
	}
	
	void drawLine(int x, int y) {
		
		points.addPoint(x, y);
		line.setAttribute("points", points.toString());
	}
	
	public Segment getSegment(int x, int y)
	{
		return points.getSegment(x, y);
	}
}
