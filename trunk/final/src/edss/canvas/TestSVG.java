package edss.canvas;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.Scrollable;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.bridge.UpdateManagerAdapter;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;

@SuppressWarnings("serial")
public class TestSVG {
	
	static String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	static String parser = XMLResourceDescriptor.getXMLParserClassName();
	static SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
	static JSVGCanvas canvas = new JSVGCanvas() {
		public void paint(java.awt.Graphics g) {
			super.paint(g);
//			g.setColor(Color.RED);
//			g.drawRect(0, 0, g.getClipBounds().width - 1,
//					g.getClipBounds().height - 1);
		};
	};
	static SVGDocument domFactory;
	static Document document;
	static int index = 1;
	static JPanel panel = new PanelScroll() {
		public boolean isOptimizedDrawingEnabled() {
			return false;
		}
	};
	static int zoomX = 0;
	static int zoomY = 0;
	static JFrame frame = new JFrame("Canvas");
	static PointMatrix matrix = new PointMatrix();
	private static double pas = PointMatrix.CELL_SIZE * matrix.scale;

	static SVGGraphics2D svgGenerator;
	static Element line;

	static void displayNode(Node n) {
		System.out.println("Name: " + n.getNodeName());
		System.out.println("Value: " + n.getNodeValue());
		System.out.println("Type: " + n.getNodeType());
		System.out.println("===========================");
	}

	static void writeSvg(Document doc, String fileName)
			throws TransformerException, IOException {
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

	static void appendSVG(SVGDocument target, String name, String transform,
			int index) throws IOException {
		String uri1 = name;
		SVGDocument doc1 = (SVGDocument) f.createDocument(uri1);

		doc1.getRootElement().setAttribute("id", "svg" + index);
		
		

		// adaug un tag group
		Element g = doc1.createElementNS(svgNS, "g");
		if (!transform.isEmpty()) {
			g.setAttribute("transform", transform);
		}

		g.appendChild(doc1.getRootElement());

		// aici se unesc
		Node x = target.importNode(g, true);
		target.getDocumentElement().appendChild(x);

		EventTarget t = (EventTarget) target.getElementById("svg" + index);
		t.addEventListener("mousedown", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				System.out.println("Mouse down on image "
						+ el.getAttribute("id"));
				crtSelection = el;
//				pinSelected = el;
				
				Element pin = (Element) evt.getTarget();
				System.out.println(pin.getAttribute("id"));
				if (pin.getAttribute("id").contains("pin")) {
					System.out.println("pin down");
					pinSelected = pin;
					
		
					
				}
			}
		}, true);
		
		t.addEventListener("mouseup", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				Element pin = (Element) evt.getTarget();
//				if (pinSelected != pin) {
					
					System.out.println("mouseUUUp");
					pinSelected = null;
//				}

			}
		}, true);
		
		t.addEventListener("mouseover", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element pin = (Element) evt.getTarget();
				if (pin.getAttribute("id").contains("pin")) {
					System.out.println("schimba");
					frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
					pinSelected = pin;
				}
			}
		}, true);
		
		t.addEventListener("mouseout", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element pin = (Element) evt.getTarget();
				if (pin.getAttribute("id").contains("pin")) {
					System.out.println("unschimba");
					frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					pinSelected = pin;
				}
			}
		}, true);

		t.addEventListener("click", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				System.out.println("Mouse click on image "
						+ el.getAttribute("id"));
				System.out.println(selected);
				selected = ((Element) el.getParentNode());
				System.out.println(selected);
				
			}
		}, true);
		
