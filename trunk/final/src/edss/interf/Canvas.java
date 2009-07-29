package edss.interf;

import java.awt.Component;
import java.awt.Point;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.xml.transform.TransformerException;

import org.w3c.dom.svg.SVGDocument;

public interface Canvas {

	void update();

	void scale(int factor);

	void rotate(int angle);
	
	
	void saveSVG(String fileName) throws TransformerException, IOException;
	
	public void enterWireState();
	public void enterPieceState();
	public void enterInsertState();
	public void enterDeleteState();

	Component getCanvas();
	Component getPreview(String fileName);
	
	public void setCanvasVariables(JPanel guiPanel, SVGDocument domFactory);
	
	//intoarce id-ul ultimei piese selectate
	//de avut grija sa se puna la null daca se intra in alt mod
	String getLastSelectedPieceId();

	
	public void createNewCanvas(JPanel guiPanel);
	public void openSVG(JPanel guiPanel, String fileName) throws IOException;
	
	public void addWire(String id, List<? extends Point> pointList);
	public void removeWire(String id);


}
