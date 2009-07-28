package edss.interf;

import java.io.File;

import javax.swing.JPanel;

public interface Gui {

	void editPieceProperties(Piece piece);
	JPanel getCenterPanel();
	JPanel getLeftPreview();
	boolean hasFrames();
	edss.gui.Piece getSelectedPiece();
}
