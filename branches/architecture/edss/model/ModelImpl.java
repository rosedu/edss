package edss.model;

import edss.interf.Model;
import edss.interf.ModelMediator;
import edss.interf.Piece;

public class ModelImpl implements Model {

	private ModelMediator med;

	@Override
	public Piece getPiece(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		med.update();
		
	}

	

}
