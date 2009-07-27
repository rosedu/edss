package edss.canvas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.Scrollable;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;

import edss.interf.Canvas;
import edss.interf.CanvasMediator;


@SuppressWarnings("serial")
public class CanvasImpl extends Constant implements Canvas {
	public JFrame frame = new JFrame("Canvas");
	public JScrollPane scrollPane;
	EventListenerImpl eventListener = new EventListenerImpl(this);
	
	
	public JPanel panel = new PanelScroll() {
		public boolean isOptimizedDrawingEnabled() {
			return false;
		}
	};
	
//	static JPanel guiPanel; 


	public CanvasImpl(CanvasMediator mediator) {
		
//		public CanvasImpl(CanvasMediator mediator) {
			
		this.mediator = mediator;
		mediator.registerCanvas(this);
	}
	public void setCanvasVariables(JPanel guiPanel)
	{
//	
////		}
//		
//		this.panel = guiPanel;
//		panel = guiPanel;
//		this.guiPanel = guiPanel;
		guiPanel.add(panel);
		frame.add(panel);
		canvas.setPreferredSize(new Dimension(2048, 2048));
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setDocument(domFactory);
		canvas.setBorder(BorderFactory.createTitledBorder("THE canvas"));

		canvas.setRecenterOnResize(true);
		canvas.setDisableInteractions(true);

		canvas.addMouseListener(eventListener.mouseCanvas);
		canvas.addMouseMotionListener(eventListener.mouseMotionCanvas);
		canvas.addKeyListener(eventListener.keyListener);
		
		

		// init matrix
		matrix.setDoubleBuffered(true);
		matrix.setBackground(null);
		matrix.setOpaque(false);

		// init panel

		panel.setLayout(new OverlayLayout(panel));
		panel.add(matrix);
		panel.add(canvas);
		System.out.println(panel);
		scrollPane = new JScrollPane(panel);
		scrollPane.setAutoscrolls(true);
		scrollPane
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {


			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				matrix.repaint();
			}

		});

		// init frame

//		frame.setVisible(true);
//		frame.setSize(800, 800);
//		frame.setContentPane(scrollPane);
		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

	//JScrollPane
	public Component getCanvas() {
		return scrollPane;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(int factor) {
		// zoom out
		matrix.scale = factor/100.0;

		AffineTransform at = new AffineTransform();
		at.scale(matrix.scale, matrix.scale);
		canvas.setRenderingTransform(at);
		canvas.repaint();


		int canvH = (int) Math.round(canvas.getHeight() / matrix.ratio);
		int canvW = (int) Math.round(canvas.getWidth() / matrix.ratio);
		System.out.println(canvW + " " + canvH);
		canvas.setSize(canvW, canvH);
//		canvas.setPreferredSize(new Dimension(canvW, canvH));
		guiPanel.revalidate();
		canvas.repaint();
		matrix.repaint();
//		pas /= matrix.ratio;
	}
	
	

	@Override
	public void enterInsertState() {
		stateManager.enterInsertState();
		
	}

	@Override
	public void enterPieceState() {
		stateManager.enterPieceState();
		
	}

	@Override
	public void enterWireState() {
		stateManager.enterWireState();
		
	}
	
	@Override
	public void enterDeleteState() {
		stateManager.enterDeleteState();
		
	}

	
	@Override
	public void rotate(int angle) {
		if (stateManager.crtState == stateManager.pieceState)
		{
			Element selected = PieceState.selectedElement.domElement;
			System.out.println(selected);
			if (selected!= null) {
				String attr = selected.getAttribute("transform");
				TransformTag tr = new TransformTag(attr);

				if (tr.rotate == null) {
					tr.rotate = new Rotate(90, 0, 0);
				} else {
					tr.rotate.angle += angle;
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
		}
	}
	
	@Override
	public void openSVG(String fileName) throws IOException {
		domFactory = (SVGDocument) saxFactory.createDocument("file:///" + fileName);
	}

	@Override
	public void saveSVG(String fileName) throws TransformerException, IOException {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = transFactory.newTransformer();
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter stringWriter = new StringWriter();
		StreamResult result = new StreamResult(stringWriter);
		DOMSource source = new DOMSource(domFactory);
		trans.transform(source, result);
		String xmlString = stringWriter.toString();

		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write(xmlString);
		out.close();		
	}
	
	//TODO scos document	
	public void addPiece(SVGDocument document, String fileName, int cursorX, int cursorY, String id) throws IOException {
		SVGDocument doc1 = (SVGDocument) Constant.saxFactory.createDocument(fileName);

		doc1.getRootElement().setAttribute("id", id);

		// adaug un tag group
		Element g = doc1.createElementNS(Constant.svgNS, "g");
		g.setAttribute("transform","translate(" + cursorX + "," + cursorY + ")");
		g.appendChild(doc1.getRootElement());

		// aici se unesc
		Node x = document.importNode(g, true);
		document.getDocumentElement().appendChild(x);
		
		EventTarget target = (EventTarget) document.getElementById(id);
		target.addEventListener("mousedown", eventListener.mouseDownListener, true);
		target.addEventListener("mouseup", eventListener.mouseUpListener, true);
		target.addEventListener("click", eventListener.mouseClickListener, true);
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
		return 96;
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
		return 24;
	}
}
