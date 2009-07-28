package edss.canvas;

import java.util.StringTokenizer;


public class TransformTag {
	Translate translate = null;
	Rotate rotate = null;
	Scale scale = null;
	
	public TransformTag(String text) {
		int index;
		index = text.indexOf("translate");
		if (index != -1) {
			StringTokenizer tok = new StringTokenizer(text.substring(index), "(, )");
			tok.nextToken();
			double x = Double.parseDouble(tok.nextToken());
			double y = Double.parseDouble(tok.nextToken());
			translate = new Translate(x, y);
		}
		
		index = text.indexOf("rotate");
		if (index != -1) {
			StringTokenizer tok = new StringTokenizer(text.substring(index), "(, )");
			tok.nextToken();
			
			double angle = Double.parseDouble(tok.nextToken());
			double x = Double.parseDouble(tok.nextToken());
			double y = Double.parseDouble(tok.nextToken());
			System.out.println("x si y = " + x+y);
			rotate = new Rotate(angle, x, y);
		}
		
		index = text.indexOf("scale");
		if (index != -1) {
			StringTokenizer tok = new StringTokenizer(text.substring(index), "(, )");
			tok.nextToken();
			double amount = Double.parseDouble(tok.nextToken());
			scale = new Scale(amount);
		}
	}
	
	@Override
	public String toString() {
		String aux = "";
		if (translate != null) {
			aux += translate.toString() + " ";
		}
		if (rotate != null) {
			aux += rotate.toString() + " ";
		}
		if (scale != null) {
			aux += scale.toString() + " ";
		}
		
		return aux;
	}
}

class Translate {
	double x;
	double y;
	
	public Translate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return ("translate(" + x + ", " + y + ")");
	}
}

class Rotate {
	double angle;
	double x;
	double y;
	
	public Rotate(double angle, double x, double y) {
		super();
		this.angle = angle;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return ("rotate("+ angle + ", " + x + ", " + y + ")");
	}
}

class Scale {
	// TODO : translate + scale + translate
	double amount;
	
	public Scale(double amount) {
		super();
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return ("scale(" + amount + ")");
	}

}