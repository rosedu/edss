package edss.med;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import edss.canvas.MyCanvas;
import edss.canvas.StateConstant;
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
		MyCanvas canv = new MyCanvas(gui.getCenterPanel());
		gui.getCenterPanel().add(canv.getCanvas());
	}

	@Override
	public void enterState(int c) {
		switch(c)
		{
		case StateConstant.PIECESTATE :
			canvas.enterInsertState();
			break;
		case StateConstant.DELETESTATE :
			canvas.enterDeleteState();
			break;
		case StateConstant.DRAGSTATE :
			break;
		case StateConstant.MOUSESTATE :
			canvas.enterPieceState();
			break;
		case StateConstant.WIRESTATE :
			canvas.enterWireState();
			break;
		default : break;
			
		}
		
	}

	@Override
	public void setPiece(String name, String category, String subcategory) {
		// pasez!
		model.setLastSelected(category,subcategory,name);
		// System.out.println(category + " " + subcategory + " " + name); 
		
	}
	@Override
	public String addPiece(int x, int y) {
		return model.addPiece(x, y);
	}

	@Override
	public String getSVG() {
		try {
			String res = new File("svg/" + model.getSVG()).getCanonicalPath();
			System.out.println(res);
			return "file:///" + res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void save(String file)
	{
		try {
			if(file.contains(".svg"))
			{
				canvas.saveSVG(file);
				model.saveScheme(file.substring(0, file.lastIndexOf('.')));
			}
			else
			{
				canvas.saveSVG(file + ".svg");
				model.saveScheme(file);
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void open(String file)
	{
		 try {
			 System.out.println(file);
			 canvas.openSVG(file  + ".svg");
			 model.openScheme(file);
		} catch (IOException e) {
	 		e.printStackTrace();
		}
	}
	
	public void rotate(int r)
	{
		//String lastSelectedId=canvas.getLastSelectedPiece();
		
		//DE AVUT grija sa se faca null la loc cand se intra in alt mod!!!
	/*	
		if (lastSelectedId!=null) {
		canvas.rotate(r,lastSelectedId);
		model.rotate(r,lastSelectedId);
		}
	 */
		
		
	}
	
	
	
	public void delete(String lastSelectedId)
	{
		// model.delete(lastSelectedId);
		// canvas.delete(lastSelectedId);
	}
	

}
