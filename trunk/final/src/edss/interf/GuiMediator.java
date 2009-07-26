package edss.interf;

import edss.gui.GuiImpl;

public interface GuiMediator {
	
	public void scale(int factor);
	public void registerGui(Gui gui);

	public void registerGui(Gui guiImpl);

}
