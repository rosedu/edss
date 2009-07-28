package edss.canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


public class WirePoints {
	ArrayList<MyPoint> pointList = new ArrayList<MyPoint>();
	public WirePoints(String text) {
		int i = 0;
		StringTokenizer tok = new StringTokenizer(text, ", ");
		while (tok.hasMoreTokens()) {
			pointList.add(new MyPoint(tok.nextToken(), tok.nextToken()));
			System.out.println(pointList.get(i++));
		}
		System.out.println(pointList);
	}
	
	public WirePoints(int x, int y) {
		pointList.add(new MyPoint(x,y));
	}
	

	@Override
	public String toString() {
		String result = "";
		for (Iterator<MyPoint> it = pointList.iterator(); it.hasNext(); ) {
			 result += it.next().toString();
		}
		return result;
	}
	
	public void addPoint(int x, int y) {
		MyPoint a;
		MyPoint b;
		if (pointList.size() > 1) {
//			int dX = Math.abs((int) Math.round(arg0.getX() / matrix.scale - crtPoint.x));	// la scara 1:1
//			int dY = Math.abs((int) Math.round(arg0.getY() / matrix.scale - crtPoint.y));
			
			
			a = pointList.get(pointList.size() - 2);
			b = pointList.get(pointList.size() - 1);
			if (
					(a.x == b.x && a.x == x) ||
					(a.y == b.y && a.y == y)) {
				
				pointList.remove(pointList.size() - 1);
				
			}
		}
		
		MyPoint tmpPoint;// = new MyPoint(x, y); 
		b = pointList.get(pointList.size() - 1);

		if (b.x != x && b.y != y) {
			tmpPoint = new MyPoint(b.x, y);
			pointList.add(tmpPoint);
		}

		tmpPoint = new MyPoint(x, y);
		if (pointList.get(pointList.size() - 1).equals(tmpPoint)) {
			// am ramas pe acelasi punct
			return;
		}
		pointList.add(tmpPoint);
		
	}
	
	public Segment getSegment(int x, int y) {
		// cand intru aici, stiu ca punctul (x, y) este pe fir si ca firul are cel putin 2 puncte
		Segment result = new Segment();
		Iterator<MyPoint> it = pointList.iterator();
		if (it.hasNext()) {
			result.a = it.next();
			if (it.hasNext()) {
				result.b = it.next();
			}
		} else {
			System.out.println("mda");
		}
		while (it.hasNext()) {
			 
			 if (result.a.x == result.b.x && result.a.x == x && 
					 (result.a.y - y) * (result.b.y - y)  < 0) {
				 result.direction = Segment.HORIZONTAL;
				 break;
			 }
			 
			 if (result.a.y == result.b.y && result.a.y == y &&
					 (result.a.x - x) * (result.b.x - x)  < 0) {
				 result.direction = Segment.VERTICAL;
				 break;
			 }
			 result.a = result.b;
			 result.b = it.next();
		}
		System.out.println(result.a + " " + result.b);
		return result;
	}
	

}

class MyPoint {
	int x;
	int y;
	
	MyPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public MyPoint(String x, String y) {
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
	}
	
	@Override
	public String toString() {
		return x + ", " + y + " ";
	}
	
	@Override
	public boolean equals(Object obj) {
		MyPoint tmpPoint = (MyPoint) obj;
		return (x == tmpPoint.x) && (y == tmpPoint.y);
	}
}

class Segment {
	final static int VERTICAL = 0;
	final static int HORIZONTAL = 1;
	MyPoint a;
	MyPoint b;
	int direction;
	
	Segment() {
		a = null;
		b = null;
	}
	
	Segment(MyPoint a, MyPoint b) {
		this.a = a; 
		this.b = b;
	}
	
	public void move(int delta) {
//		System.out.println("delta " + delta);
//		System.out.println("direct " + direction);
//		System.out.println(a==b);
		switch(direction) {
		
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