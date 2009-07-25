import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.Scrollable;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;


public class MyCanvas {
	public JFrame frame = new JFrame("Canvas");

	static public JPanel panel = new PanelScroll() {
		private static final long serialVersionUID = 8450159075035550085L;

		public boolean isOptimizedDrawingEnabled() {
			return false;
		}
	};


	public MyCanvas(JPanel panel) {
		//			this.panel = panel;
		Constant.panel = panel;
		Constant.canvas.setPreferredSize(new Dimension(2048, 2048));
		Constant.canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		Constant.canvas.setDocument(Constant.domFactory);
		Constant.canvas.setBorder(BorderFactory.createTitledBorder("THE canvas"));

		Constant.canvas.setRecenterOnResize(true);
		Constant.canvas.setDisableInteractions(true);

		Constant.canvas.addMouseListener(Constant.stateManager.getMouseListenerCanvas());
		Constant.canvas.addMouseMotionListener(Constant.stateManager.getMouseMotionListenerCanvas());
		

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

public static void main(String[] args) {
	MyCanvas myCanvas = new MyCanvas(panel);

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
