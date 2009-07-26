package edss.canvas;

import org.w3c.dom.Element;


public abstract class SchematicElement {
	Element domElement;
	
	public SchematicElement() {
		domElement = null;
	}
	
	public SchematicElement(Element element)
	{
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
//			throw new Exception(displayError());
		}
	}

	public abstract void move(int... destination);

	protected abstract String displayError();
}
