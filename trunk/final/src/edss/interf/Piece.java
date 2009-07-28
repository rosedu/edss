package edss.interf;


public interface Piece {
	String getId();
	String getName();
	String getValue();
	String getSvgURI();
	int getX();
	int getY();
	void setRotated(int i);
}
