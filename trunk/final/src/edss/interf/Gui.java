package edss.interf;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import edss.gui.NewInternalFrame;

public interface Gui {

	void editPieceProperties(Piece piece);
	JPanel getCenterPanel();
	JPanel getLeftPreview();
	boolean hasFrames();
}
