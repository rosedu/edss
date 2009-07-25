import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ParseFavorites {
	private Vector <String> cat = new Vector <String> ();
	private Vector <String> subcat = new Vector <String> ();
	private Vector <String> name = new Vector <String> ();
	
	public ParseFavorites(String file)
	{
		try 
		{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		Element elt = document.getDocumentElement();	
		
		showAttributesRec(elt);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void showAttributesRec(Node n)
	{
		NodeList aux;
		NamedNodeMap map;
		// System.out.println(n.getNodeName());

		if(n.getNodeName().equals("category"))
		{
			map = n.getAttributes();
			
			for(int i=0; i<n.getChildNodes().getLength(); i++)
			{
				// System.out.println(n.getNodeName() + " " + n.getChildNodes().item(i).getNodeValue());	
				cat.add(n.getChildNodes().item(i).getNodeValue());
			}
		}
		
		if(n.getNodeName().equals("subcategory"))
		{
			map = n.getAttributes();
			
			for(int i=0; i<n.getChildNodes().getLength(); i++)
			{
				// System.out.println(n.getNodeName() + " " + n.getChildNodes().item(i).getNodeValue());	
				subcat.add(n.getChildNodes().item(i).getNodeValue());
			}
		}
		
		if(n.getNodeName().equals("name"))
		{
			map = n.getAttributes();
			
			for(int i=0; i<n.getChildNodes().getLength(); i++)
			{
				// System.out.println(n.getNodeName() + " " + n.getChildNodes().item(i).getNodeValue());	
				name.add(n.getChildNodes().item(i).getNodeValue());
			}
		}
		
		if(n.hasChildNodes())
			{
				aux = n.getChildNodes();
				for(int i=0; i<aux.getLength(); i++)
					showAttributesRec(aux.item(i));
			}
	}
	public Vector <String> getNames()
	{
		return name;
	}
	
	public Vector <String> getCategory()
	{
		return cat;
	}
	
	public Vector <String> getSubCategory()
	{
		return subcat;
	}
	
	public Vector <Piece> getPiece()
	{
		Vector <Piece>  p = new Vector <Piece> ();
		for(int i=0; i<name.size(); i++)
			p.add(new Piece(name.get(i), cat.get(i), subcat.get(i)));
		return p;
	}
}
