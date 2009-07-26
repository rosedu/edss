package edss.canvas;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PointMatrix extends JPanel {
	final static int CELL_SIZE = 24;
	double scale = 1;
	final double ratio = 2;
	int height = 10;
	int width = 16;
	private int step;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		height = g.getClipBounds().height ;
		width = g.getClipBounds().width;
		//y = g.getClipBounds().y/CELL_SIZE*CELL_SIZE
		step = (int)(CELL_SIZE*scale);
		for (int y = Math.round(g.getClipBounds().y/step*step); y < height + g.getClipBounds().y; y += Math.round(CELL_SIZE * scale) ) {
			for (int x = Math.round(g.getClipBounds().x/step*step); x < width + g.getClipBounds().x; x += Math.round(CELL_SIZE*scale)) {
				g.fillOval(x,y, 2, 2);
			}
		}
		//System.out.println(g.getClipBounds());
	}
	
}
