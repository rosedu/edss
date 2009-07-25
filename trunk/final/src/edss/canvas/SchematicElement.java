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
	public void delete() throws Exception {

		if (domElement != null) {
			domElement.removeChild(domElement.getFirstChild());
			domElement = null;
		} else {
			throw new Exception(displayError());
		}
	}

	public abstract void move(int... destination);

	protected abstract String displayError();
}
