package edss.model;

import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;

public class ModelImpl implements Model {

	private ModelMediator med;
	private Schematic scheme;
	private edss.model.Piece selectedPiece;
	
	public ModelImpl(ModelMediator med) {
		this.med = med;
		med.registerModel(this);
	}
	
	@Override
	public Piece getPiece(String id) {
		return scheme.getComponents().get(id);
	}
		
	@Override
	public void update() {
		med.update();	
	}
	
	public Piece getSelectedPiece() {
		return selectedPiece;
	}
	
	public void setSelectedPiece(Piece p) {
		selectedPiece = (edss.model.Piece)p;
	}
	
	public Piece createPiece(String category, String subcategory, String name) {
		return new edss.model.Piece(category, subcategory, 0, 0, name);
	}
	
	public void addPiece(int x, int y) {
		 selectedPiece.setX(x);
		 selectedPiece.setY(y);
		 scheme.addComponent(selectedPiece);
		 selectedPiece.setX(0);
		 selectedPiece.setY(0);
	}
	
	public String getSVG() {
		return selectedPiece.getSvgURI();
	}
}
