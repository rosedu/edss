package edss.gui;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ParseDatabase {
	
	Tree data = new Tree("DATA", "database");
	
	public ParseDatabase(String file)
	{
		try 
		{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		Element elt = document.getDocumentElement();	
		
		showAttributesRec(data, elt);
		// data.show("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void showAttributesRec(Tree d, Node n)
	{
		for(int i=0; i<n.getChildNodes().getLength(); i++)
		{
			if(n.getChildNodes().item(i).getNodeName().equals("piece"))
			{
				d.add(n.getChildNodes().item(i).getChildNodes().item(0).getNodeValue(), "piece");
				showAttributesRec(d.sons[d.n-1], n.getChildNodes().item(i));	
			}
			else
			{
				if(n.getChildNodes().item(i).getAttributes() != null)
				{
					d.add(n.getChildNodes().item(i).getAttributes().item(0).getNodeValue(), n.getChildNodes().item(i).getNodeName());
					showAttributesRec(d.sons[d.n-1], n.getChildNodes().item(i));	
				}
			}	
		}
		
	}
}
