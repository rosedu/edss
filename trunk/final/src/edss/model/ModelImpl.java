package edss.model;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;
import edss.interf.WireInfo;

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
	
	public Schematic getScheme() {
		return scheme;
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
	public edss.interf.Piece addPiece(int x, int y) {
		if (lastSelected[0] == null)
			return null;
		
		edss.model.Piece p = new edss.model.Piece(scheme, lastSelected[0], lastSelected[1], lastSelected[2], x, y);
		piece = p;
		scheme.addComponent(p); //scheme.addComp(p);
		return p;
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
		return scheme.addWire(piece1, pin1, piece2, pin2, points);
	}
	
	@Override
	public String splitWire(String splitId, int x, int y, List<? extends Point> list1, 
							List<? extends Point> list2, String idStartPiece, String idStartPin,
							List<? extends Point> newWireList) {
		return scheme.splitWire(splitId, x, y, list1, list2, idStartPiece, idStartPin, newWireList);
		
	}

	@Override
	public List<WireInfo> getWiresInfo(final String pieceId) {
		List<WireInfo> wi = new LinkedList<WireInfo>();
		List<Pin> pins = new LinkedList<Pin>(scheme.getComponents().get(pieceId).getPins().values());
		
		Iterator<Pin> it = pins.iterator();
		
		while(it.hasNext()) {
			final Pin pin = it.next();
			List<Connection> connections = pin.getConnections();
			
			Iterator<Connection> itc = connections.iterator();
			
			while (itc.hasNext()) {
				Connection con = itc.next();
				final Wire w = con.getWire();
				
				wi.add(new WireInfo() {
					
					public int getEnd() {
						if (w.getPin1().getId() == pin.getId())
							return WireInfo.BEGIN;
						else
							return WireInfo.END;
					}

					@Override
					public String getWireId() {
						return w.getId();
					}
					
				});
			}
		}
		return wi;
	}

	@Override
	public void delete(String id) {
		if (scheme.getWires().get(id) != null)
			scheme.removeWire(id);
		else
			scheme.removeComponent(id);
	}
}
