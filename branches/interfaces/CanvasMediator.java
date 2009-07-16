
public interface CanvasMediator {
	// notify
	void popupNotify(String id);
	Piece pieceCreatedNotify(String type, int x, int y); //?
	void wireCreatedNotify(Point[] points);
	void deletePieceNotify(String id);
	void rotatePieceNotify(String id, double angle);
	void translateNotify(String id, int x, int y);
	void textUpdateNotify(String id, String text);
	
	
	// get
	Piece getPiece(String id);
	
	
}
