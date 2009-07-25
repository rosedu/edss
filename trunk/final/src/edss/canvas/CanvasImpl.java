package edss.canvas;

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
		// TODO Auto-generated method stub
		
	}
	
	public CanvasImpl(CanvasMediator mediator) {
		this.mediator = mediator;
		mediator.registerCanvas(this);
	}

}
