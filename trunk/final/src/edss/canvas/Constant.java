package edss.canvas;

import javax.swing.JPanel;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import edss.interf.CanvasMediator;


public class Constant {
	static String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	static String parser = XMLResourceDescriptor.getXMLParserClassName();
	static SAXSVGDocumentFactory saxFactory = new SAXSVGDocumentFactory(parser);
	static int svgDimension = 48;
	
	CanvasMediator mediator;
	StateManager stateManager = new StateManager((CanvasImpl) this);
	JPanel guiPanel;
	
	PointMatrix matrix = new PointMatrix();
	JSVGCanvas canvas = new JSVGCanvas();
	
	static DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
	SVGDocument domFactory;
	
}
