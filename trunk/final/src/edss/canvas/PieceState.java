package edss.canvas;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;


public class PieceState extends State {
	public static SchematicElement selectedElement;
	public static SchematicElement movedElement;
	
	public void getMouseDownElementListener(Event evt) {
		Element target = (Element) evt.getCurrentTarget();
		DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
//		if (target.getElementsByTagName("svg").getLength() == 0) {
		if (!target.getNodeName().equals("svg")) {
			// fir
			movedElement = new Wire((Element) target.getFirstChild());
			((Wire) movedElement).selectedSegment = ((Wire) movedElement).points.getSegment(mouseEvent.getClientX(),
																						mouseEvent.getClientY());
			System.out.println("down fir");
		} else {
			// piesa
			movedElement = new Piece(target);
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
		// TODO Auto-generated method stub
		
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0) {
		System.out.println("mouseClicked Canvas - piece mode");
		try {
			Piece.addPiece(Constant.domFactory, "file:///C:/My Documents 1/EDSS/svn/branches/model/svg/bjt_npn.svg", arg0.getX(), arg0.getY(), "dsacascadsca" + Math.random());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void getMouseMovedListener(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseClickElementListener(Event evt) {
		Element target = (Element) evt.getCurrentTarget();
		if (!target.getNodeName().equals("svg")) {
			// fir
			selectedElement = new Wire((Element) target.getFirstChild());
		} else {
			// piesa
			selectedElement = new Piece(target);
		}
		
	}

	public void getMouseUpElementListener(Event evt) {
		movedElement = null;
		
	}

	public void getMouseOutElementListener(Event evt) {
		// TODO Auto-generated method stub
		
	}

	public void getMouseOverElementListener(Event evt) {
		// TODO Auto-generated method stub
		
	}

	public void getKeyTypedListener(KeyEvent arg0) {
		switch(arg0.getKeyChar())
		{
		case 127:
			System.out.println("key - DEL");
//			try {
				System.out.println("key - DEL");
				System.out.println(selectedElement);
				selectedElement.delete();
//			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			break;
		}
	}
}
