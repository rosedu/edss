package edss.gui;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;


public class Customize {
		
	public final String	CONFIG_FILE = "config.xml";	
	Configuration config = new Configuration(CONFIG_FILE);
	JDialog customDialog;
	JPanel allButtons;
	JPanel selectedButtons;
	JPanel emptyPanel;
	JComboBox combo;
	JList list;
	JPanel masterPanel;
	JButton addB;
	JButton create;
	JButton clear;
	JButton saveas;	
	JButton remove;
	Checkbox cb;
	JTabbedPane pane;
	final Vector <String> bn = new Vector <String> ();	
	DefaultListModel selected = new DefaultListModel();
	int skinId = 0;
	
	public Customize(JFrame f, final Vector v, JPanel p, int sk)
	{ 	
		skinId = sk;
		int W = 100;
		int H = 50;
		addB = new JButton("Add...");
		addB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    addB.setMaximumSize(new Dimension(W,H));
	    addB.setMinimumSize(new Dimension(W,H));
		addB.setSize(W,H);
		addB.setPreferredSize(new Dimension(W,H));
		
	    create = new JButton("New");
		create.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    create.setMaximumSize(new Dimension(W,H));
	    create.setMinimumSize(new Dimension(W,H));
		create.setSize(W,H);
		create.setPreferredSize(new Dimension(W,H));
		
	    clear = new JButton("Clear");
		clear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    clear.setMaximumSize(new Dimension(W,H));
	    clear.setMinimumSize(new Dimension(W,H));
		clear.setSize(W,H);
		clear.setPreferredSize(new Dimension(W,H));
		
	    saveas = new JButton("Save as");	
		saveas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    saveas.setMaximumSize(new Dimension(W,H));
	    saveas.setMinimumSize(new Dimension(W,H));
		saveas.setSize(W,H);
		saveas.setPreferredSize(new Dimension(W,H));
		
		remove = new JButton("Remove");
		remove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		remove.setMaximumSize(new Dimension(W,H));
		remove.setMinimumSize(new Dimension(W,H));
		remove.setSize(W,H);
		remove.setPreferredSize(new Dimension(W,H));
		
		customDialog = new JDialog(f,"Customize your toolbars", true);
		//customDialog.setLayout(new GridLayout(0,1));
		customDialog.setLayout(new BorderLayout());
		customDialog.setBackground(Color.darkGray);
		customDialog.setSize(500,750);
		customDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
								
		allButtons = new JPanel(new GridLayout(0,1));
		selectedButtons = new JPanel(new GridLayout(0,1));
		emptyPanel = new JPanel();  
		emptyPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		emptyPanel.add(create);
		emptyPanel.add(addB);
		emptyPanel.add(remove);
		emptyPanel.add(clear);
		emptyPanel.add(saveas);			
					
		int w = 60;
		int h = 100;
					
					
		// buttons.setBorder(BorderFactory.createBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner);
		allButtons.setBorder(BorderFactory.createLoweredBevelBorder());
		allButtons.setBackground(Color.LIGHT_GRAY);
		allButtons.setPreferredSize(new Dimension(w,h));
		allButtons.setSize(w, h); 
		allButtons.setBounds(5, 5, w, h);
		allButtons.validate();
					
		for(int i = 0 ; i<v.size(); i++)
		{
			cb = new Checkbox(((JMenuItem) v.get(i)).getText());
			cb.setForeground(Color.BLACK);
			allButtons.add(cb);
		}
		cb = new Checkbox("Separator");
		cb.setForeground(Color.BLACK);
		allButtons.add(cb);
		// allButtons.add(new Checkbox("Separator"));
		
		selectedButtons.setBorder(BorderFactory.createLoweredBevelBorder());
		selectedButtons.setBackground(Color.LIGHT_GRAY);
		selectedButtons.setPreferredSize(new Dimension(w,h));
		selectedButtons.setSize(w, h); 
		selectedButtons.setBounds(5, 5, w, h);
		selectedButtons.validate();
		
		final Configuration config = new Configuration(CONFIG_FILE);
					
		for (int i = 0; i < config.toolbarsNr; i++)
		{		
			bn.add("Toolbar " + i);
		}
		combo = new JComboBox(bn);
		
		final DefaultListModel [] listOfButtons = new DefaultListModel[config.toolbarsNr];
		final JList theList = new JList();
//
		for(int i=0; i < config.toolbarsNr; i++)
		{
			listOfButtons[i] = new DefaultListModel();
			Vector<Integer> aux = config.toolButtons.get(i);
			for(int j=0; j<aux.size(); j++)
			{
				if(aux.get(j).intValue() == -1)
				{
					listOfButtons[i].addElement(" - ");
					continue;
				}
				// System.out.println(""+((JMenuItem)v.get(aux.get(j).intValue())).getText());
				listOfButtons[i].addElement(((JMenuItem)v.get(aux.get(j).intValue())).getText());
			}
		}
//		addB.addActionListener(new ActionListener()
//		{public void actionPerformed(ActionEvent e)
//			{ 	for(int i = 0; i < allButtons.getComponents().length; i++)
//				{
//					if (((Checkbox)allButtons.getComponents()[i]).isEnabled() == true)
//					{ 	
//						selected.addElement(((Checkbox)allButtons.getComponents()[i]).getName());
//						selectedButtons.add(new JList(selected));
//					}				
//				}
//			}
//		}); 
		final Vector <Vector <Integer>> ourToolBars = config.toolButtons;
		if(bn.size()>0)
		{
			combo.setSelectedIndex(0);
			DefaultListModel lm = new DefaultListModel();	
			for(int i=0; i<ourToolBars.get(0).size(); i++)
			{	
				if(ourToolBars.get(0).get(i) != -1)
					lm.addElement(((JMenuItem)v.get(ourToolBars.get(0).get(i))).getText());
				else 
					lm.addElement("-");
				// System.out.println("" + i);
			}
			theList.setModel(lm);
			selectedButtons.add(theList);
			selectedButtons.validate();
		}
		combo.setBorder(BorderFactory.createRaisedBevelBorder());
		combo.setMaximumSize(new Dimension(10,10));
		
		
		combo.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{
			int ind = combo.getSelectedIndex();
			// System.out.println("" + ind);
			if(ind != -1)
			{
				if(ind<=ourToolBars.size())
				{
					Vector <Integer> auxVector = ourToolBars.get(ind);
					DefaultListModel lm = new DefaultListModel();	
					for(int i=0; i<auxVector.size(); i++)
					{	
						if(auxVector.get(i) != -1)
							lm.addElement(((JMenuItem)v.get(auxVector.get(i))).getText());
						else 
							lm.addElement("-");
						// System.out.println("" + i);
					}
					theList.setModel(lm);
					selectedButtons.add(theList);
					selectedButtons.validate();
				}
			}
			if(ind == -1)
			{
				theList.setModel(new DefaultListModel());
				selectedButtons.add(theList);
				selectedButtons.validate();
			}
			}			
		});
			
		final JPanel auxCustom = new JPanel(new BorderLayout());
		
		create.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				Vector <Integer> newToolBarVector = new Vector <Integer> ();
				for(int i=0; i<v.size()+1; i++)
				{
					if(((Checkbox)allButtons.getComponents()[i]).getState() == true)
					{
						if(i == v.size())
							newToolBarVector.add(-1);
						else
							newToolBarVector.add(i);
					}
				}
				 if(newToolBarVector.size() != 0)
				 {
					ourToolBars.add(newToolBarVector);
					int ref;
					if(bn.size()>0)
						ref = bn.get(bn.size()-1).charAt(bn.get(bn.size()-1).length()-1)-'0';
					else
						ref = -1;
					// System.out.println(ref);
					// System.out.println("Toolbar " + (ref+1));
					bn.add("Toolbar " + (1+ref));
					combo.setSelectedIndex(bn.size()-1);
				 }
			unselect();
			}
			
		});
		
		saveas.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try {
					BufferedWriter  f = new BufferedWriter (new FileWriter(CONFIG_FILE));
					f.write("<?xml version=\"1.0\" ?>\n");
					f.write("<config>\n\t<skin id = \"" + skinId + "\" />\n"); // in loc de 1 skinul selectat
					for(int i=0; i<ourToolBars.size(); i++)
					{
						f.write("\t\t<toolbar>\n");
						for(int j=0; j<ourToolBars.get(i).size(); j++)
							f.write("\t\t\t<button id = \"" + ourToolBars.get(i).get(j) + "\" />\n");
						f.write("\t\t</toolbar>\n");
					}
					f.write("</config>\n");
					f.close();				
				} catch (Exception e) {
					e.printStackTrace();
				}
			customDialog.dispose();
			}
			
		});
		
		addB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int ind = combo.getSelectedIndex();
				if(ind != -1)
				{
					for(int i=0; i<v.size()+1; i++)
					{
						if(((Checkbox)allButtons.getComponents()[i]).getState() == true && ourToolBars.get(ind).contains(i) == false)
						{
							if(i == v.size())
								i = -1;
							ourToolBars.get(ind).add(i);
							if(i == -1)
								i = v.size();
						}
						combo.setSelectedIndex(ind);
					}
				}
			unselect();
			}
		});
		
		clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(bn.size()>0)
				{
					int ind = combo.getSelectedIndex();
					ourToolBars.remove(ind);
					bn.remove(ind);
					if(bn.size()>0)
						combo.setSelectedIndex(0);
					else 
						combo.setSelectedIndex(-1);
				}
			unselect();
			}
			
		});
		
		remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int x = theList.getSelectedIndex();
				if(x != -1)
					((DefaultListModel)theList.getModel()).remove(x);
				int y = combo.getSelectedIndex();
				if(x>=0 && y>=0)
				{
					ourToolBars.get(y).remove(x);
				}
				// System.out.println("" + x);
			}
		});
		
		masterPanel = new JPanel(new GridLayout(0,3));
		
		masterPanel.add(allButtons, BorderLayout.WEST);
		masterPanel.add(emptyPanel,BorderLayout.CENTER);
		masterPanel.add(selectedButtons, BorderLayout.EAST);
		
			auxCustom.add(combo, BorderLayout.NORTH);
			auxCustom.add(new JPanel(), BorderLayout.CENTER);
			auxCustom.setSize(500,100);
			auxCustom.setPreferredSize(new Dimension(500,100));
			
		
