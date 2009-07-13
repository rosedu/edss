import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;


public class TestSVG {
	
	static String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	static String parser = XMLResourceDescriptor.getXMLParserClassName();
	static SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
	static JSVGCanvas canvas = new JSVGCanvas();
	static SVGDocument domFactory;

	static Document document;
	static int index = 1;

	
	static void displayNode(Node n) {
		System.out.println("Name: " + n.getNodeName());
		System.out.println("Value: " + n.getNodeValue());
		System.out.println("Type: " + n.getNodeType());
		System.out.println("===========================");
	}
	
	static void writeSvg(Document doc, String fileName) throws TransformerException, IOException {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = transFactory.newTransformer();
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		
		StringWriter stringWriter = new StringWriter();
		StreamResult result = new StreamResult(stringWriter);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);
		String xmlString = stringWriter.toString();
		
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(xmlString);
		out.close();
	}
	
	static void appendSVG(SVGDocument target, String name, String transform, int index) throws IOException {
		String uri1 = name;
		SVGDocument doc1 = (SVGDocument) f.createDocument(uri1);
		
		doc1.getRootElement().setAttribute("id", "svg" + index);
		
		// adaug un tag group
		Element g = doc1.createElementNS(svgNS, "g");
		if (!transform.isEmpty()) {
			g.setAttribute("transform", transform);
		}

		// old code: adauga copiii lui <svg> la <g>
//		for (int i = 0; i < doc1.getRootElement().getChildNodes().getLength(); i++) {
//			if (doc1.getRootElement().getChildNodes().item(i).getNodeType() == SVGDocument.ELEMENT_NODE) {
//				g.appendChild(doc1.getRootElement().getChildNodes().item(i).cloneNode(true));
//				doc1.getRootElement().removeChild(doc1.getRootElement().getChildNodes().item(i));
//				i--;
//			}
//		}
		
		g.appendChild(doc1.getRootElement());
		
		// aici se unesc
		Node x = target.importNode(g, true);
		target.getDocumentElement().appendChild(x);
		
		EventTarget t = (EventTarget) target.getElementById("svg" + index);
		t.addEventListener("mousedown", new EventListener() {
			
			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				System.out.println("Mouse down on image " + el.getAttribute("id"));
				crtSelection = el;
			}
		}, true);
	}
	
	public static void main(String[] args) {
		try {
			DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
			domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);

//			appendSVG(domFactory, "file:///C:\\My Documents 1\\EDSS\\TestBatik\\BJT_NPN_symbol_(case).svg", "", index++);
//			appendSVG(domFactory, "file:///C:\\My Documents 1\\EDSS\\TestBatik\\Comparator_symbol.svg", "", index++);
			
			writeSvg(domFactory, "export.svg");
			
			JPanel panel = new JPanel() {
				public boolean isOptimizedDrawingEnabled() {
					return false;
				}
			};
			
			JFrame frame = new JFrame();
			frame.setContentPane(panel);
			PointMatrix matrix = new PointMatrix();
			frame.getContentPane().setLayout(new OverlayLayout(frame.getContentPane()));
			
			matrix.setBackground(null);
			matrix.setOpaque(false);
			
			frame.getContentPane().add(matrix);
			
			frame.getContentPane().add(canvas);
			System.out.println(frame.getContentPane().getLayout());
			System.out.println(frame.getContentPane().getComponents().length);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.pack();
			frame.setSize(900, 900);
			
			canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
			
			document = domFactory;
			registerListeners();
			
			canvas.setDocument(domFactory);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static int i;
	static Element crtSelection = null;
	static Point crtPoint;
	
	static void registerListeners() {
		
		canvas.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (crtSelection != null) {
					// TODO : pentru update in realtime se decomenteaza codul de mai jos
					
//					System.out.println("Drag finished: [" + crtPoint.getX() + ", " + crtPoint.getY() + 
//							"] to [" + arg0.getPoint().getX() + ", " + arg0.getPoint().getY() + "]");
//					double dx = arg0.getPoint().getX() - crtPoint.getX();
//					double dy = arg0.getPoint().getY() - crtPoint.getY();
//					String attr = ((Element) crtSelection.getParentNode()).getAttribute("transform");
//					if (!attr.isEmpty()) {
//						StringTokenizer tok = new StringTokenizer(attr, "(, )");
//						tok.nextToken();
//						dx += Double.parseDouble(tok.nextToken());
//						dy += Double.parseDouble(tok.nextToken());
//					}
//					
//					
//					((Element) crtSelection.getParentNode()).setAttribute("transform", "translate(" + 
//							dx + ", " +
//							dy + ")");
//					
//					canvas.repaint();
//					crtPoint = arg0.getPoint();
//					try {
//						writeSvg(domFactory, "export.svg");
//					} catch (TransformerException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
			
		});
		canvas.addMouseListener(new MouseListener() {
			
			private int pas = 32;

			@Override
			public void mouseReleased(MouseEvent arg0) {
				/** realizeaza translatia piesei
				 */
				if (crtSelection != null) {
					System.out.println("Drag finished: [" + crtPoint.getX() + ", " + crtPoint.getY() + 
							"] to [" + arg0.getPoint().getX() + ", " + arg0.getPoint().getY() + "]");
					double dx = arg0.getPoint().getX() - crtPoint.getX();
					double dy = arg0.getPoint().getY() - crtPoint.getY();
					String attr = ((Element) crtSelection.getParentNode()).getAttribute("transform");
					if (!attr.isEmpty()) {
						StringTokenizer tok = new StringTokenizer(attr, "(, )");
						tok.nextToken();
						dx += Double.parseDouble(tok.nextToken());
						dy += Double.parseDouble(tok.nextToken());
					}
					
					((Element) crtSelection.getParentNode()).setAttribute("transform", "translate(" + 
							dx + ", " +
							dy + ")");
					
					canvas.setDoubleBufferedRendering(true);

					canvas.repaint();
					try {
						writeSvg(domFactory, "export.svg");
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					crtSelection = null;
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				crtPoint = arg0.getPoint();
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/** adauga un svg pe canvas la pozitia unde s-a dat click cu mouse-ul
				 */
				System.out.println("Mouse clicked at [" + arg0.getPoint().getX() + ", " + arg0.getPoint().getY() + "]");
				JFileChooser fcOpen = new JFileChooser();
				try {
					String crtDir = new File(".").getCanonicalPath();
					fcOpen.setCurrentDirectory(new File(crtDir));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int retVal = fcOpen.showOpenDialog(null);
				if (retVal == JFileChooser.APPROVE_OPTION) 
				{
					// Get the file path and pass it to the method
					// that will perform saving
					File file = fcOpen.getSelectedFile();
					try {

						int xul = arg0.getX();
						int yul = arg0.getY();
						xul = xul/pas * pas;
						yul = yul/pas*pas;

						System.out.println(xul + " " + yul);
						appendSVG(domFactory, file.toURI().toURL().toString(), "translate(" + xul + ", " + yul + ")" , index++);
						writeSvg(domFactory, "export.svg");

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		for (i = 1; i < document.getElementsByTagName("svg").getLength(); i++) {
			EventTarget t = (EventTarget) document.getElementsByTagName("svg").item(i);
			
			t.addEventListener("mousedown", new EventListener() {
				
				@Override
				public void handleEvent(Event evt) {
					Element el = (Element) evt.getCurrentTarget();
					System.out.println("Mouse down on image " + el.getAttribute("id"));
					crtSelection = el;
				}
			}, true);
		}
		
	}

}
