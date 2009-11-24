package edss.canvas.pieces;

import org.w3c.dom.Element;

import edss.canvas.CanvasImpl;

public abstract class SchematicElement
{
	CanvasImpl canvas;
	public Element domElement;

	public SchematicElement(CanvasImpl canvas)
	{
		domElement = null;
		this.canvas = canvas;
	}

	public SchematicElement(CanvasImpl canvas, Element element)
	{
		this.canvas = canvas;
		domElement = element;
	}

	public void delete()
	{
		if (domElement != null)
		{
			String id = domElement.getAttribute("id");
			domElement.getParentNode().removeChild(domElement);
			domElement = null;
			canvas.mediator.delete(id);
		} else
		{
			System.out.println("null");
		}
	}

	public abstract void move(int... destination);

	protected abstract String displayError();
}
