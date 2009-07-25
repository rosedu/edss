package edss.gui;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Configuration {
	public int skinId;
	public int toolbarsNr = 0;
	public Vector <Vector<Integer>> toolButtons = new Vector <Vector<Integer>> ();
	
	public Configuration(String cf)
	{
		try 
		{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(cf);
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
		
		//System.out.println("Atributele tagului" + n.getNodeName() + " sunt: ");
		if(n.getNodeName().equals("skin"))
		{
			map = n.getAttributes();
		
			for(int i=0; i<map.getLength(); i++)
			{
				//System.out.println("\t" + map.item(i).getNodeName() + "=\"" + map.item(i).getNodeValue() + "\"");
				skinId = Integer.parseInt(map.item(i).getNodeValue());
			}
		}
		
		if(n.getNodeName().equals("toolbar"))
		{
			toolbarsNr++;
			toolButtons.add(new Vector<Integer>());
		}
		
		if(n.getNodeName().equals("button"))
		{
			map = n.getAttributes();
		
			for(int i=0; i<map.getLength(); i++)
			{
				//System.out.println(toolbarsNr);
				toolButtons.get(toolbarsNr - 1).add(Integer.parseInt(map.item(i).getNodeValue()));
				
			}
		}
		
				if(n.hasChildNodes())
				{
					aux = n.getChildNodes();
					for(int i=0; i<aux.getLength(); i++)
						showAttributesRec(aux.item(i));
				}
	}
	public void show()
	{
		System.out.println("Skin number " + skinId);
		for(int i=0; i<toolbarsNr; i++)
		{
			System.out.println(i + " ::> " + toolButtons.get(i).toString());
		}
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
