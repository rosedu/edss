package edss.gui;
public class Tree {

	String value;
 	String type;
	int n;
	Tree [] sons;
	
	public Tree(String v, String t)
	{
		value = v;
		type = t;
		n = 0;
		sons = new Tree[100];
	}
	
	public void add(String v, String t)
	{
		sons[n] = new Tree(v,t);
		n = n + 1;
	}
	
	public void show(String s)
	{
		System.out.println(s + value);
		for(int i=0; i<n; i++)
			sons[i].show(s + "  ");
		
	}
	
	
}
