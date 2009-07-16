
public interface ModelMediator {
	//they notify us
	public void valueChanged(String id, String s);
	public void nameChanged(String id, String s);
	public void rotate(String id, int i);
	public void scale(String id, double d);
	public void move(String id, int x, int y);
	public void removed(String id);
	public void created(String type, int x, int y, String value);
	
	//we notify them
	public void reDraw(String id);	
	public void changeValue(String id, String s);
	public void changeName(String id, String s);
	
}
