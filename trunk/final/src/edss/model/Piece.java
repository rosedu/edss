package edss.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class Piece implements Serializable, edss.interf.Piece {
	protected String svgURI;
	protected String id;
	protected Schematic scheme;
	

	protected String type;
	protected String model;

//	protected static Map<String, Map<String, Boolean>> instances = 
//		new HashMap<String, Map<String, Boolean>>();
	
	protected Map<String, String> properties;

	protected int x,y;
	protected int rotated = 0;
	
	protected Map<String, Pin> pins;
	
/*	protected Piece() {
	}*/
	
	public Piece(Schematic scheme, String category, String subCategory, String model, int x, int y) {
		this.x = x;
		this.y = y;
		this.scheme = scheme;
		
		pins = new HashMap<String, Pin>();
		properties = new HashMap<String, String>();
		Document doc = null;
		
		
		//-------------------------------------------------------------------
		//-------------------------------------------------------------------
		String componentFileName = "components/" + category + "/" + subCategory +
									"/" + subCategory + ".xml";
		
		File f = new File(componentFileName);
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Node pieceName = doc.getChildNodes().item(0);
		NodeList nl = pieceName.getChildNodes();
		
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("pinInfo")) {
				NodeList nl1 = n.getChildNodes();
				
				for (int j = 0; j < nl1.getLength(); j++) {
					Node n1 = nl1.item(j);
					
					if (n1.getNodeType() == Node.ELEMENT_NODE) {
						String pinName = n1.getChildNodes().item(0).getTextContent().trim();
						String pinID = "pin" + n1.getAttributes().item(0).getNodeValue().trim();
						
						pins.put(pinID, new Pin(pinID, pinName, this));
					}

				}
			}
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("svg")) {
				svgURI = n.getChildNodes().item(0).getTextContent().trim();
			}
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("id")) {
				type = n.getChildNodes().item(0).getTextContent().trim();
			}
		}
		//-------------------------------------------------------------------
		//-------------------------------------------------------------------
		String modelFileName = componentFileName.substring(0, componentFileName.length()-4)
								+ "_model.xml";
		
		f = new File(modelFileName);
		
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		NodeList modelsList = doc.getChildNodes().item(0).getChildNodes();
		
		for (int i = 0; i < modelsList.getLength(); i++) {
			Node n = modelsList.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (n.getAttributes().item(0).getNodeValue().equals(model)) {
					NodeList prop = n.getChildNodes();
					
					for (int j = 0; j < prop.getLength(); j++) {
						Node m = prop.item(j);
						if (m.getNodeType() == Node.ELEMENT_NODE) {
							String property = m.getNodeName();
							String value = m.getChildNodes().item(0).getTextContent().trim();
							
							properties.put(property, value);
						}
					}
				}
			}
		}
		//----------------------------------------------------------------
		//----------------------------------------------------------------
		Map<String, Boolean> usedIDs;
		Integer no;

		usedIDs = scheme.getPieceInstances().get(type);

		if (usedIDs == null) {
			no = 1;
			Map<String, Boolean> m2 = new HashMap<String, Boolean>();
			m2.put(type+"1", true);
			scheme.getPieceInstances().put(type, m2);
		} else {
			int i;
			for (i = 1; ; i++) {
				if (usedIDs.get(type+i) == null)
					break;
			}

			no = i;
			usedIDs.put(type+no, true);
		}

		this.id = type + no;
		
		//----------------------------------------------------------------
		//----------------------------------------------------------------
		this.model = null;
		if (type.equals("R"))
			this.model = properties.get("resistance");
		if (type.equals("C"))
		    this.model = properties.get("capacitance");
		if (type.equals("V"))
			this.model = properties.get("value");
		if (type.equals("D"))
			this.model = model;
		if (type.equals("Q"))
			this.model = model;
		
		System.out.println("Created component: " + id);
	}
	
	public static String getSVG(String category, String subCategory) {
		String componentFileName = "components/" + category + "/" + subCategory +
		"/" + subCategory + ".xml";
		Document doc = null;

		File f = new File(componentFileName);
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return doc.getElementsByTagName("svg").item(0).getChildNodes().item(0).getTextContent().trim();
	}
	
	public String getSvgURI() {
		return svgURI;
	}
	
	public void setSvgURI(String svgURI) {
		this.svgURI = svgURI;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getRotated() {
		return rotated;
	}
	
	public void setRotated(int rotated) {
		this.rotated = rotated;
	}
	
	public boolean isRotated() {
		return !(rotated == 0);
	}
	
	public Map<String, Pin> getPins() {
		return pins;
	}
	
	public String getId() {
		return id;
	}
	
	public Schematic getScheme() {
		return scheme;
	}
	
	public String getType() {
		return type;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
	
	public void setProperty(String property, String value) {
		properties.put(property, value);
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("id: ").append(id).append("\n");
		
		Iterator<Pin> it = pins.values().iterator();
		
		while (it.hasNext())
		{
			Pin p = it.next();
			sb.append("pin: ").append(p.toString()).append(" ").append("connected to: ");
			
			sb.append(p.listConnections());
			
			sb.append("\n");
		}
		
		return sb.toString();
	}

	@Override
	public String getName() {
		return id;
	}

	@Override
	public String getValue() {
		return model;
	}
}
