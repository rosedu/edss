package edss.interf;

import javax.swing.JPanel;

public interface Gui {

	void editPieceProperties(Piece piece);
	JPanel getCenterPanel();
	JPanel getLeftPreview();
	boolean hasFrames();
}
