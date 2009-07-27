package edss.interf;

public interface Model {

	Piece getPiece(String id);
	String getSVG();
	void update();
	String addPiece(int x, int y);
	void setLastSelected(String category, String subCategory, String name);
}
