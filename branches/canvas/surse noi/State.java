import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

public abstract class State {
	public final static int DOWN = 0;
	public final static int DRAG = 1;
	public final static int UP = 2;

	public void delete() throws Exception {

		if (getSchematicElement().domElement != null) {
			getSchematicElement().domElement.removeChild(getSchematicElement().domElement.getFirstChild());
			getSchematicElement().domElement = null;
		} else {
			throw new Exception(displayError());
		}
	}

	public abstract void move(int... destination) throws Exception;// {}

	public abstract void select(Element element, Element pin, int... cursorPosition);// {}

	public abstract void draw(SVGDocument document, int type, int cursorX, int cursorY, String id, String... fileName) throws Exception;// {} // deseneaza fir sau afiseaza fereastra de dialog

	protected abstract String displayError();

	protected abstract SchematicElement getSchematicElement();

}
