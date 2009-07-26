import java.awt.Point;
import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;


public class Piece extends SchematicElement {
	public Point crtPoint;
	public Piece(Element element)
	{
		super(element);
		
	}
			
	public static void addPiece(SVGDocument document, String fileName, int cursorX, int cursorY, String id) throws IOException {
		SVGDocument doc1 = (SVGDocument) Constant.saxFactory.createDocument(fileName);

		doc1.getRootElement().setAttribute("id", id);

		// adaug un tag group
		Element g = doc1.createElementNS(Constant.svgNS, "g");
		g.setAttribute("transform","translate(" + cursorX + "," + cursorY + ")");
		g.appendChild(doc1.getRootElement());

		// aici se unesc
		Node x = document.importNode(g, true);
		document.getDocumentElement().appendChild(x);
		
		EventTarget target = (EventTarget)g;
		target.addEventListener("mousedown", Constant.stateManager.getMouseDownElement(), true);
		target.addEventListener("mouseup", Constant.stateManager.getMouseUpElement(), true);
		target.addEventListener("click", Constant.stateManager.getMouseClickElement(), true);
		

	}
	
	@Override
	public void move(int... destination)
	{
		int x = destination[0];
		int y = destination[1];
		int startX = (int) crtPoint.getX();
		int startY = (int) crtPoint.getY();
		if (Math.abs(x - startX) > PointMatrix.CELL_SIZE ||	Math.abs(y - startY) > PointMatrix.CELL_SIZE )
		{
			if (domElement != null) {
				int dx = MyMath.roundAtStep((x - startX)/Constant.matrix.scale, PointMatrix.CELL_SIZE);
				int dy = MyMath.roundAtStep((y - startY)/Constant.matrix.scale, PointMatrix.CELL_SIZE);
				String attr = ((Element) domElement.getParentNode()).getAttribute("transform");
				TransformTag transform = new TransformTag(attr);
				if (transform.translate != null) {
					transform.translate.x += dx;
					transform.translate.y += dy;
				} else {
					transform.translate = new Translate(dx, dy);
				}

				((Element) domElement.getParentNode()).setAttribute("transform", transform.toString());

//				canvas.repaint();
				// canvas.setDoubleBuffered(true);
				// canvas.setDoubleBufferedRendering(true);
				crtPoint.setLocation(x, y);
			}
		}

	}

	@Override
	protected String displayError() {
		return "No piece selected";
	}


}