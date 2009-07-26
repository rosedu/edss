package edss.model;

import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;

public class ModelImpl implements Model {

	private ModelMediator med;
	private Schematic scheme;
//	private edss.model.Piece selectedPiece;
	
	private String[] lastSelected;
	
	
	public ModelImpl(ModelMediator med) {
		this.med = med;
		med.registerModel(this);
		
		scheme = new Schematic();
		lastSelected = new String[3];
	}
	
	@Override
	public Piece getPiece(String id) {
		return scheme.getComponents().get(id);
	}
		
	@Override
	public void update() {
		med.update();	
	}
	
	@Override
	public void setLastSelected(String category, String subCategory, String name) {
		lastSelected[0] = category;
		lastSelected[1] = subCategory;
		lastSelected[2] = name;
	}
	
//	public Piece getSelectedPiece() {
//		return selectedPiece;
//	}
//	
//	public void setSelectedPiece(Piece p) {
//		selectedPiece = (edss.model.Piece)p;
//	}
	
	public void createPiece(String category, String subcategory, String name) {
		setLastSelected(category, subcategory, name);
	}
	
	public void addPiece(int x, int y) {
		 scheme.addComponent(new edss.model.Piece(lastSelected[0], lastSelected[1], x, y, lastSelected[2]));
	}
	
	public String getSVG() {
		return edss.model.Piece.getSVG(lastSelected[0], lastSelected[1]);
	}
}
