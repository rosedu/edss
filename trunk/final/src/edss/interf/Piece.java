package edss.interf;

import java.util.Map;

import edss.model.Pin;


public interface Piece {
	String getId();
	String getName();
	String getValue();
	String getSvgURI();
	
	public Map<String, Pin> getPins();
	
	int getX();
	int getY();
	
	void setRotated(int i);
}
