import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class PieceModel implements Serializable {
	protected String svgURI;
	protected String id;
	
	protected String name;
	protected String value;
	
	protected String type;
	public static final String DC_VOLTAGE_SRC = "V";
	public static final String RESISTOR = "R";
	public static final String DIODE = "D";
	public static final String TRANSISTOR_BJT = "Q";
	public static final String GROUND = "G";
	public static final String NODE = "N";
	protected static Map<String, Map<String, Boolean>> instances = 
		new HashMap<String, Map<String, Boolean>>();
	protected static Document pinInfo = parseXML("pins.xml");
	
	protected int x,y;
	protected int rotated = 0;
	
	protected Map<String, Pin> pins;
	
	protected PieceModel() {
	}
	
	public PieceModel(String type, int x, int y, String model) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.value = model;
		pins = new HashMap<String, Pin>();

		Map<String, Boolean> usedIDs;
		Integer no;

		usedIDs = instances.get(type);

		if (usedIDs == null) {
			no = 1;
			Map<String, Boolean> m2 = new HashMap<String, Boolean>();
			m2.put(type+"1", true);
			instances.put(type, m2);
		} else {
			int i;
			for (i = 1; ; i++) {
				if (usedIDs.get(type+i) == null)
					break;
			}

			no = i;
			usedIDs.put(type+no, true);
		}

		this.name = type + no;
		this.id = type + no;
		
		NodeList nl = pinInfo.getChildNodes().item(0).getChildNodes();
		
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attr = n.getAttributes();

				if (attr.item(0).getNodeValue().equals(type)) {
					NodeList pinList = n.getChildNodes();

					for (int j = 0; j < pinList.getLength(); j++) {
						String s = pinList.item(j).getTextContent().trim();
						
						if (!s.equals("")) {
							String id = pinList.item(j).getTextContent();
							pins.put(id, new Pin(id, this));
						}
					}
				}
			}
		}
		
		System.out.println("Created component: " + id);
	}
	
	public static Document parseXML(String file) {
		Document doc = null;
		try {
			File f = new File(file);
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return doc;
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
	
	public void addPin(Pin pin) {
		pins.put(pin.getId(), pin);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
}
