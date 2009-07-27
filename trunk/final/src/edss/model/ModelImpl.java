package edss.model;

import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;

public class ModelImpl implements Model {

	private ModelMediator med;
	private Schematic scheme;
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
	
	@Override
	public String addPiece(int x, int y) {
		if (lastSelected[0] == null)
			return null;
		
		edss.model.Piece p = new edss.model.Piece(lastSelected[0], lastSelected[1], lastSelected[2], x, y);
		scheme.addComponent(p);
		return p.getId();
	}
	
	@Override
	public String getSVG() {
		return edss.model.Piece.getSVG(lastSelected[0], lastSelected[1]);
	}

	@Override
	public void openScheme(String name) {
		scheme = Schematic.load(name+".sch");
		System.out.println(scheme);
	}

	@Override
	public void saveScheme(String name) {
		scheme.save(name+".sch");
	}
}
