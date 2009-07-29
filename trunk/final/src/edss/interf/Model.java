package edss.interf;

import java.awt.Point;
import java.util.List;

public interface Model {

	Piece getPiece(String id);
	String getSVG();
	void update();
	
	void setLastSelected(String category, String subCategory, String name);
	
	edss.interf.Piece addPiece(int x, int y);
	String addWire(String piece1, String pin1, String piece2, String pin2, List<? extends Point> points);
	void delete(String id);
	
	void saveScheme(String name);
	void openScheme(String name, ModelMediator med);
	
	void rotate(int r, String id);
	List<WireInfo> getWiresInfo(String Id);
	
	String splitWire(String splitId, int x, int y, List<? extends Point> list1,
			List<? extends Point> list2, String idStartPiece,
			String idStartPin, List<? extends Point> newWireList);
}
