package edss.interf;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JPanel;
import javax.xml.transform.TransformerException;

public interface Canvas {

	void update();

	void scale(int factor);

	void rotate(int angle);
	
	
	void saveSVG(String fileName) throws TransformerException, IOException;
	void openSVG(String fileName) throws IOException;
	
	public void enterWireState();
	public void enterPieceState();
	public void enterInsertState();
	public void enterDeleteState();

	Component getCanvas();
	
	public void setCanvasVariables(JPanel guiPanel);
	
	//intoarce id-ul ultimei piese selectate
	//de avut grija sa se puna la null daca se intra in alt mod
	String getLastSelectedPieceId();


}
