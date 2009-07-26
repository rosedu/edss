package edss.interf;

public interface Model {

	Piece getPiece(String id);
	String getSVG();
	String getId();
	void update();
	void createPiece(String category, String subcategory, String name);
	void addPiece(int x, int y);
	void setLastSelected(String category, String subCategory, String name);
}
