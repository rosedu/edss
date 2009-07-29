package edss.interf;

import java.awt.Point;
import java.util.List;

public interface ModelMediator {
	
	void registerModel(Model model);

	void update();

	void removeWire(String id);

	void addNode(int x, int y);

	void addWire(String id, List<? extends Point> points);
	
}
