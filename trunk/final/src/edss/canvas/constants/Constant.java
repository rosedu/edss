package edss.canvas.constants;

import javax.swing.JPanel;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import edss.canvas.CanvasImpl;
import edss.canvas.states.StateManager;
import edss.interf.CanvasMediator;

public class Constant
{
	public static String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	static String parser = XMLResourceDescriptor.getXMLParserClassName();
	public static SAXSVGDocumentFactory saxFactory = new SAXSVGDocumentFactory(parser);
	protected static int svgDimension = 48;

	public CanvasMediator mediator;
	public StateManager stateManager = new StateManager((CanvasImpl) this);
	public JPanel guiPanel;

	public PointMatrix matrix = new PointMatrix();
	protected JSVGCanvas canvas = new JSVGCanvas();

	protected static DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
	public SVGDocument domFactory;

}
