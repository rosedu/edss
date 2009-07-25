import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;


public class WireState extends State {
	Wire wire;
	
	@Override
	public void draw(SVGDocument document, int type, int cursorX, int cursorY, String id, String... fileName) {
		switch (type) {
		case DOWN:
			wire = new Wire(document, cursorX, cursorY, id);
			break;
			
		case DRAG:
			wire.drawLine(cursorX, cursorY);
			break;
			
		case UP:
			wire = null;
			break;
			
		default:
			System.err.println("Wrong type");
			break;
		}
	}

	@Override
	public void move(int... destination) throws Exception {
		if (wire.selectedSegment == null) {
			throw new Exception(displayError()); 
		}
		wire.selectedSegment.move(destination[0]);
	}

	@Override
	public void select(Element element, Element pin, int... cursorPosition) {
		int cursorX = cursorPosition[0];
		int cursorY = cursorPosition[1];
		wire.domElement = element;
		wire.selectedSegment = wire.getSegment(cursorX, cursorY);
	}

	@Override
	protected String displayError() {
		return "No wire selected";
	}

	@Override
	protected SchematicElement getSchematicElement() {
		return wire;
	}
	
}
