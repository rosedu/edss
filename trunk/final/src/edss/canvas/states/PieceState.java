package edss.canvas.states;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;

import edss.canvas.CanvasImpl;
import edss.canvas.pieces.Piece;
import edss.canvas.pieces.SchematicElement;
import edss.canvas.pieces.Wire;

public class PieceState extends State
{
	public SchematicElement selectedElement;
	public SchematicElement movedElement;

	public PieceState(CanvasImpl canvas)
	{
		super(canvas);
	}

	public void getMouseDownElementListener(Event evt)
	{
		Element target = (Element) evt.getCurrentTarget();
		DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
		// if (target.getElementsByTagName("svg").getLength() == 0) {
		if (!target.getNodeName().equals("svg"))
		{
			// fir
			movedElement = new Wire(canvas, (Element) target.getFirstChild());
			((Wire) movedElement).selectedSegment = ((Wire) movedElement).points.getSegment((int) (mouseEvent.getClientX() / canvas.matrix.scale), (int) (mouseEvent.getClientY() / canvas.matrix.scale));
			System.out.println("down fir");
		} else
		{
			// piesa
			movedElement = new Piece(canvas, target);
			((Piece) movedElement).crtPoint = new Point(mouseEvent.getClientX(), mouseEvent.getClientY());
			System.out.println("down piesa");
		}
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0)
	{
		movedElement = null;
	}

	public void getMouseDraggedListener(MouseEvent arg0)
	{
		if (movedElement != null)
		{
			System.out.println("drag");
			movedElement.move((int) (arg0.getX() / canvas.matrix.scale), (int) (arg0.getY() / canvas.matrix.scale));
		}
	}

	public void getMouseClickElementListener(Event evt)
	{
		Element target = (Element) evt.getCurrentTarget();
		if (!target.getNodeName().equals("svg"))
		{
			// fir
			selectedElement = new Wire(canvas, (Element) target.getFirstChild());
		} else
		{
			// piesa
			selectedElement = new Piece(canvas, target);
			System.out.println("MouseClickPiesa: " + selectedElement.domElement.getNodeName());
		}

	}

	public void getMouseUpElementListener(Event evt)
	{
		// Element target = (Element) evt.getCurrentTarget();
		// if (!target.getNodeName().equals("svg")) {
		// // fir
		// ((Wire)movedElement).selectedSegment = null;
		// }
		movedElement = null;
	}

	public void getKeyTypedListener(KeyEvent arg0)
	{
		if (selectedElement == null)
			return;
		switch (arg0.getKeyChar())
		{
			case 127:
				selectedElement.delete();
				break;

		}
	}
}
