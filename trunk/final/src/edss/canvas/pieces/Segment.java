package edss.canvas.pieces;

import edss.canvas.constants.MyPoint;

class Segment
{
	final static int VERTICAL = 0;
	final static int HORIZONTAL = 1;
	MyPoint a;
	MyPoint b;
	int direction;

	Segment()
	{
		a = null;
		b = null;
	}

	Segment(MyPoint a, MyPoint b)
	{
		this.a = a;
		this.b = b;
	}

	public void move(int delta)
	{
		// System.out.println("delta " + delta);
		// System.out.println("direct " + direction);
		// System.out.println(a==b);
		switch (direction)
		{

			case Segment.HORIZONTAL:
				a.x = delta;
				b.x = delta;
				break;

			case Segment.VERTICAL:
				a.y = delta;
				b.y = delta;
				break;

			default:
				System.out.println("Nu trebuie sa intre aici !!!");
				break;
		}
	}
}