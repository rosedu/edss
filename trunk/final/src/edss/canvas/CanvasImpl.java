package edss.canvas;

import java.awt.geom.AffineTransform;

import edss.interf.Canvas;
import edss.interf.CanvasMediator;


public class CanvasImpl implements Canvas {

	CanvasMediator mediator;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale(int factor) {
		// zoom out
		Constant.matrix.scale = factor/100.0;

		AffineTransform at = new AffineTransform();
		at.scale(Constant.matrix.scale, Constant.matrix.scale);
		Constant.canvas.setRenderingTransform(at);
		Constant.canvas.repaint();


		int canvH = (int) Math.round(Constant.canvas.getHeight() / Constant.matrix.ratio);
		int canvW = (int) Math.round(Constant.canvas.getWidth() / Constant.matrix.ratio);
		System.out.println(canvW + " " + canvH);
		Constant.canvas.setSize(canvW, canvH);
//		canvas.setPreferredSize(new Dimension(canvW, canvH));
		Constant.panel.revalidate();
		Constant.canvas.repaint();
		Constant.matrix.repaint();
//		Constant.pas /= matrix.ratio;
	}
	
	public CanvasImpl(CanvasMediator mediator) {
		
		this.mediator = mediator;
		Constant.mediator = mediator;
		mediator.registerCanvas(this);
		
	}

}
