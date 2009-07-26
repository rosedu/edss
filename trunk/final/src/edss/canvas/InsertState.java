package edss.canvas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import org.w3c.dom.events.Event;

public class InsertState extends State {
	public void getMouseDownElementListener(Event evt) {
	}

	public void getMousePressedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseExitedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseReleasedCanvasListener(MouseEvent arg0) {
	}

	public void getMouseDraggedListener(MouseEvent arg0) {
	}

	public void getMouseEnteredCanvasListener(MouseEvent arg0) {
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0) {
		String fileName = Constant.mediator.getSVG();
		String id = Constant.mediator.getId();
		try {
			Piece.addPiece(Constant.domFactory, fileName, arg0.getX(), arg0.getY(), id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getMouseMovedListener(MouseEvent arg0) {
	}

	public void getMouseClickElementListener(Event evt) {
	}

	public void getMouseUpElementListener(Event evt) {
	}

	public void getMouseOutElementListener(Event evt) {
	}

	public void getMouseOverElementListener(Event evt) {
	}

	public void getKeyTypedListener(KeyEvent arg0) {
	}
}
