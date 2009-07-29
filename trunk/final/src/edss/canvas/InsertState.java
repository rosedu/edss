package edss.canvas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import org.w3c.dom.events.Event;

public class InsertState extends State {
	public InsertState(CanvasImpl canvas) {
		super(canvas);
	}

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
		System.out.println(canvas);
		int roundX = MyMath.roundAtStep(arg0.getX() / canvas.matrix.scale, PointMatrix.CELL_SIZE);
		int roundY = MyMath.roundAtStep(arg0.getY() / canvas.matrix.scale, PointMatrix.CELL_SIZE);
		edss.interf.Piece piece = canvas.mediator.addPiece(roundX, roundY);
		if(piece != null)
		{
			if (piece.getId() != null) 
			{
				String fileName = canvas.mediator.getSVG();
				try 
				{
					canvas.addPiece(canvas.domFactory, fileName, roundX, roundY, piece.getId(), piece.getValue());
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
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
