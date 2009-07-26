package edss.interf;

import edss.gui.GuiImpl;

public interface GuiMediator {
	
	public void scale(int factor);
	public void registerGui(Gui guiImpl);
	public void addPanel();

}
