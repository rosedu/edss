package edss.canvas;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;

import edss.main.Main;

public class Wire extends SchematicElement{
	WirePoints points;
	Segment selectedSegment;
	Element line;
	Element startPin;
	Element endPin;
	String idStartPin;
	String idEndPin;
	String idStartPiece;
	String idEndPiece;
	
	
	public Wire(CanvasImpl canvas, SVGDocument document, int cursorX, int cursorY, String id)
	{
		super(canvas);
		// add <g> <polyline ...>
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
		target.addEventListener("mousedown", canvas.eventListener.mouseDownListener, true);
		target.addEventListener("mouseup", canvas.eventListener.mouseUpListener, true);
		target.addEventListener("click", canvas.eventListener.mouseClickListener, true);
		
		//TODO anunt mediatorul ca am desenat linie
		//canvas.mediator.drawLine(cursorX, cursorY);
	}
	
	public Wire(CanvasImpl canvas, List<? extends Point> pointList, String id) {
		this(canvas, canvas.domFactory, 0, 0, id);
		points.pointList = (LinkedList<MyPoint>) pointList; 
		line.setAttributeNS(null, "points", points.toString());
	}


	public Wire(CanvasImpl canvas, Element element) {
		super(canvas,element);
		points = new WirePoints(element.getAttribute("points"));
		System.out.println(points);
	}


	public void draw(int cursorX, int cursorY)
	{
		int roundX = MyMath.roundAtStep(cursorX / canvas.matrix.scale, PointMatrix.CELL_SIZE);
		int roundY = MyMath.roundAtStep(cursorY / canvas.matrix.scale, PointMatrix.CELL_SIZE);
		points.addPoint(roundX, roundY);
		line.setAttribute("points", points.toString());
		//TODO anunt mediatorul ca am desenat linie
		//canvas.mediator.drawLine(cursorX, cursorY);
	}



	@Override
	public void move(int... destination) {
		// TODO : rotunjire destination[0]
		
		if (selectedSegment == null) {
			return;
		}
		
		if (selectedSegment.direction == Segment.VERTICAL) {
			selectedSegment.move(destination[1]);
		} else {
			selectedSegment.move(destination[0]);
		}
		domElement.setAttributeNS(null, "points", points.toString());
		//TODO anunt mediatorul ca am mutat linie
		//canvas.mediator.moveLine(destination);
	}

	
	@Override
	protected String displayError() {
		return "No wire selected";
	}
	
}
