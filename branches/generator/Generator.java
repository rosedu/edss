import java.awt.event.ItemEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Generator
{
	public static void main(String args[])
	{

		if (args.length!=2) {
			System.err.println("Usage: java Generator cale nume_fisier");
			System.exit(0);
		}

		try {
			BufferedWriter fw=new BufferedWriter(new FileWriter(args[1]));
			
			
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			fw.write("<database>\n");
			
			File directory = new File(args[0]);
			  
			File files[] = directory.listFiles();
			  
			for (File f : files) {
			   
			      if (f.isDirectory() && f.getName().charAt(0)!='.')
			      {
			    	  File subdirectory = new File(f.getAbsolutePath());
			    	  File subfiles[] = subdirectory.listFiles();
			    	  
			    	  fw.write("\t<category name=\"" +f.getName()+"\">\n");
			    	  
			    	  
			    	  for (File f1 : subfiles)
			    	  {
			    		  if (f1.isDirectory() && f1.getName().charAt(0)!='.')
			    		  {
			    			  		fw.write("\t\t<subcategory name=\"" +f1.getName()+"\">\n");
			    		    	  File subdirectory1 = new File(f1.getAbsolutePath());
			    		    	  File subfiles1[] = subdirectory1.listFiles();
			    		    	  
			    		    	  for (File f11 : subfiles1)
			    		    	  {
			    		    		  if (f11.isFile())
			    		    		  {
			    		    			  if (f11.getName().contains("_model"))
			    		    			  {
			    		    				  getModelName(f11.getAbsolutePath(),fw);
			    		    			  }
			    		    		  }
			    		    	  }
			    		    	  fw.write("\t\t</subcategory>\n");		    		    	  
			    		      
			    		  }
			    	  }
			    	  
			    	  fw.write("\t</category>\n");
			    	  
			      }
			   }
			
			fw.write("</database>\n");
			fw.close();
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	private static void getModelName(String absolutePath,BufferedWriter fw) {
		// TODO Auto-generated method stub
		
		
	try {
		Document doc=	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(absolutePath));
		
		visit(doc,0,fw);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
	}
	
	
	public static void visit(Node node, int level,BufferedWriter fw) throws DOMException, IOException
	{
		NodeList nl = node.getChildNodes();
		
		for(int i=0, cnt=nl.getLength(); i<cnt; i++)
		{
			if (nl.item(i).getNodeName().equals("model"))
			{
				NamedNodeMap np=nl.item(i).getAttributes();
				for (int j=0;j<np.getLength();j++)
				{
					if (np.item(j).getNodeName().equals("name"))
					{
						fw.write("\t\t\t<piece>"  +np.item(j).getNodeValue()+"</piece>\n");
						//System.out.println("\t\t"+np.item(j).getNodeValue());
					}
				}
			}
			
			visit(nl.item(i), level+1,fw);
		}
	}

	
}
