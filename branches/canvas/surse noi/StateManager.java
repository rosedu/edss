import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;


public class StateManager {
	State crtState;
	
	private static WireState wireState = new WireState();
	private static PieceState pieceState = new PieceState();
	
	public void enterWireState() {
		crtState = wireState;
	}
	
	public void enterPieceState() {
		crtState = pieceState;
	}
	
	public void draw(SVGDocument document, int type, int cursorX, int cursorY, String id, String... fileName) throws Exception {
		crtState.draw(document, type, cursorX, cursorY, id, fileName);
	}
	
	public void delete() throws Exception {
		crtState.delete();
	}
	
	public void move(int... destination) throws Exception {
		crtState.move(destination);
	}
	
	public void select(Element element, Element pin, int... cursorPosition) {
		crtState.select(element, pin, cursorPosition);
	}
	
}
