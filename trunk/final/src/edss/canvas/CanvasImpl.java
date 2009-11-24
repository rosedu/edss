package edss.canvas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGRect;

import edss.canvas.constants.Constant;
import edss.canvas.pieces.Wire;
import edss.canvas.transforms.Rotate;
import edss.canvas.transforms.TransformTag;
import edss.interf.Canvas;
import edss.interf.CanvasMediator;

public class CanvasImpl extends Constant implements Canvas
{
	public JScrollPane scrollPane;
	public EventListenerImpl eventListener = new EventListenerImpl(this);

	public static final int WIDTH = 2000;
	public static final int HEIGHT = 2000;

	public JPanel panel = new PanelScroll();

	public CanvasImpl(CanvasMediator mediator)
	{
		this.mediator = mediator;
		mediator.registerCanvas(this);
	}

	public void createNewCanvas(JPanel guiPanel)
	{
		this.domFactory = (SVGDocument) impl.createDocument(svgNS, "svg", null);
		setCanvasVariables(guiPanel, domFactory);
	}

	public void setCanvasVariables(JPanel guiPanel, SVGDocument domFactory)
	{
		this.guiPanel = guiPanel;
		this.guiPanel.add(panel);

		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
		scrollPane = new JScrollPane(panel);
		scrollPane.setAutoscrolls(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				matrix.repaint();
			}
		});
	}

	// JScrollPane
	public Component getCanvas()
	{
		return scrollPane;
	}

	// what?!
	@Override
	public void update()
	{
	}

	// enter states
	@Override
	public void enterInsertState()
	{
		stateManager.enterInsertState();
	}

	@Override
	public void enterPieceState()
	{
		stateManager.enterPieceState();

	}

	@Override
	public void enterWireState()
	{
		stateManager.enterWireState();

	}

	@Override
	public void enterDeleteState()
	{
		stateManager.enterDeleteState();
	}

	// transform
	@Override
	public void scale(int factor)
	{
		// zoom out
		matrix.scale = factor / 100.0;

		AffineTransform at = new AffineTransform();
		at.scale(matrix.scale, matrix.scale);
		canvas.setRenderingTransform(at);
		canvas.repaint();

		int canvH = (int) Math.round(HEIGHT * matrix.scale);
		int canvW = (int) Math.round(WIDTH * matrix.scale);
		canvas.setPreferredSize(new Dimension(canvW, canvH));
		canvas.revalidate();
		panel.setPreferredSize(new Dimension(canvW, canvH));
		panel.revalidate();
		guiPanel.validate();
		canvas.repaint();
		matrix.repaint();
	}

	@Override
	public void rotate(int angle)
	{
		if (stateManager.crtState == stateManager.pieceState)
		{
			Element selected = (Element) stateManager.pieceState.selectedElement.domElement.getElementsByTagName("g").item(0);
			if (selected != null)
			{
				String attr = selected.getAttribute("transform");
				TransformTag tr = new TransformTag(attr);

				if (tr.rotate == null)
				{
					tr.rotate = new Rotate(angle, svgDimension, svgDimension);
				} else
				{
					tr.rotate.x = svgDimension;
					tr.rotate.y = svgDimension;
					tr.rotate.angle += angle;
					tr.rotate.angle %= 360;
				}

				selected.setAttribute("transform", tr.toString());
				tr.rotate.angle *= -1;
				Node text = selected.getElementsByTagName("text").item(0);
				((Element) text.getParentNode()).setAttribute("transform", tr.rotate.toString());

			}
		}
	}

	@Override
	public void openSVG(JPanel guiPanel, String fileName) throws IOException
	{
		domFactory = (SVGDocument) saxFactory.createDocument("file:///" + fileName);
		setCanvasVariables(guiPanel, domFactory);
	}

	@Override
	public void saveSVG(String fileName)
	{
		SVGRect bbox;
		BufferedWriter out;
		String xmlString;
		
		try
		{
			bbox = ((SVGLocatable) domFactory.getDocumentElement()).getBBox();
			domFactory.getRootElement().setAttribute("height", "" + (bbox.getHeight() + 200));
			domFactory.getRootElement().setAttribute("width", "" + (bbox.getWidth() + 200));

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			StringWriter stringWriter = new StringWriter();
			StreamResult result = new StreamResult(stringWriter);
			DOMSource source = new DOMSource(domFactory);
			trans.transform(source, result);
			xmlString = stringWriter.toString();

			out = new BufferedWriter(new FileWriter(fileName));
			out.write(xmlString);
			out.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (TransformerException e)
		{
			e.printStackTrace();
		}
	}

	// TODO scos document
	public void addPiece(SVGDocument document, String fileName, int cursorX, int cursorY, String id, String value) throws IOException
	{
		SVGDocument doc1 = (SVGDocument) Constant.saxFactory.createDocument(fileName);

		doc1.getRootElement().setAttribute("id", id);

		// adaug un tag group
		Element g = doc1.createElementNS(Constant.svgNS, "g");
		g.setAttribute("transform", "translate(" + cursorX + "," + cursorY + ")");
		g.appendChild(doc1.getRootElement());

		// aici se unesc
		Node x = document.importNode(g, true);
		document.getDocumentElement().appendChild(x);

		// conventie piesa de intrare: 2 copii <g>: primul: piesa + textLabel;
		// alDoilea: pinii
		setTextTag(x, id, value);

		EventTarget target = (EventTarget) document.getElementById(id);
		target.addEventListener("mousedown", eventListener.mouseDownListener, true);
		target.addEventListener("mouseup", eventListener.mouseUpListener, true);
		target.addEventListener("click", eventListener.mouseClickListener, true);
	}

	private static void setTextTag(Node x, String text1, String text2)
	{
		NodeList nl = ((Element) x).getElementsByTagName("text");
		for (int i = 0; i < nl.getLength(); i++)
		{
			if (((Element) (nl.item(i).getFirstChild())).getAttribute("id").equals("name"))
				((Element) nl.item(i)).setTextContent(text1);
			else
				((Element) nl.item(i)).setTextContent(text2);
		}
	}

	@Override
	public String getLastSelectedPieceId()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getPreview(String fileName)
	{
		CanvasPreview preview = new CanvasPreview(this);
		return preview.getPreview(fileName);
	}

	@Override
	public void addWire(String id, List<? extends Point> pointList)
	{
		// Wire wire =
		new Wire(this, pointList, id);
	}

	@Override
	public void removeWire(String id)
	{
		Wire wire = new Wire(this, domFactory.getElementById(id));
		wire.delete();
	}
}
