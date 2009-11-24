package edss.canvas.transforms;

public class Rotate
{
	public double angle;
	public double x;
	public double y;

	public Rotate(double angle, double x, double y)
	{
		super();
		this.angle = angle;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString()
	{
		return ("rotate(" + angle + ", " + x + ", " + y + ")");
	}
}
