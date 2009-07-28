package edss.canvas;

import java.awt.Point;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;


public class Piece extends SchematicElement {
	public Point crtPoint;
	public Piece(CanvasImpl canvas, Element element)
	{
		super(canvas, element);
	}
//	//TODO scos document	
//	public void addPiece(SVGDocument document, String fileName, int cursorX, int cursorY, String id) throws IOException {
//		SVGDocument doc1 = (SVGDocument) Constant.saxFactory.createDocument(fileName);
//
//		doc1.getRootElement().setAttribute("id", id);
//
//		// adaug un tag group
//		Element g = doc1.createElementNS(Constant.svgNS, "g");
//		g.setAttribute("transform","translate(" + cursorX + "," + cursorY + ")");
//		g.appendChild(doc1.getRootElement());
//
//		// aici se unesc
//		Node x = document.importNode(g, true);
//		document.getDocumentElement().appendChild(x);
//		
//		EventTarget target = (EventTarget) document.getElementById(id);
//		target.addEventListener("mousedown", canvas.eventListener.mouseDownListener, true);
//		target.addEventListener("mouseup", canvas.eventListener.mouseUpListener, true);
//		target.addEventListener("click", canvas.eventListener.mouseClickListener, true);
//	}
	
	@Override
	public void move(int... destination)
	{
		Wire wire;
		
		int x = destination[0];
		int y = destination[1];
		int startX = (int) crtPoint.getX();
		int startY = (int) crtPoint.getY();
		if (Math.abs(x - startX) > PointMatrix.CELL_SIZE * canvas.matrix.scale ||	Math.abs(y - startY) > PointMatrix.CELL_SIZE * canvas.matrix.scale)
		{
			if (domElement != null) {
				int dx = MyMath.roundAtStep((x - startX)/canvas.matrix.scale, PointMatrix.CELL_SIZE);
				int dy = MyMath.roundAtStep((y - startY)/canvas.matrix.scale, PointMatrix.CELL_SIZE);
				String attr = ((Element) domElement.getParentNode()).getAttribute("transform");
				TransformTag transform = new TransformTag(attr);
				if (transform.translate != null) {
					transform.translate.x += dx;
					transform.translate.y += dy;
				} else {
					transform.translate = new Translate(dx, dy);
				}

				((Element) domElement.getParentNode()).setAttribute("transform", transform.toString());

			//	canvas.canvas.repaint();
				// canvas.setDoubleBuffered(true);
				// canvas.setDoubleBufferedRendering(true);
				crtPoint.setLocation(x, y);
				try {
					CanvasImpl.writeSvg(canvas.domFactory, "export.svg");
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	protected String displayError() {
		return "No piece selected";
	}


}
