package edss.canvas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.swing.BorderFactory;
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
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGRect;


import edss.interf.Canvas;
import edss.interf.CanvasMediator;


@SuppressWarnings("serial")
public class CanvasImpl extends Constant implements Canvas {
//	public JFrame frame = new JFrame("Canvas");
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
	
	public void createNewCanvas(JPanel guiPanel) {
		 this.domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);
		 setCanvasVariables(guiPanel, domFactory);
	}
	
	public void setCanvasVariables(JPanel guiPanel, SVGDocument domFactory)
	{
//	
////		}
//		
//		this.panel = guiPanel;
//		panel = guiPanel;
		this.guiPanel = guiPanel;
		guiPanel.add(panel);
//		frame.add(panel);
		canvas.setPreferredSize(new Dimension(700, 700));
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setDocument(domFactory);
		canvas.setBorder(BorderFactory.createTitledBorder("THE canvas"));

		canvas.setRecenterOnResize(true);
		canvas.setDisableInteractions(true);

		canvas.addMouseListener(eventListener.mouseCanvas);
		canvas.addMouseMotionListener(eventListener.mouseMotionCanvas);
		canvas.addKeyListener(eventListener.keyListener);
//		 canvas.setDoubleBuffered(true);
		

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


	int canvH = (int) Math.round(2000 * matrix.scale);
	int canvW = (int) Math.round(2000 * matrix.scale);
	System.out.println("#### Width"+canvW + "Height " + canvH + "Ratio="+ matrix.scale);

	canvas.setPreferredSize(new Dimension(canvW, canvH));
	canvas.revalidate();


	panel.setPreferredSize(new Dimension(canvW, canvH));
	panel.revalidate();

	//guiPanel.setPreferredSize(new Dimension(canvW, canvH));


	guiPanel.validate();




	canvas.repaint();
	matrix.repaint();
	}
	
//	@Override
//	public void scale(int factor) {
//		// zoom out
//		matrix.scale = factor/100.0;
//
//		AffineTransform at = new AffineTransform();
//		at.scale(matrix.scale, matrix.scale);
//		canvas.setRenderingTransform(at);
//		canvas.repaint();
//
//
//		int canvH = (int) Math.round(canvas.getHeight() / matrix.ratio);
//		int canvW = (int) Math.round(canvas.getWidth() / matrix.ratio);
//		System.out.println(canvW + " " + canvH);
//		canvas.setSize(canvW, canvH);
//		guiPanel.validate();
//		canvas.repaint();
//		matrix.repaint();
//	}
	
	

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
			Element selected = (Element) stateManager.pieceState.selectedElement.domElement.getElementsByTagName("g").item(0);
				//domFactory.getElementById(lastSelectedId);
			System.out.println(selected.getFirstChild().getNodeName());
			if (selected != null) {
				String attr = selected.getAttribute("transform");
				TransformTag tr = new TransformTag(attr);

				if (tr.rotate == null) {
					tr.rotate = new Rotate(angle, svgDimension, svgDimension);
				} else {
					tr.rotate.x = svgDimension;
					tr.rotate.y = svgDimension; 
					tr.rotate.angle += angle;
					tr.rotate.angle %= 360;
				}

				selected.setAttribute("transform", tr.toString());
				tr.rotate.angle *= -1;
				Node text = selected.getElementsByTagName("text").item(0);
				((Element) text.getParentNode()).setAttribute("transform",tr.rotate.toString());

			}		
		}
	}
	
	@Override
	public void openSVG(JPanel guiPanel, String fileName) throws IOException {
		System.out.println(guiPanel);
		domFactory = (SVGDocument) saxFactory.createDocument("file:///" + fileName);
		setCanvasVariables(guiPanel, domFactory);
//		setCanvasVariables(guiPanel, ((JSVGCanvas) getPreview(fileName)).getSVGDocument());
	}

	@Override
	public void saveSVG(String fileName) throws TransformerException, IOException {
		SVGRect bbox = ((SVGLocatable)domFactory.getDocumentElement()).getBBox();
		domFactory.getRootElement().setAttribute("height", ""+bbox.getHeight());
		domFactory.getRootElement().setAttribute("width",""+bbox.getWidth());
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
	public void addPiece(SVGDocument document, String fileName, int cursorX, int cursorY, String id, String value) throws IOException {
		SVGDocument doc1 = (SVGDocument) Constant.saxFactory.createDocument(fileName);

		doc1.getRootElement().setAttribute("id", id);

		// adaug un tag group
		Element g = doc1.createElementNS(Constant.svgNS, "g");
		g.setAttribute("transform","translate(" + cursorX + "," + cursorY + ")");
		g.appendChild(doc1.getRootElement());

		// aici se unesc
		Node x = document.importNode(g, true);
		document.getDocumentElement().appendChild(x);
		
		
		
		//conventie piesa de intrare: 2 copii <g>: primul: piesa + textLabel; alDoilea: pinii
		setTextTag(x, id, value);
		
		EventTarget target = (EventTarget) document.getElementById(id);
		target.addEventListener("mousedown", eventListener.mouseDownListener, true);
		target.addEventListener("mouseup", eventListener.mouseUpListener, true);
		target.addEventListener("click", eventListener.mouseClickListener, true);
	}
	
	private static void setTextTag(Node x, String text1, String text2) {
		NodeList nl = ((Element) x).getElementsByTagName("text");
		for (int i = 0; i < nl.getLength(); i++) {
			if(((Element)(nl.item(i).getFirstChild())).getAttribute("id").equals("name"))
				((Element)nl.item(i)).setTextContent(text1);
			else 
				((Element)nl.item(i)).setTextContent(text2);
		}	
	}
	
	@Override
	public String getLastSelectedPieceId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Component getPreview(String fileName) {
		CanvasPreview preview = new CanvasPreview(this);
		return preview.getPreview(fileName);
	}

	@Override
	public void addWire(String id, List<? extends Point> pointList) {
		Wire wire = new Wire(this, pointList, id);
	}

	@Override
	public void removeWire(String id) {
		Wire wire = new Wire(this, domFactory.getElementById(id));
		wire.delete();
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
