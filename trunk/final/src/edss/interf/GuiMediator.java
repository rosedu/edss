package edss.interf;

import edss.gui.GuiImpl;

public interface GuiMediator {
	
	public void scale(int factor);
	public void registerGui(Gui guiImpl);
	public void addPanel();
	public void enterState(int c);
	public void setPiece(String name, String category, String subcategory);
}
