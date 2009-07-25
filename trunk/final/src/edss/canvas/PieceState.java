import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;


public class PieceState extends State {
	public static SchematicElement selectedElement;
	public static SchematicElement movedElement;
	public static EventListener mouseDown = new EventListener() {
		
		@Override
		public void handleEvent(Event evt) {
			Element target = (Element) evt.getCurrentTarget();
			if (target.getElementsByTagName("svg").getLength() == 0) {
				// fir
				movedElement = new Wire(target);
				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
				((Wire) movedElement).selectedSegment = ((Wire) movedElement).points.getSegment(mouseEvent.getClientX(), mouseEvent.getClientY()); 
			} else {
				// piesa
				movedElement = new Piece(target);
			}
		}
	};
	public static EventListener mouseUp = new EventListener() {
		
		@Override
		public void handleEvent(Event evt) {
			movedElement = null;
		}
	};
	public static EventListener mouseClick = new EventListener() {
		
		@Override
		public void handleEvent(Event evt) {
			Element target = (Element) evt.getCurrentTarget();
			if (target.getElementsByTagName("svg").getLength() == 0) {
				// fir
				selectedElement = new Wire(target);
			} else {
				// piesa
				selectedElement = new Piece(target);
			}
		}
	};
	
	public static MouseMotionListener mouseMotionCanvas = new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			if(movedElement != null)
				movedElement.move(arg0.getX(), arg0.getY());

		}
	};
	
	public static MouseListener mouseCanvas = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			movedElement = null;
			
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				Piece.addPiece(Constant.domFactory, "file:///C:/My Documents 1/EDSS/svn/branches/model/svg/bjt_npn.svg", arg0.getX(), arg0.getY(), "dsacascadsca");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	public EventListener getMouseDownElement()
	{
		return mouseDown;
	}
	public EventListener getMouseUpElement()
	{
		return mouseUp;
	}
	public EventListener getMouseClickElement()
	{
		return mouseClick;
	}
	
	public MouseListener getMouseListenerCanvas()
	{
		return mouseCanvas;
	}
	
	public MouseMotionListener getMouseMotionListenerCanvas()
	{
		return mouseMotionCanvas;
	}
}
