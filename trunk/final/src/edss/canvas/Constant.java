package edss.canvas;

import javax.swing.JPanel;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;


public class Constant {
//	CanvasMediator mediator = new CanvasMediator();
	static PointMatrix matrix = new PointMatrix();
	
	static String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	static String parser = XMLResourceDescriptor.getXMLParserClassName();
	static SAXSVGDocumentFactory saxFactory = new SAXSVGDocumentFactory(parser);
	static DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
	static SVGDocument domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);
	
	static JSVGCanvas canvas = new JSVGCanvas();
	static JPanel panel;
	static StateManager stateManager = new StateManager();
}
