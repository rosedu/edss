package edss.med;

import edss.canvas.MyCanvas;
import edss.interf.*;


public class Mediator implements CanvasMediator, GuiMediator, ModelMediator {

	private Gui gui;
	private Model model;
	private Canvas canvas;

	@Override
	public void registerGui(Gui g)
	{
		gui = g;
	}
	
	@Override
	public void registerModel(Model model) {
		this.model = model;
	}
	
	@Override
	public void editPieceProperties(String id) {
		Piece piece = model.getPiece(id);
		gui.editPieceProperties(piece);
		model.update();
	}

	@Override
	public void update() {
		canvas.update();
	}

	@Override
	public void scale(int factor) {
		canvas.scale(factor);
	}
	
	@Override
	public void registerCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void addPanel() {
		//System.out.println(gui.getCenterPanel());
		MyCanvas canv = new MyCanvas(gui.getCenterPanel());
		
		gui.getCenterPanel().add(canv.getCanvas());
	}

	@Override
	public void enterState(int c) {
		// TODO Auto-generated method stub
		
	}
}
