package edss.interf;

import java.awt.Point;
import java.util.List;



public interface CanvasMediator {
	
	/**
	 * Metoda apelata de Canvas cand utilizatorul vrea sa modifice
	 * valoarea unei piese, dupa ce a dat click pe ea
	 * @param Id
	 */
	public void editPieceProperties(String id);
	public void registerCanvas(Canvas canvas);
	public edss.interf.Piece addPiece(int x, int y);
	public String getSVG();
//	public void delete();
	public String addWire(List<? extends Point> pointList,
			String idStartPiece, String idStartPin, String idEndPiece,
			String idEndPin);
	
	
	public List<WireInfo> getWiresInfo(String pieceId);
	public String splitWire(String splitId, int x, int y, List<? extends Point> list1, List<? extends Point> list2, String idStartPiece, String idStartPin,
			List<? extends Point> newWireList);
	
	// TODO delete
	public void delete(String id);
	 
}
