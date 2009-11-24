package edss.canvas.constants;

import java.awt.Point;

public class MyPoint extends Point
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8685824146737361555L;

	public MyPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public MyPoint(String x, String y)
	{
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
	}

	@Override
	public String toString()
	{
		return x + ", " + y + " ";
	}

	@Override
	public boolean equals(Object obj)
	{
		MyPoint tmpPoint = (MyPoint) obj;
		return (x == tmpPoint.x) && (y == tmpPoint.y);
	}
}
