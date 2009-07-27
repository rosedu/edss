package edss.canvas;

import org.w3c.dom.Element;


public abstract class SchematicElement {
	CanvasImpl canvas;
	Element domElement;
	
	public SchematicElement(CanvasImpl canvas) {
		domElement = null;
		this.canvas = canvas;
	}
	
	public SchematicElement(CanvasImpl canvas,Element element)
	{
		this.canvas = canvas;
		domElement = element;
	}


	public void delete() {

		System.out.println("ss");
		if (domElement != null) {
			System.out.println("ss");
			System.out.println(domElement.getNodeName());
			domElement.getParentNode().removeChild(domElement);
			domElement = null;
		} else {
			System.out.println("null");
		}
	}

	public abstract void move(int... destination);

	protected abstract String displayError();
}
