package edss.interf;

public interface Model {

	Piece getPiece(String id);
	String getSVG();
	void update();
	
	void setLastSelected(String category, String subCategory, String name);
	String addPiece(int x, int y);
	
	void saveScheme(String name);
	void openScheme(String name);
}
