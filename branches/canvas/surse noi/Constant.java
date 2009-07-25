import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;


public interface Constant {
	CanvasMediator mediator = new CanvasMediator();
	PointMatrix matrix = new PointMatrix();
	
	String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	String parser = XMLResourceDescriptor.getXMLParserClassName();
	SAXSVGDocumentFactory saxFactory = new SAXSVGDocumentFactory(parser);
	DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
	SVGDocument domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);
	
	
	StateManager stateManager = new StateManager();
}
