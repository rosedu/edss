import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PointMatrix extends JPanel {
	final static int CELL_SIZE = 25;
	double scale = 1;
	
	int height = 10;
	int width = 16;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		height = g.getClipBounds().height ;
		width = g.getClipBounds().width;
		
		for (int y = g.getClipBounds().y/CELL_SIZE*CELL_SIZE; y < height + g.getClipBounds().y; y += CELL_SIZE*scale) {
			for (int x = g.getClipBounds().x/CELL_SIZE*CELL_SIZE; x < width + g.getClipBounds().x; x += CELL_SIZE*scale) {
				g.fillOval(x,y, 2, 2);
			}
			
		}
		System.out.println(g.getClipBounds());
	}
	
}
