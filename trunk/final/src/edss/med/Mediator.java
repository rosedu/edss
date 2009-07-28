package edss.med;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.transform.TransformerException;

import edss.canvas.CanvasImpl;
import edss.canvas.StateConstant;
import edss.canvas.StateConstant;
import edss.interf.Canvas;
import edss.interf.CanvasMediator;
import edss.interf.Gui;
import edss.interf.GuiMediator;
import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;
import edss.model.ModelImpl;


public class Mediator implements CanvasMediator, GuiMediator, ModelMediator, Serializable {

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
	public void registerCanvas(Canvas canvas) {
		this.canvas = canvas;
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
	public void addPanel() {
//		CanvasImpl canv = new CanvasImpl(gui.getCenterPanel());
		canvas.createNewCanvas(gui.getCenterPanel());
		gui.getCenterPanel().add(canvas.getCanvas());
//		return gui.getCenterPanel();
		
		
//		gui.getCenterPanel().add(canvas.getPreview("file:///C:/My Documents 1/EDSS/svn/trunk/final/svg/amplifier.svg"));
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
	
	/*public String addPiece(Mediator med, int x, int y) {
		commandManager.doCommand(new AddComponentCommand(this, x, y));
	}*/
	
	@Override
	public String addPiece/*WithoutUndo*/(int x, int y) {
		return model.addPiece(x, y);
		//canvas.addPieceToCanvas(x, y);
	}
	
	/*
	 * public void addPieceToCanvas(edss.interf.Piece piece){
	 * 		canvas.addPieceToCanvas(piece);
	 * }
	 */

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
		
//		canvas.setCanvasVariables(gui.getCenterPanel());
//		gui.getCenterPanel().add(canvas.getCanvas());
		
		 try {
			 System.out.println(file);
			 canvas.openSVG(gui.getCenterPanel(), file  + ".svg");
			 model.openScheme(file, this);
		} catch (IOException e) {
	 		e.printStackTrace();
		}
	}
	
	public void rotate(int r)
	{
		String lastSelectedId = canvas.getLastSelectedPieceId();
		canvas.rotate(angle);//tine minte singur piesa selectata
		
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

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public void init()
	{
		canvas = new CanvasImpl(this);
		model = new ModelImpl(this);
	}

	public void setPreview()
	{
		gui.getLeftPreview().removeAll();
		gui.getLeftPreview().add(canvas.getPreview(getSVG()));
		gui.getLeftPreview().repaint();
		gui.getLeftPreview().validate();
	}

}
