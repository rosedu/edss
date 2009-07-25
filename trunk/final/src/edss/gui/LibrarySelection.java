package edss.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class LibrarySelection {
	
	private JDialog libraryFrame;
	private Vector <Piece> currentTable = new Vector <Piece> ();
	private Vector <Piece> favoritesTable = new Vector <Piece> ();
	
	public LibrarySelection(JFrame f, final JList current, final JList favorites, final Vector v1, final Vector v2)
	{	
		currentTable = v1;
		favoritesTable = v2;
		
		libraryFrame = new JDialog(f, "Library", true);
		libraryFrame.setLayout(new BorderLayout());
		libraryFrame.setSize(600,500);
		libraryFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		libraryFrame.setAlwaysOnTop(true);
		
		final DefaultListModel modelCategory = new DefaultListModel();
		
		final ParseDatabase dataBase = new ParseDatabase("database.xml");
		modelCategory.addElement("all");
		for(int i=0; i<dataBase.data.n; i++)
		{
			modelCategory.addElement(dataBase.data.sons[i].value);
		}
		
		final DefaultListModel modelSubcategory = new DefaultListModel();
		
		final DefaultListModel customModel = new DefaultListModel();
		
		final JList catList = new JList(modelCategory);
		JScrollPane pane1 = new JScrollPane(catList);
		// adding subcategories to a JList
		int catSelected = catList.getSelectedIndex();  
		modelSubcategory.addElement("all");
		if(catSelected == -1)
			catSelected = 0;
		
		if(catSelected == 0)
		{
			for(int i=0; i<dataBase.data.n; i++)
			{
				for(int j=0; j<dataBase.data.sons[i].n; j++)
					modelSubcategory.addElement(dataBase.data.sons[i].sons[j].value);
			}
		}
		else 
		{
			Tree subCatTree = dataBase.data.sons[catSelected - 1];
			for(int i=0; i<subCatTree.n; i++)
			{
				modelSubcategory.addElement(subCatTree.sons[i]);
			}
		}
		
		final JList subcatList = new JList(modelSubcategory);
		JScrollPane pane1p = new JScrollPane(subcatList);
		
		//obtinem elementul selectat din sublista
		final DefaultListModel model = new DefaultListModel();
		int subcatSelected = subcatList.getSelectedIndex();
		if(subcatSelected < 1)
		{
			for(int i=0; i<dataBase.data.n; i++)
			{
				Tree t = dataBase.data.sons[i];
				for(int j=0; j<t.n; j++)
				{
					Tree s = t.sons[j];
					for(int k=0; k<s.n; k++)
					{
						
						model.addElement(new Piece(s.sons[k].value, t.value, s.value));
					}
				}
			}
		}
		else
		{
			if(catList.getSelectedIndex()>=1)
			{
				Tree pieceTree = dataBase.data.sons[catList.getSelectedIndex() - 1].sons[subcatSelected - 1];
				for(int i=0; i<pieceTree.n; i++)
				{
					model.addElement(new Piece(pieceTree.sons[i].value, catList.getSelectedValue().toString(), pieceTree.value));
				}
			}
			else
			{
				Tree sb = null;
				int c=0,s=0;
				for(int i=0; i<dataBase.data.n; i++)
				{
					for(int j=0; j<dataBase.data.sons[i].n; j++)
					{
						if(dataBase.data.sons[i].sons[j].value.equals(subcatList.getSelectedValue().toString()))
						{
							sb = dataBase.data.sons[i].sons[j];
							c = i;
							s = j;
							break;
						}
							
					}
				}
				for(int i=0; i<sb.n; i++)
				{
					model.addElement(new Piece(sb.sons[i].value, dataBase.data.sons[c].value, dataBase.data.sons[c].sons[s].value ));
				}
			}
		}
		
		for(int i=0; i<model.getSize(); i++)
			customModel.addElement(model.elementAt(i));
		
		final JLabel selected = new JLabel();
		selected.setText("nothing selected");
		selected.setForeground(Color.BLUE);
		selected.setPreferredSize(new Dimension(100, 15));
		final JTextField text = new JTextField(12);

			text.addKeyListener(new KeyListener()
			{
				String auxiliary = new String();
				
			    public void keyTyped ( KeyEvent e ){  
		    		customModel.removeAllElements();
		    		if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
		    			libraryFrame.dispose();
			    	if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && auxiliary.length()>=1)
			    	{
			    		auxiliary = new String(auxiliary.substring(0, auxiliary.length()-1));
			    		selected.setText(auxiliary);
			    		//System.out.println("am apasat backspace");
			    	}
			    	else
			    	{
			    		if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
			    			auxiliary = "";
			    		else
			    		{
			    			auxiliary = auxiliary + e.getKeyChar();
			    			selected.setText(auxiliary);
			    		}
			    	}
			    	if(auxiliary.equals("") == true)
			    	{
			    		for(int i=0;i<model.getSize();i++)
			    			customModel.addElement(model.get(i));
			    	}
			    		
			    	else 
			    	{
			    		for(int i=0;i<model.getSize();i++)
			    		{
			    			if(model.get(i).toString().toLowerCase().contains(auxiliary.toLowerCase())==true)
			    				customModel.addElement(model.get(i));
			    		}
			        //System.out.println(""+e.getKeyChar());
			    	}
			         }  
			        public void keyPressed ( KeyEvent e){  

			        //selected.setText(""+e.getKeyChar());
			        //System.out.println(""+e.getKeyChar());
			        }  
			        public void keyReleased ( KeyEvent e ){  
			        //selected.setText(""+e.getKeyChar());
			        //System.out.println(""+e.getKeyChar());
			        }
			});

		final JList searchList = new JList(customModel);
		JButton add1 = new JButton("Add Current");
		
		
		
		add1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(selected.getText().contains("nothing")!=true)
				{
					// Piece pSS =new Piece(selected.getText(), catList.getSelectedValue().toString(), subcatList.getSelectedValue().toString());
					Piece pSS = null;
					for(int i=0; i<customModel.getSize(); i++)
					{
						Piece aux = (Piece)customModel.elementAt(i);
						if(selected.getText().equals(aux.getName()))
						{
							pSS = aux;
							break;
						}
					}
					
					if(currentTable.contains(pSS) == false && pSS != null) //customModel.contains(selected.getText()) == true)
					{
						((DefaultListModel)current.getModel()).addElement(selected.getText());
						current.repaint();
						currentTable.add(pSS); //MODIFICAT
					}
				}
			}
		});
		
		JButton add2 = new JButton("Add Favorites");
		//add2.addActionListener(add1.getListeners(ActionListener.class)[0]);
		
		
		add2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(selected.getText().contains("nothing")!=true)
				{
					// Piece pSS =new Piece(selected.getText(), catList.getSelectedValue().toString(), subcatList.getSelectedValue().toString());
					Piece pSS = null;
					for(int i=0; i<customModel.getSize(); i++)
					{
						Piece aux = (Piece)customModel.elementAt(i);
						if(selected.getText().equals(aux.getName()))
						{
							pSS = aux;
							break;
						}
					}
					
					if(favoritesTable.contains(pSS)== false && pSS != null) //customModel.contains(selected.getText()) == true)
					{
						// JButton b = new JButton(selected.getText());
						DefaultListModel d = (DefaultListModel) favorites.getModel();
						d.addElement(selected.getText());
						favorites.repaint();
						// toolPanel.repaint();
						// toolPanel.validate();
						//System.out.println("bla fav");
						favoritesTable.add(pSS); //MODIFICAT
					}
				}
			}
		});
		
		JPanel p = new JPanel(new FlowLayout());
		p.setSize(50,50);
		p.add(selected);
		p.add(text);
		p.add(add1);
		p.add(add2);
		
		searchList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				if(searchList.getSelectionModel().isSelectionEmpty() == false)
					selected.setText(searchList.getSelectedValue().toString());
				//text.setText(searchList.getSelectedValue().toString());
			}
		});
		
		catList.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(catList.getSelectionModel().isSelectionEmpty() == false)
				{
					modelSubcategory.removeAllElements();
					int catSelected = catList.getSelectedIndex();  
					modelSubcategory.addElement("all");
					if(catSelected == -1)
						catSelected = 0;
					
					if(catSelected == 0)
					{
						for(int i=0; i<dataBase.data.n; i++)
						{
							for(int j=0; j<dataBase.data.sons[i].n; j++)
								modelSubcategory.addElement(dataBase.data.sons[i].sons[j].value);
						}
					}
					else 
					{
						Tree subCatTree = dataBase.data.sons[catSelected - 1];
						for(int i=0; i<subCatTree.n; i++)
						{
							modelSubcategory.addElement(subCatTree.sons[i].value);
						}
					}
				subcatList.setSelectedIndex(0);
				subcatList.repaint();
				}
			}
			
		});
		
		subcatList.addListSelectionListener(new ListSelectionListener()
		{
			
			public void valueChanged(ListSelectionEvent e) {
				
				model.removeAllElements();
				int subcatSelected = subcatList.getSelectedIndex();
				if(subcatSelected < 1 && catList.getSelectedIndex() < 1)
				{
					for(int i=0; i<dataBase.data.n; i++)
					{
						Tree t = dataBase.data.sons[i];
						for(int j=0; j<t.n; j++)
						{
							Tree s = t.sons[j];
							for(int k=0; k<s.n; k++)
							{
								model.addElement(new Piece(s.sons[k].value, t.value, s.value)); 
							}
						}
					}
				}
				if(subcatSelected < 1 && catList.getSelectedIndex() >= 1)
				{
						Tree t = dataBase.data.sons[catList.getSelectedIndex() - 1];
						for(int j=0; j<t.n; j++)
						{
							Tree s = t.sons[j];
							for(int k=0; k<s.n; k++)
							{
								model.addElement(new Piece(s.sons[k].value, t.value, s.value)); 
							}
						}
				}
				else
				{
					if(catList.getSelectedIndex()>=1)
					{
						Tree pieceTree = dataBase.data.sons[catList.getSelectedIndex() - 1].sons[subcatSelected - 1];
						for(int i=0; i<pieceTree.n; i++)
						{
							model.addElement(new Piece(pieceTree.sons[i].value, catList.getSelectedValue().toString(), subcatList.getSelectedValue().toString()));
						}
					}
					else
					{
						Tree sb = null;
						String catg = null;
						//if(subcatList.getSelectedIndex() == -1)
						//	subcatList.setSelectedIndex(0);
						for(int i=0; i<dataBase.data.n; i++)
						{
							for(int j=0; j<dataBase.data.sons[i].n; j++)
							{
								if(subcatList.getSelectedValue() != null)
									if(dataBase.data.sons[i].sons[j].value.equals(subcatList.getSelectedValue().toString()))
									{
										sb = dataBase.data.sons[i].sons[j];
										catg = dataBase.data.sons[i].value;
										break;
									}
									
							}
						}
						if(sb != null)
							for(int i=0; i<sb.n; i++)
							{
								model.addElement(new Piece(sb.sons[i].value, catg, sb.value));
							}
					}
				}
			customModel.removeAllElements();
			for(int i=0; i<model.getSize(); i++)
			{
				customModel.addElement(model.elementAt(i));
			}
			searchList.repaint();
			}
			
		});
		
		
		
		
		libraryFrame.add(p,BorderLayout.NORTH);
			JPanel pp = new JPanel(new GridLayout(0,1));
			pane1.setSize(150, 400);
			pane1p.setSize(150, 400);
			pane1.setPreferredSize(new Dimension(150, 400));
			pane1p.setPreferredSize(new Dimension(150, 400));
			pp.add(pane1);
			pp.add(pane1p);
		libraryFrame.add(pp,BorderLayout.WEST);
		// libraryFrame.add(searchList,BorderLayout.CENTER);
		
		JPanel preview;
		JLabel previewLabel;
		int WIDTH = 800;
		int HEIGHT = 600;
		preview = new JPanel();
		preview.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 4, true)); 
		preview.setBackground(Color.BLACK);
		preview.setSize(2*WIDTH/10,2*HEIGHT/10);
		previewLabel = new JLabel("preview");
		previewLabel.setForeground(Color.WHITE);
		preview.add(previewLabel);
		
		JPanel auxPrevSearch = new JPanel(new GridLayout(0,1));
		JScrollPane scor = new JScrollPane(searchList);

		auxPrevSearch.add(scor);
		auxPrevSearch.add(preview);
		libraryFrame.add(auxPrevSearch, BorderLayout.CENTER);
		
		libraryFrame.setLocationRelativeTo(null);
		libraryFrame.setVisible(true);
	}
}
