package edss.model;

import java.awt.Point;
import java.util.List;

import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;

public class ModelImpl implements Model {

	private ModelMediator med;
	private Schematic scheme;
	private String[] lastSelected;
	private edss.model.Piece piece = null;
	
	public ModelImpl(ModelMediator med) {
		this.med = med;
		med.registerModel(this);
		
		scheme = new Schematic(med);
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
		piece = null;
	}
	
	@Override
	public String addPiece(int x, int y) {
		if (lastSelected[0] == null)
			return null;
		
		edss.model.Piece p = new edss.model.Piece(scheme, lastSelected[0], lastSelected[1], lastSelected[2], x, y);
		piece = p;
		scheme.addComponent(p); //scheme.addComp(p);
		return p.getId();
	}
	
	@Override
	public void rotate(int r, String id) {
		scheme.rotateComponent(r, id);
	}
	
	@Override
	public String getSVG() {
		return edss.model.Piece.getSVG(lastSelected[0], lastSelected[1]);
	}
	
	@Override
	public void openScheme(String name, ModelMediator med) {
		scheme = Schematic.load(name+".sch", med);
		System.out.println(scheme);
	}
	
	@Override
	public void saveScheme(String name) {
		scheme.save(name+".sch");
	}
	
	public edss.model.Piece getPiece(){
		return piece;
	}

	@Override
	public String addWire(String piece1, String pin1, String piece2, String pin2, List<? extends Point> points) {
		Piece pm1 = scheme.getComponents().get(piece1);
		Piece pm2 = scheme.getComponents().get(piece2);
		Pin p1 = pm1.getPins().get(pin1);
		Pin p2 = pm2.getPins().get(pin2);

		Wire w = new Wire(p1, p2, points);
		scheme.addWire(w);
		return w.getId();
	}
}
