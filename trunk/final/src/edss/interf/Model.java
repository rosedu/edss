package edss.interf;

import java.awt.Point;
import java.util.List;

public interface Model {

	Piece getPiece(String id);
	String getSVG();
	void update();
	
	void setLastSelected(String category, String subCategory, String name);
	
	String addPiece(int x, int y);
	String addWire(String piece1, String pin1, String piece2, String pin2, List<? extends Point> points);
	
	void saveScheme(String name);
	void openScheme(String name, ModelMediator med);
	
	void rotate(int r, String id);
	List<WireInfo> getWiresInfo();
}
