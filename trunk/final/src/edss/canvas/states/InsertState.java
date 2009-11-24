package edss.canvas.states;

import java.awt.event.MouseEvent;
import java.io.IOException;

import edss.canvas.CanvasImpl;
import edss.canvas.constants.MyMath;
import edss.canvas.constants.PointMatrix;

public class InsertState extends State
{
	public InsertState(CanvasImpl canvas)
	{
		super(canvas);
	}

	public void getMouseClickedCanvasListener(MouseEvent arg0)
	{
		int roundX = MyMath.roundAtStep(arg0.getX() / canvas.matrix.scale, PointMatrix.CELL_SIZE);
		int roundY = MyMath.roundAtStep(arg0.getY() / canvas.matrix.scale, PointMatrix.CELL_SIZE);
		edss.interf.Piece piece = canvas.mediator.addPiece(roundX, roundY);
		if (piece != null)
		{
			if (piece.getId() != null)
			{
				String fileName = canvas.mediator.getSVG();
				try
				{
					canvas.addPiece(canvas.domFactory, fileName, roundX, roundY, piece.getId(), piece.getValue());
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
