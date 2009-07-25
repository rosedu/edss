import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;


public class PieceState extends State {

	public Piece piece;

	@Override
	public void draw(SVGDocument document, int type, int cursorX, int cursorY, String id, String... fileName) throws Exception 
	{

		switch(type)
		{
		//TODO cu keyboard
		case CLICK:
			
			String transform = "translate(" + cursorX + "," + cursorY + ")";
			piece = new Piece(document, fileName[0], transform, id);
			break;
			
		case DOWN:

		case DRAG:
			move(cursorX, cursorY);
			break;

			//TODO
		case UP:
			piece = null;
			break;
		}


	}

	@Override
	public void move(int... destination) throws Exception 
	{
		int x = destination[0];
		int y = destination[1];
		int startX = (int) piece.crtPoint.getX();
		int startY = (int) piece.crtPoint.getY();
		if (Math.abs(x - startX) > PointMatrix.CELL_SIZE ||	Math.abs(y - startY) > PointMatrix.CELL_SIZE )
		{
			if (piece.domElement != null) {
				int dx = MyMath.roundAtStep((x - startX)/Constant.matrix.scale, PointMatrix.CELL_SIZE);
				int dy = MyMath.roundAtStep((y - startY)/Constant.matrix.scale, PointMatrix.CELL_SIZE);
				String attr = ((Element) piece.domElement.getParentNode()).getAttribute("transform");
				TransformTag transform = new TransformTag(attr);
				if (transform.translate != null) {
					transform.translate.x += dx;
					transform.translate.y += dy;
				} else {
					transform.translate = new Translate(dx, dy);
				}

				((Element) piece.domElement.getParentNode()).setAttribute("transform", transform.toString());

//				canvas.repaint();
				// canvas.setDoubleBuffered(true);
				// canvas.setDoubleBufferedRendering(true);
				piece.crtPoint.setLocation(x, y);
			}
		}

	}

	@Override
	public void select(Element element, int... cursorPosition) {
		piece.domElement = element;
	}

	@Override
	protected String displayError() {
		return "No piece selected";
	}

	@Override
	protected SchematicElement getSchematicElement() {
		return piece;
	}
}
