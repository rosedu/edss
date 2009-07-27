package edss.interf;

public interface CanvasMediator {
	
	/**
	 * Metoda apelata de Canvas cand utilizatorul vrea sa modifice
	 * valoarea unei piese, dupa ce a dat click pe ea
	 * @param Id
	 */
	public void editPieceProperties(String id);
	public void registerCanvas(Canvas canvas);
	public String addPiece(int x, int y);
	public String getSVG();

}
