
public class Piece {
	private String name;
	private String categ;
	private String subcateg;
	
	public Piece(String n, String c, String s)
	{
		name = n;
		categ = c;
		subcateg = s;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getCategory()
	{
		return categ;
	}
	
	public String getSubCategory()
	{
		return subcateg;
	}
	
	public String toString()
	{
		return name;
		// return (name + "$" + categ + "$" + subcateg);
	}
	
	
	@Override
	public boolean equals(Object p)
	{
		Piece pp = (Piece)p;
		if(name.equals(pp.name) && categ.equals(pp.categ) && subcateg.equals(pp.subcateg))
			return true;
		return false;
	}
	
}
