package edss.interf;

public interface Model {

	Piece getPiece(String id);
	Piece getSelectedPiece();
	Piece createPiece(String category, String subcategory, String name);
	String getSVG();
	void update();
	void addPiece(int x, int y);
	void setSelectedPiece(Piece p);
}
