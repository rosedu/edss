package edss.canvas;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;


public class PieceState extends State {
	public SchematicElement selectedElement;
	public SchematicElement movedElement;
	
	public PieceState(CanvasImpl canvas) {
		super(canvas);
	}


	public void getMouseDownElementListener(Event evt) {
		Element target = (Element) evt.getCurrentTarget();
		DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
//		if (target.getElementsByTagName("svg").getLength() == 0) {
		if (!target.getNodeName().equals("svg")) {
			// fir
			movedElement = new Wire(canvas, (Element) target.getFirstChild());
			((Wire) movedElement).selectedSegment = ((Wire) movedElement).points.getSegment(mouseEvent.getClientX(),
																						mouseEvent.getClientY());
			System.out.println("down fir");
		} else {
			// piesa
			movedElement = new Piece(canvas, target);
			((Piece) movedElement).crtPoint = new Point(mouseEvent.getClientX(), mouseEvent.getClientY()); 
			System.out.println("down piesa");
		}		
	}

	public void getMousePressedCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseExitedCanvasListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0) {
		movedElement = null;
		
	}

	public void getMouseDraggedListener(MouseEvent arg0) {
		if(movedElement != null) {
			System.out.println("drag");
			movedElement.move(arg0.getX(), arg0.getY());
			
		}
		
	}

	public void getMouseEnteredCanvasListener(MouseEvent arg0) {
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseMovedListener(MouseEvent arg0) {
	}

	public void getMouseClickElementListener(Event evt) {
		Element target = (Element) evt.getCurrentTarget();
		if (!target.getNodeName().equals("svg")) {
			// fir
			selectedElement = new Wire(canvas, (Element) target.getFirstChild());
		} else {
			// piesa
			selectedElement = new Piece(canvas, target);
			System.out.println("MouseClickPiesa: " + selectedElement.domElement.getNodeName());
		}
		
	}

	public void getMouseUpElementListener(Event evt) {
		movedElement = null;
	}

	public void getMouseOutElementListener(Event evt) {
	}

	public void getMouseOverElementListener(Event evt) {
	}

	public void getKeyTypedListener(KeyEvent arg0) {
		if(selectedElement == null)
			return;
		switch(arg0.getKeyChar())
		{
		case 127:
			selectedElement.delete();
			break;
		
		}
	}
}
