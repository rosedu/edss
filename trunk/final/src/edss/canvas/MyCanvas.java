package edss.canvas;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;


public class MyCanvas {
	public JFrame frame = new JFrame("Canvas");

	static public JPanel panel = new PanelScroll() {
		private static final long serialVersionUID = 8450159075035550085L;

		public boolean isOptimizedDrawingEnabled() {
			return false;
		}
	};
	
	static JPanel guiPanel; 


	public MyCanvas(JPanel guiPanel) {
		//			this.panel = panel;
		Constant.panel = guiPanel;
		Constant.panel.add(panel);
		Constant.canvas.setPreferredSize(new Dimension(2048, 2048));
		Constant.canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		Constant.canvas.setDocument(Constant.domFactory);
		Constant.canvas.setBorder(BorderFactory.createTitledBorder("THE canvas"));

		Constant.canvas.setRecenterOnResize(true);
		Constant.canvas.setDisableInteractions(true);

		Constant.canvas.addMouseListener(EventListenerImpl.mouseCanvas);
		Constant.canvas.addMouseMotionListener(EventListenerImpl.mouseMotionCanvas);
		
		
		
		Constant.canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == 'w') {
					Constant.stateManager.enterWireState();
				System.out.println("wire mode");
				}
				

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// init matrix
		Constant.matrix.setDoubleBuffered(true);
		Constant.matrix.setBackground(null);
		Constant.matrix.setOpaque(false);

		// init panel

		panel.setLayout(new OverlayLayout(panel));
		panel.add(Constant.matrix);
		panel.add(Constant.canvas);
		final JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setAutoscrolls(true);
		scrollPane
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {


			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Constant.matrix.repaint();
			}

		});

		// init frame

		frame.setVisible(true);
		frame.setSize(800, 800);
		frame.setContentPane(scrollPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

	public static void main(String[] args) {
		MyCanvas myCanvas = new MyCanvas(guiPanel);

	}

}

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
