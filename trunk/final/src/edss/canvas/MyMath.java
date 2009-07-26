package edss.canvas;

public class MyMath {
	static int roundAtStep(double x, int step) {
		int result;
		int offset = (int) (x % step);
		result = (int) (x - offset);
		if (offset > step / 2) {
			result += step; 
		}
		return result;
	}
}
