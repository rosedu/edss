import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class PointMatrix extends JPanel {
	final static int CELL_SIZE = 32;
	int scale = 1;
	
	int height = 10;
	int width = 16;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		height = g.getClipBounds().height / scale / CELL_SIZE + 1;
		width = g.getClipBounds().width / scale / CELL_SIZE + 1;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				g.fillOval(x * CELL_SIZE * scale, y * CELL_SIZE * scale, 2, 2);
			}
		}
	}
	
}
