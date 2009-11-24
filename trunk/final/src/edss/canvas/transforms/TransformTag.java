package edss.canvas.transforms;

import java.util.StringTokenizer;

public class TransformTag
{
	public Translate translate = null;
	public Rotate rotate = null;
	Scale scale = null;

	public TransformTag(String text)
	{
		int index;
		index = text.indexOf("translate");
		if (index != -1)
		{
			StringTokenizer tok = new StringTokenizer(text.substring(index), "(, )");
			tok.nextToken();
			double x = Double.parseDouble(tok.nextToken());
			double y = Double.parseDouble(tok.nextToken());
			translate = new Translate(x, y);
		}

		index = text.indexOf("rotate");
		if (index != -1)
		{
			StringTokenizer tok = new StringTokenizer(text.substring(index), "(, )");
			tok.nextToken();

			double angle = Double.parseDouble(tok.nextToken());
			double x = Double.parseDouble(tok.nextToken());
			double y = Double.parseDouble(tok.nextToken());
			rotate = new Rotate(angle, x, y);
		}

		index = text.indexOf("scale");
		if (index != -1)
		{
			StringTokenizer tok = new StringTokenizer(text.substring(index), "(, )");
			tok.nextToken();
			double amount = Double.parseDouble(tok.nextToken());
			scale = new Scale(amount);
		}
	}

	@Override
	public String toString()
	{
		String aux = "";
		if (translate != null)
		{
			aux += translate.toString() + " ";
		}
		if (rotate != null)
		{
			aux += rotate.toString() + " ";
		}
		if (scale != null)
		{
			aux += scale.toString() + " ";
		}

		return aux;
	}
}

class Scale
{
	// TODO : translate + scale + translate
	double amount;

	public Scale(double amount)
	{
		super();
		this.amount = amount;
	}

	@Override
	public String toString()
	{
		return ("scale(" + amount + ")");
	}

}