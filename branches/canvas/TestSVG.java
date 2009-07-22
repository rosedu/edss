import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
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
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.JSVGCanvas.ScrollAction;
import org.apache.batik.swing.JSVGCanvas.ZoomAction;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
			g.setColor(Color.RED);
			g.drawRect(0, 0, g.getClipBounds().width - 1,
					g.getClipBounds().height - 1);
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
			}
		}, true);

		t.addEventListener("click", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				Element el = (Element) evt.getCurrentTarget();
				System.out.println("Mouse click on image "
						+ el.getAttribute("id"));
				selected = ((Element) el.getParentNode());
			}
		}, true);
	}

	public static void main(String[] args) {
		try {
			// init canvas
			DOMImplementation impl = SVGDOMImplementation
					.getDOMImplementation();
			domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);
			writeSvg(domFactory, "export.svg");

			svgGenerator = new SVGGraphics2D(domFactory);

			// TODO : de setat stroke
			// svgGenerator.setStroke(new Stroke() {
			//				
			// @Override
			// public Shape createStrokedShape(Shape arg0) {
			// // TODO Auto-generated method stub
			// return null;
			// }
			// });

			canvas.setPreferredSize(new Dimension(2500, 2500));
			canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
			canvas.setDocument(domFactory);
			canvas.setBorder(BorderFactory.createTitledBorder("THE canvas"));

			canvas.setRecenterOnResize(true);

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
			JScrollPane scrollPane = new JScrollPane(panel);
			scrollPane.setAutoscrolls(true);
			scrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			// init frame

			frame.setVisible(true);
			frame.setSize(800, 800);
			frame.setContentPane(scrollPane);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.addWindowFocusListener(new WindowFocusListener() {

				@Override
				public void windowLostFocus(WindowEvent arg0) {
					// TODO Auto-generated method stub
					System.err.println("fasdfs");
				}

				@Override
				public void windowGainedFocus(WindowEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
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
	static boolean wireMode = false;

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
					if (crtSelection != null) {
						System.out.println("Drag finished: [" + crtPoint.getX()
								+ ", " + crtPoint.getY() + "] to ["
								+ arg0.getPoint().getX() + ", "
								+ arg0.getPoint().getY() + "]");
						double dx = (arg0.getPoint().getX() - crtPoint.getX())
								/ matrix.scale;
						double dy = (arg0.getPoint().getY() - crtPoint.getY())
								/ matrix.scale;
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
					// if (Math.abs(arg0.getX() - crtPoint.x * matrix.scale) >=
					// PointMatrix.CELL_SIZE * matrix.scale ||
					// Math.abs(arg0.getY() - crtPoint.y * matrix.scale) >=
					// PointMatrix.CELL_SIZE * matrix.scale) {
					// System.out.println(arg0.getPoint()+ " " + crtPoint + " "
					// + matrix.scale);
					// System.out.print(arg0.getX() - crtPoint.x *
					// matrix.scale);
					// System.out.print( " ");
					// System.out.println(arg0.getY() - crtPoint.y *
					// matrix.scale);
					//
					// int roundX = (int) Math.round ( Math.round(arg0.getX()-
					// (arg0.getX()) % (PointMatrix.CELL_SIZE * matrix.scale))/
					// matrix.scale);
					// int roundY = (int) Math.round ( Math.round(arg0.getY() -
					// (arg0.getY()) % (PointMatrix.CELL_SIZE * matrix.scale))/
					// matrix.scale);
					// if (Math.abs(arg0.getX() - crtPoint.x * matrix.scale) >=
					// PointMatrix.CELL_SIZE * matrix.scale &&
					// Math.abs(arg0.getY() - crtPoint.y * matrix.scale) >=
					// PointMatrix.CELL_SIZE * matrix.scale) {
					// svgGenerator.drawLine(crtPoint.x, crtPoint.y, roundX,
					// crtPoint.y);
					// svgGenerator.drawLine(roundX, crtPoint.y, roundX,
					// roundY);
					// } else //if(crtPoint.x == roundX || crtPoint.y == roundY
					// ){
					// {
					// System.out.println("=============================");
					// svgGenerator.drawLine(crtPoint.x, crtPoint.y, roundX,
					// roundY);
					// // }
					// // else
					// // {
					// System.out.println(roundX + " "+roundY + " "+ crtPoint.x+
					// " " +crtPoint.y);
					// System.err.println("=============================");
					// }
					// Element root = domFactory.getDocumentElement();
					// svgGenerator.getRoot(root);
					// crtPoint = new Point(roundX, roundY);
					// canvas.repaint();
					// }
					// int roundX = (int) ((int) (arg0.getX() - arg0.getX()%
					// (PointMatrix.CELL_SIZE * matrix.scale))/ matrix.scale);
					// int roundY = (int) ((int) (arg0.getY() - arg0.getY()%
					// (PointMatrix.CELL_SIZE * matrix.scale))/ matrix.scale);
					// System.out.println(crtPoint + " " + roundX + " " + roundY
					// + " " + matrix.scale);
					// svgGenerator.drawLine(crtPoint.x, crtPoint.y, roundX,
					// roundY);
					//
					// Element root = domFactory.getDocumentElement();
					// svgGenerator.getRoot(root);
					// crtPoint = new Point(roundX, roundY);
					// System.out.println(crtPoint + " " + matrix.scale);

					// test 2
					int dX = (int) (arg0.getX() / matrix.scale - crtPoint.x);	// la scara 1:1
					int dY = (int) (arg0.getY() / matrix.scale - crtPoint.y);
					// roundX roundY la scara 1:1
					int roundX = (int) Math.round(Math.round(arg0.getX() - (arg0.getX()) % 
							(PointMatrix.CELL_SIZE * matrix.scale))	/ matrix.scale);
					int roundY = (int) Math.round(Math.round(arg0.getY() - (arg0.getY()) % 
							(PointMatrix.CELL_SIZE * matrix.scale))	/ matrix.scale);
					System.out.println(roundX + " " + roundY);
					System.out.println(crtPoint);
					
					if (dX >= PointMatrix.CELL_SIZE /* * matrix.scale*/
							|| dY >= PointMatrix.CELL_SIZE /* * matrix.scale*/) {
						if (dX > dY) {
							svgGenerator.drawLine((int) (crtPoint.x * matrix.scale), (int) (crtPoint.y * matrix.scale),
									(int) (roundX * matrix.scale), (int) (crtPoint.y * matrix.scale));
							crtPoint.x = roundX;
						} else {
							svgGenerator.drawLine((int) (crtPoint.x * matrix.scale), (int) (crtPoint.y * matrix.scale),
									(int) (crtPoint.x * matrix.scale), (int) (roundY * matrix.scale));
							crtPoint.y = roundY;
						}

						Element root = domFactory.getDocumentElement();
						svgGenerator.getRoot(root);
						canvas.repaint();
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

					if (crtSelection != null) {
						System.out.println("Drag finished: [" + crtPoint.getX()
								+ ", " + crtPoint.getY() + "] to ["
								+ arg0.getPoint().getX() + ", "
								+ arg0.getPoint().getY() + "]");
						double dx = (arg0.getPoint().getX() - crtPoint.getX())
								/ matrix.scale;
						double dy = (arg0.getPoint().getY() - crtPoint.getY())
								/ matrix.scale;
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

						// canvas.setDoubleBufferedRendering(true);

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
					int roundX = (int) ((int) (arg0.getX() - (arg0.getX())
							% (int) (PointMatrix.CELL_SIZE * matrix.scale)) / matrix.scale);
					int roundY = (int) ((int) (arg0.getY() - (arg0.getY())
							% (int) (PointMatrix.CELL_SIZE * matrix.scale)) / matrix.scale);
					crtPoint = new Point(roundX, roundY);
					// System.out.println(arg0.getPoint());
					System.out.println(roundX + " " + roundY);
				}

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// // TODO : nu aici
				// String attr = ((Element)
				// crtSelection.getParentNode()).getAttribute("transform");
				// TransformTag transform = new TransformTag(attr);
				//				
				// if (transform.scale != null) {
				// transform.scale = new Scale(0.5);
				// } else {
				// transform.scale.amount *= 0.5;
				// }
				//				
				// ((Element)
				// crtSelection.getParentNode()).setAttribute("transform",
				// transform.toString());

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
				ZoomAction zoomAction;
				ScrollAction sa1, sa2;
				switch (arg0.getKeyChar()) {
				case '-':
					/**
					 * zoom out
					 */

					matrix.scale /= matrix.ratio;

					// canvas.getRenderingTransform().scale(5, 5);

					zoomAction = canvas.new ZoomAction(1 / matrix.ratio);
					zoomAction.actionPerformed(null);
					sa1 = canvas.new ScrollDownAction((int) (canvas.getHeight()
							* (matrix.ratio - 1) / 2));
					sa1.actionPerformed(null);
					sa2 = canvas.new ScrollRightAction((int) (canvas.getWidth()
							* (matrix.ratio - 1) / 2));
					sa2.actionPerformed(null);

					canvH = (int) (canvas.getHeight() / matrix.ratio);
					canvW = (int) (canvas.getWidth() / matrix.ratio);
					// canvas.setSize((int) (canvas.getWidth() / matrix.ratio),
					// (int) (canvas.getHeight() / matrix.ratio));
					canvas.setSize(canvW, canvH);
					// panel.setSize((int) (canvas.getWidth() / matrix.ratio),
					// (int) (canvas.getHeight() / matrix.ratio));
					// canvas.setPreferredSize(new Dimension(canvW, canvH));
					// panel.setPreferredSize(new Dimension(canvW, canvH));
					// ((JScrollPane)frame.getContentPane()).revalidate();

					// TODO : aici creste memoria mult
					// panel.setPreferredSize(new
					// Dimension((int)(panel.getWidth() / matrix.ratio), (int)
					// (panel.getHeight()/ matrix.ratio)));
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
					zoomAction = canvas.new ZoomAction(matrix.ratio);
					zoomAction.actionPerformed(null);
					sa1 = canvas.new ScrollUpAction((int) (canvas.getHeight()
							* (matrix.ratio - 1) / 2));
					sa1.actionPerformed(null);
					sa2 = canvas.new ScrollLeftAction((int) (canvas.getWidth()
							* (matrix.ratio - 1) / 2));
					sa2.actionPerformed(null);
					canvH = (int) (canvas.getHeight() * matrix.ratio);
					canvW = (int) (canvas.getWidth() * matrix.ratio);
					canvas.setSize(canvW, canvH);

					// TODO : aici creste memoria mult
					// panel.setPreferredSize(new
					// Dimension((int)(panel.getWidth() * matrix.ratio), (int)
					// (panel.getHeight()* matrix.ratio)));
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
							// tr.rotate.x = 32;
							// tr.rotate.y = 32;
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
				selected = ((Element) el.getParentNode());
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
				// // TODO : de vazut
				// String attr = ((Element) ((Element)
				// evt.getCurrentTarget()).getParentNode()).getAttribute("transform");
				// TransformTag transform = new TransformTag(attr);
				//				
				// if (transform.scale != null) {
				// transform.scale = new Scale(1.5);
				// } else {
				// transform.scale.amount *= 1.5;
				// }
				//				
				// ((Element) ((Element)
				// evt.getCurrentTarget()).getParentNode()).setAttribute("transform",
				// transform.toString());

			}

		}, true);
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