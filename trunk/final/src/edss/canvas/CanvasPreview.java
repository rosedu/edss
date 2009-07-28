package edss.canvas;

import java.io.IOException;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.svg.SVGDocument;

public class CanvasPreview {
	CanvasImpl canvasImpl;
	
	JSVGCanvas canvas = new JSVGCanvas();
	SVGDocument document;
	
	
	public CanvasPreview(CanvasImpl canvasImpl) {
		this.canvasImpl = canvasImpl;
	}
	
	public void createPreview(String fileName) {
		try {
			document = (SVGDocument) Constant.saxFactory.createDocument(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		canvas.setPreferredSize(new Dimension(2048, 2048));
//		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setDocument(document);

		canvas.setRecenterOnResize(true);
		canvas.setDisableInteractions(true);
	}
	
	public JSVGCanvas getPreview(String fileName) {
		createPreview(fileName);
		return canvas;
	}
}
