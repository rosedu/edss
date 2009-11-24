package edss.canvas.transforms;

public class Translate
{
	public double x;
	public double y;

	public Translate(double x, double y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString()
	{
		return ("translate(" + x + ", " + y + ")");
	}
}