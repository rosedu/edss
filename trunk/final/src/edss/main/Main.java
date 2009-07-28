package edss.main;


import edss.canvas.CanvasImpl;
import edss.gui.GuiImpl;
import edss.interf.Canvas;
import edss.interf.Gui;
import edss.interf.Model;
import edss.med.Mediator;
import edss.model.ModelImpl;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Mediator mediator = new Mediator();
		Gui gui = new GuiImpl();
		// Canvas canvas = new CanvasImpl(mediator); 
		// Model model = new ModelImpl(mediator);
	}

}
