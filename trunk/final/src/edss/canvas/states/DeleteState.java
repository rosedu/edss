package edss.canvas.states;

import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.MouseEvent;

import edss.canvas.CanvasImpl;
import edss.canvas.pieces.Piece;
import edss.canvas.pieces.SchematicElement;
import edss.canvas.pieces.Wire;

public class DeleteState extends State
{

	public DeleteState(CanvasImpl canvas)
	{
		super(canvas);
	}

	public void getMouseMovedListener(MouseEvent arg0)
	{
	}

	public void getMouseClickElementListener(Event evt)
	{
		Element target = (Element) evt.getCurrentTarget();
		SchematicElement selectedElement;
		if (!target.getNodeName().equals("svg"))
		{
			// fir
			selectedElement = new Wire(canvas, (Element) target.getFirstChild());
		} else
		{
			// piesa
			selectedElement = new Piece(canvas, target);
		}
		selectedElement.delete();
	}
}
