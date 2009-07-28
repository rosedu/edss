package edss.interf;

import java.io.File;

import javax.swing.JPanel;

import edss.gui.NewInternalFrame;

public interface Gui {

	void editPieceProperties(Piece piece);
	JPanel getCenterPanel();
	JPanel getLeftPreview();
	boolean hasFrames();
	edss.gui.Piece getSelectedPiece();
	NewInternalFrame getActiveFrame();
}