//		EventTarget pin;
////		for (int i = 1; ; i++) {
////			pin = (EventTarget) target.getElementById("pin" + i);
//			pin = (EventTarget) target.getElementById("path2524");
//			if (pin == null) {
////				break;
//			}
//			
////			System.out.println("Pin" + i);
//			
//			pin.addEventListener("mousedown", new EventListener() {
//				
//				@Override
//				public void handleEvent(Event evt) {
//					Element el = (Element) evt.getCurrentTarget();
//					pinSelected = el;
//					System.out.println("down on pin");
//				}
//			}, true);
//			
//			pin.addEventListener("mouseup", new EventListener() {
//
//				@Override
//				public void handleEvent(Event evt) {
//					System.out.println("up on pin");
//					Element el = (Element) evt.getCurrentTarget();
//					if (pinSelected != el) {
//						pinSelected = null;
//					}
//
//				}
//			}, true);
////		}
		
		
	}

	public static void porneste() {
		
		try {
			// init canvas
			DOMImplementation impl = SVGDOMImplementation
					.getDOMImplementation();
			domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);
			writeSvg(domFactory, "export.svg");

			svgGenerator = new SVGGraphics2D(domFactory);

			//TODO : de setat stroke
			svgGenerator.setStroke(new BasicStroke(2));
			svgGenerator.setColor(Color.red);

			canvas.setPreferredSize(new Dimension(2048, 2048));
			canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
			canvas.setDocument(domFactory);
			canvas.setBorder(BorderFactory.createTitledBorder("THE canvas"));

			canvas.setRecenterOnResize(true);
			canvas.setDisableInteractions(true);
			document = domFactory;

			// init matrix
			matrix = new PointMatrix();
			matrix.setDoubleBuffered(true);
			matrix.setBackground(null);
			matrix.setOpaque(false);

			// init panel

			panel.setLayout(new OverlayLayout(panel));
			panel.add(matrix);
			panel.add(canvas);
			final JScrollPane scrollPane = new JScrollPane(panel);
			scrollPane.setAutoscrolls(true);
			scrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
				
				
				@Override
				public void adjustmentValueChanged(AdjustmentEvent e) {
//					matrix.revalidate();
					//int pas = (int) (PointMatrix.CELL_SIZE * matrix.scale);
					//scrollPane.getVerticalScrollBar().setValue((int)Math.round((0.5 + e.getValue() / pas) * pas));
					matrix.repaint();
				}
			});

			// init frame

			frame.setVisible(true);
			frame.setSize(800, 800);
			frame.setContentPane(scrollPane);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			registerListeners();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	static Element crtSelection = null;
	static Point crtPoint;
	static Element selected = null;
	static Element pinSelected = null;
	static WirePoints wireSelected = null;
	static Segment segmentSelected = null;
	static boolean wireMode = false;
	static boolean dragMode = false;
	static boolean occupied = false;

	static void registerListeners() {

		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				// System.err.println("x = " + arg0.getX()+ " y = " +
				// arg0.getY());
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (!wireMode) {
					if (wireSelected != null) {
						int dx = roundAtStep((arg0.getX() - crtPoint.getX())/matrix.scale, PointMatrix.CELL_SIZE);
						int dy = roundAtStep((arg0.getY() - crtPoint.getY())/matrix.scale, PointMatrix.CELL_SIZE);
//						sada.
						return;
					}
					if((Math.abs(arg0.getX() - crtPoint.x)) > PointMatrix.CELL_SIZE || Math.abs((arg0.getY() - crtPoint.y)) > PointMatrix.CELL_SIZE )
					if (crtSelection != null) {
						System.out.println("Drag finished: [" + crtPoint.getX()
								+ ", " + crtPoint.getY() + "] to ["
								+ arg0.getPoint().getX() + ", "
								+ arg0.getPoint().getY() + "]");
//						double dx = (arg0.getX() - crtPoint.getX())
//								/ (PointMatrix.CELL_SIZE * matrix.scale) * PointMatrix.CELL_SIZE;
//						double dy = (arg0.getY() - crtPoint.getY())
//								/ (PointMatrix.CELL_SIZE * matrix.scale) * PointMatrix.CELL_SIZE;
						int dx = roundAtStep((arg0.getX() - crtPoint.getX())/matrix.scale, PointMatrix.CELL_SIZE);
						int dy = roundAtStep((arg0.getY() - crtPoint.getY())/matrix.scale, PointMatrix.CELL_SIZE);
						String attr = ((Element) crtSelection.getParentNode())
								.getAttribute("transform");
						TransformTag transform = new TransformTag(attr);
						if (transform.translate != null) {
							transform.translate.x += dx;
							transform.translate.y += dy;
						} else {
							transform.translate = new Translate(dx, dy);
						}

						((Element) crtSelection.getParentNode()).setAttribute(
								"transform", transform.toString());

						canvas.repaint();
						// canvas.setDoubleBuffered(true);
						// canvas.setDoubleBufferedRendering(true);
						crtPoint = arg0.getPoint();
						try {
							writeSvg(domFactory, "export.svg");
						} catch (TransformerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					if (pinSelected != null) {
						// test 2
						int dX = Math.abs((int) Math.round(arg0.getX() / matrix.scale - crtPoint.x));	// la scara 1:1
						int dY = Math.abs((int) Math.round(arg0.getY() / matrix.scale - crtPoint.y));

						int roundX = roundAtStep((int) (arg0.getX() / matrix.scale), PointMatrix.CELL_SIZE);
						int roundY = roundAtStep((int) (arg0.getY() / matrix.scale), PointMatrix.CELL_SIZE);

						System.out.println(roundX + " " + roundY);
						System.out.println(crtPoint +"  scale: " + matrix.scale);

						if (dX>= PointMatrix.CELL_SIZE
								|| dY >= PointMatrix.CELL_SIZE) {
							if (dX > dY) {
								drawLine(line, roundX, crtPoint.y);
								crtPoint.x = roundX;
							} else {
								drawLine(line, crtPoint.x, roundY);
								crtPoint.y = roundY;
							}
							canvas.repaint();
						}
					}
				}
			}

		});
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (!wireMode) {
					/**
					 * realizeaza translatia piesei
					 */
					if(Math.abs((arg0.getX() - crtPoint.x)) > PointMatrix.CELL_SIZE || Math.abs((arg0.getY() - crtPoint.y)) > PointMatrix.CELL_SIZE )
					if (crtSelection != null) {
						System.out.println("Drag finished: [" + crtPoint.getX()
								+ ", " + crtPoint.getY() + "] to ["
								+ arg0.getPoint().getX() + ", "
								+ arg0.getPoint().getY() + "]");
						int dx = roundAtStep(arg0.getX() / matrix.scale, PointMatrix.CELL_SIZE);
						int dy = roundAtStep(arg0.getY() / matrix.scale, PointMatrix.CELL_SIZE);
						String attr = ((Element) crtSelection.getParentNode())
								.getAttribute("transform");
						TransformTag transform = new TransformTag(attr);
						if (transform.translate != null) {
							transform.translate.x += dx;
							transform.translate.y += dy;
						} else {
							transform.translate = new Translate(dx, dy);
						}

						((Element) crtSelection.getParentNode()).setAttribute(
								"transform", transform.toString());


						matrix.repaint();
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
				} else {
					crtPoint = null;
				}

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!wireMode) {
					crtPoint = arg0.getPoint();
				} else {

		
					
					int roundX = roundAtStep(arg0.getX() / matrix.scale, PointMatrix.CELL_SIZE);
					int roundY = roundAtStep(arg0.getY() / matrix.scale, PointMatrix.CELL_SIZE);
					crtPoint = new Point(roundX, roundY);
					System.out.println(roundX + " " + roundY);
					// TODO : de mutat in mousedown
					//					<polyline points="0,0 0,20 20,20 20,40 40,40 40,60"
					//					style="fill:white;stroke:red;stroke-width:2"/>


					Element g = domFactory.createElementNS(svgNS, "g");

					line = domFactory.createElementNS(svgNS, "polyline");
					line.setAttributeNS(null, "stroke", "blue");
					line.setAttributeNS(null, "stroke-width", "2");
					line.setAttributeNS(null, "fill", "none");
					g.appendChild(line);
					domFactory.getRootElement().appendChild(g);

					//				EventTarget t = (EventTarget) target.getElementById("svg" + index);
					//				t.addEventListener("mousedown", new EventListener() {
					//
					//					@Override
					//					public void handleEvent(Event evt) {
					//						Element el = (Element) evt.getCurrentTarget();
					//						System.out.println("Mouse down on image "
					//								+ el.getAttribute("id"));
					//						crtSelection = el;
					////						pinSelected = el;
					//						
					//						Element pin = (Element) evt.getTarget();
					//						System.out.println(pin.getAttribute("id"));
					//						if (pin.getAttribute("id").contains("pin")) {
					//							System.out.println("pin down");
					//							pinSelected = pin;
					//							
					//				
					//							
					//						}
					//					}
					//				}, true);

					NodeList nl = domFactory.getRootElement().getElementsByTagName("polyline");
//					EventTarget t = (EventTarget) nl.item(nl.getLength() - 1).getParentNode();
					EventTarget t = (EventTarget) g;
					t.addEventListener("click", new EventListener() {

						@Override
						public void handleEvent(Event evt) {
							System.out.println("click pa fir");
							selected = ((Element) evt.getCurrentTarget());

						}
					}, true);
					
					t.addEventListener("mousedown", new EventListener() {

						@Override
						public void handleEvent(Event evt) {
							System.out.println("fir selectat");
							wireSelected = new WirePoints(((Element) evt.getCurrentTarget()).getAttribute("points"));
							DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;
							segmentSelected = wireSelected.getSegment(mouseEvent.getClientX(), mouseEvent.getClientY());
						}
					}, true);
					
					System.out.println(domFactory.getRootElement().getChildNodes().getLength());


					drawLine(line, roundX, roundY);
				}

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				/**
				 * adauga un svg pe canvas la pozitia unde s-a dat click cu
				 * mouse-ul
				 */

				if (!wireMode) {
					System.out.println("Mouse clicked at ["
							+ arg0.getPoint().getX() + ", "
							+ arg0.getPoint().getY() + "]");
					JFileChooser fcOpen = new JFileChooser();
					try {
						String crtDir = new File(".").getCanonicalPath();
						fcOpen.setCurrentDirectory(new File(crtDir));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int retVal = fcOpen.showOpenDialog(null);
					if (retVal == JFileChooser.APPROVE_OPTION) {
						// Get the file path and pass it to the method
						// that will perform saving
						File file = fcOpen.getSelectedFile();
						try {

							int xul = arg0.getX();
							int yul = arg0.getY();
							xul = (xul - xul % (int) pas);
							yul = (yul - yul % (int) pas);
							System.out.println("pun piesa la coord: " + xul
									+ ", " + yul + ". pas " + pas);
							appendSVG(domFactory, file.toURI().toURL()
									.toString(), "translate(" + xul + ", "
									+ yul + ")", index++);
							

						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {

				}
			}
		});

		canvas.addUpdateManagerListener(new UpdateManagerAdapter() {
			@Override
			public void updateCompleted(UpdateManagerEvent arg0) {
				matrix.repaint();
				canvas.repaint();
			}
		});

		canvas.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				TransformTag tr;
				String attr;
				int canvH, canvW;
				AffineTransform at;
				switch (arg0.getKeyChar()) {
				case '-':
					/**
					 * zoom out
					 */

					matrix.scale /= matrix.ratio;

					at = new AffineTransform();
					at.scale(matrix.scale, matrix.scale);
					canvas.setRenderingTransform(at);
					canvas.repaint();


					canvH = (int) Math.round(canvas.getHeight() / matrix.ratio);
					canvW = (int) Math.round(canvas.getWidth() / matrix.ratio);
					System.out.println(canvW + " " + canvH);
					canvas.setSize(canvW, canvH);
//					canvas.setPreferredSize(new Dimension(canvW, canvH));
					panel.revalidate();
					canvas.repaint();
					matrix.repaint();
					pas /= matrix.ratio;
					break;

				case '=':
					/**
					 * zoom in
					 */
					matrix.scale *= matrix.ratio;
					
					at = new AffineTransform();
					at.scale(matrix.scale, matrix.scale);
					canvas.setRenderingTransform(at);
					canvas.repaint();

					canvH = (int) Math.round(canvas.getHeight() * matrix.ratio);
					canvW = (int) Math.round(canvas.getWidth() * matrix.ratio);
					canvas.setSize(canvW, canvH);
//					canvas.setPreferredSize(new Dimension(canvW, canvH));

					panel.revalidate();
					canvas.repaint();
					matrix.repaint();
					pas *= matrix.ratio;
					break;

				case 'e':
					/**
					 * rotate clockwise
					 */
					System.out.println(selected);
					if (selected != null) {
						attr = selected.getAttribute("transform");
						tr = new TransformTag(attr);

						if (tr.rotate == null) {
							tr.rotate = new Rotate(90, 0, 0);
						} else {
							tr.rotate.angle += 90;
							tr.rotate.angle %= 360;
						}

						if (tr.rotate.angle == 0) {
							tr.rotate = null;
						} else {
							// TODO : eventual de gasit centrul
							tr.rotate.x = PointMatrix.CELL_SIZE;
							tr.rotate.y = PointMatrix.CELL_SIZE;
						}

						selected.setAttribute("transform", tr.toString());
					}
					break;

				case 'q':
					/**
					 * rotate counter-clockwise
					 */
					System.out.println(selected);
					if (selected != null) {
						attr = selected.getAttribute("transform");
						tr = new TransformTag(attr);

						if (tr.rotate == null) {
							tr.rotate = new Rotate(-90, 0, 0);
						} else {
							tr.rotate.angle += -90;
							tr.rotate.angle %= 360;
						}

						if (tr.rotate.angle == 0) {
							tr.rotate = null;
						} else {
							// nu stiu ce era 32
							tr.rotate.x = PointMatrix.CELL_SIZE;
							tr.rotate.y = PointMatrix.CELL_SIZE;
						}

						selected.setAttribute("transform", tr.toString());
					}
					break;

				case 'w':
					wireMode = !wireMode;
					break;
					
				case 127 :
					if(selected != null)
					{
//						selected.getParentNode().removeChild(selected);
						NodeList nl = selected.getChildNodes();
						System.out.println(nl.getLength());
						selected.removeChild(selected.getFirstChild());
						canvas.repaint();
						selected = null;
					}
					break;
				case 's':
					try {
						writeSvg(domFactory, "export.svg");
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
				
				
			}
		});

		for (int i = 1; i < document.getElementsByTagName("svg").getLength(); i++) {
			registerEvent(i);
		}

	}

	static void registerEvent(int index) {
		EventTarget t = (EventTarget) document.getElementsByTagName("svg")
				.item(index);

		t.addEventListener("click", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				System.out.println("Mouse click on image "
						+ el.getAttribute("id"));
				System.out.println(selected);
				selected = ((Element) el.getParentNode());
				System.out.println(selected);
				
			}
		}, true);

		t.addEventListener("mousedown", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				System.out.println("Mouse down on image "
						+ el.getAttribute("id"));
				crtSelection = el;
			}
		}, true);

		t.addEventListener("mouseover", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (dragMode) {
					occupied = true;
				}

			}

		}, true);
		
		t.addEventListener("mouseout", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (dragMode) {
					occupied = false;
				}
			}

		}, true);
	}

	static int roundAtStep(double x, int step) {
		int result;
		int offset = (int) (x % step);
		result = (int) (x - offset);
		if (offset > step / 2) {
			result += step; 
		}
		return result;
		
	}

	static void drawLine(Element line, int x, int y) {
		WirePoints points = new WirePoints(line.getAttribute("points"));
		points.addPoint(x, y);
		line.setAttribute("points", points.toString());
	}
	
}

@SuppressWarnings("serial")
class PanelScroll extends JPanel implements Scrollable {
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 70;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 20;
	}

}
