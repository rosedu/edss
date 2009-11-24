package edss.canvas.states;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;

import edss.canvas.CanvasImpl;
import edss.canvas.pieces.Wire;

public class WireState extends State
{
	/**
	 * doar desenare fire
	 */

	Wire wire;

	public WireState(CanvasImpl canvas)
	{
		super(canvas);
	}

	public void getMouseMovedListener(MouseEvent arg0)
	{
		if (wire != null)
		{
			wire.draw(arg0.getX(), arg0.getY());
		}
	}

	public void getMouseClickElementListener(Event evt)
	{
		Element pin = (Element) evt.getTarget();
		Element piece = (Element) evt.getCurrentTarget();
		if (pin.getAttribute("id").contains("pin"))
		{
			DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
			if (wire == null)
			{
				wire = new Wire(canvas, canvas.domFactory, (int) (mouseEvent.getClientX() / canvas.matrix.scale), (int) (mouseEvent.getClientY() / canvas.matrix.scale), null);
				wire.startPin = pin;
				wire.idStartPin = pin.getAttribute("id");
				wire.idStartPiece = piece.getAttribute("id");
				return;
			} else
			{
				wire.endPin = pin;
				if (wire.startPin != wire.endPin)
				{
					wire.draw(mouseEvent.getClientX(), mouseEvent.getClientY());
					wire.idEndPin = pin.getAttribute("id");
					wire.idEndPiece = piece.getAttribute("id");

					String id = canvas.mediator.addWire(wire.points.pointList, wire.idStartPiece, wire.idStartPin, wire.idEndPiece, wire.idEndPin);
					((Element) wire.line.getParentNode()).setAttribute("id", id);

					wire = null;
					return;
					// TODO eventual sa nu mai fac draw aici
				}
			}
		} else
		{
			if (piece.getNodeName().equals("g") && piece.getFirstChild().getNodeName().equals("polyline"))
			{
				// String id = canvas.mediator.addWire(wire.points.pointList,
				// wire.idStartPiece, wire.idStartPin, wire.idEndPiece,
				// wire.idEndPin);
				// ((Element) wire.line.getParentNode()).setAttribute("id", id);
				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
				Wire tmpWire = new Wire(canvas, (Element) piece.getFirstChild());
				tmpWire.points.splitLine((int) (mouseEvent.getClientX() / canvas.matrix.scale), (int) (mouseEvent.getClientY() / canvas.matrix.scale));
				String id = canvas.mediator.splitWire(piece.getAttribute("id"), (int) (mouseEvent.getClientX() / canvas.matrix.scale), (int) (mouseEvent.getClientY() / canvas.matrix.scale), tmpWire.points.splitList1, tmpWire.points.splitList2, wire.idStartPiece, wire.idStartPin, wire.points.pointList);

				((Element) wire.line.getParentNode()).setAttribute("id", id);

				wire = null;

				// String id = canvas.mediator.splitWire(wire.points.pointList,
				// wire.idStartPiece, wire.idStartPin,
				// (int) (mouseEvent.getClientX() / canvas.matrix.scale), (int)
				// (mouseEvent.getClientY() / canvas.matrix.scale));
			}
		}
	}

	public void getMouseOutElementListener(Event evt)
	{
		Element pin = (Element) evt.getTarget();
		if (pin.getAttribute("id").contains("pin"))
		{
			canvas.guiPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void getMouseOverElementListener(Event evt)
	{
		Element pin = (Element) evt.getTarget();
		if (pin.getAttribute("id").contains("pin"))
		{
			canvas.guiPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
}