//		customDialog.add(auxCustom, BorderLayout.NORTH);
		masterPanel.setSize(500,650);
		masterPanel.setPreferredSize(new Dimension(500, 650));
//		customDialog.add(masterPanel, BorderLayout.CENTER);
		JPanel secondryPanel = new JPanel(new BorderLayout());
		secondryPanel.add(auxCustom, BorderLayout.NORTH);
		secondryPanel.add(masterPanel, BorderLayout.CENTER);
		
		pane = new JTabbedPane();
		pane.addTab("Toolbar Editor", secondryPanel);
		JPanel thirdPanel = new JPanel(new FlowLayout());
		
		JButton saveSkin = new JButton("Save");
		saveSkin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		
		
		JButton cancelSkin = new JButton("Cancel");
		cancelSkin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		
		
		final SubstanceSkinComboSelector sscs = new SubstanceSkinComboSelector();
		thirdPanel.add(sscs);
		
		saveSkin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				skinId = sscs.getSelectedIndex();
				try {
					BufferedWriter  f = new BufferedWriter (new FileWriter(CONFIG_FILE));
					f.write("<?xml version=\"1.0\" ?>\n");
					f.write("<config>\n\t<skin id = \"" + skinId + "\" />\n"); // in loc de 1 skinul selectat
					for(int i=0; i<ourToolBars.size(); i++)
					{
						f.write("\t\t<toolbar>\n");
						for(int j=0; j<ourToolBars.get(i).size(); j++)
							f.write("\t\t\t<button id = \"" + ourToolBars.get(i).get(j) + "\" />\n");
						f.write("\t\t</toolbar>\n");
					}
					f.write("</config>\n");
					f.close();				
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			customDialog.dispose();
			}
		});
		
		cancelSkin.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sscs.setSelectedIndex(skinId);
				customDialog.dispose();
				
			}
			
		});
		thirdPanel.add(saveSkin);
		thirdPanel.add(cancelSkin);
		
		
		pane.addTab("Skin Editor", thirdPanel);
		
		customDialog.add(pane);
		
		customDialog.setVisible(true);		
		
	}
	public void unselect()
	{
		for(int i=0; i<allButtons.getComponents().length; i++)
		{
			((Checkbox)allButtons.getComponents()[i]).setState(false);
		}
	}

}

////*******************************************************************************************				
//eCustomize.addActionListener(new ActionListener() 
//	{	public void actionPerformed(ActionEvent e)
//		{
//		System.out.println("Customize");
//		Customize x = new Customize(mainFrame,v,customPanel);
//		}					
//	});
////*******************************************************************************************

