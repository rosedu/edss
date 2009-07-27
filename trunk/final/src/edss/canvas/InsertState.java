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
		int roundX = MyMath.roundAtStep(arg0.getX() / Constant.matrix.scale, PointMatrix.CELL_SIZE);
		int roundY = MyMath.roundAtStep(arg0.getY() / Constant.matrix.scale, PointMatrix.CELL_SIZE);
		String id = Constant.mediator.addPiece(roundX, roundY);
		try {
			Piece.addPiece(Constant.domFactory, fileName, roundX, roundY, id);
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
