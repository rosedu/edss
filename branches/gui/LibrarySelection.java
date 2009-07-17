import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
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
	private Vector <String> currentTable = new Vector <String> ();
	private Vector <String> favoritesTable = new Vector <String> ();
	
	public LibrarySelection(JFrame f, final JList current, final JList favorites)
	{	
		libraryFrame = new JDialog(f, "Library", true);
		libraryFrame.setLayout(new BorderLayout());
		libraryFrame.setSize(500,375);
		libraryFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		libraryFrame.setAlwaysOnTop(true);
		
		final DefaultListModel model = new DefaultListModel();
		model.addElement("Tranzistor");
		model.addElement("Rezistor");
		model.addElement("Dioda");
		model.addElement("Sursa curent");
		model.addElement("Capacitate");
		model.addElement("Bobina");
		model.addElement("Sursa tensiune");
		model.addElement("Amplificator");
		model.addElement("Multimetru");
		
		final DefaultListModel customModel = new DefaultListModel();
			for(int i=0;i<model.getSize();i++)
			{
				customModel.addElement(model.get(i));
			}
		
		final JList list = new JList(customModel);
		//JScrollPane pane1 = new JScrollPane(list);
		JScrollPane pane1 = new JScrollPane(new JList(model));
	    //final JTextPane selected = new JTextPane();
		final JLabel selected = new JLabel();
		selected.setText("nothing selected");
		//selected.setEditable(false);
		selected.setForeground(Color.BLUE);
		final JTextField text = new JTextField(12);
			//libraryFrame.addWindowListener(new WindowAdapter(){
			//	   public void windowClosing(WindowEvent e)
			//	   {
			//		   libraryFrame.setVisible(false);
			//	   }
			//});
			text.addKeyListener(new KeyListener()
			{
				String auxiliary = new String();
				
			    public void keyTyped ( KeyEvent e ){  
		    		customModel.removeAllElements();
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
					if(currentTable.contains(selected.getText()) == false && customModel.contains(selected.getText()) == true)
					{
						//JButton b = new JButton(selected.getText());
						//JButton b = new JButton(customModel.get(0).toString());
						// b.setSize(70,20);
						//size.add(b);
						((DefaultListModel)current.getModel()).addElement(selected.getText());
						current.repaint();
						// toolPanel.repaint();
						// toolPanel.validate();
						//current.add(size);
						//System.out.println("bla current");
						currentTable.add(selected.getText());
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
					if(favoritesTable.contains(selected.getText())== false && customModel.contains(selected.getText()) == true)
					{
						// JButton b = new JButton(selected.getText());
						DefaultListModel d = (DefaultListModel) favorites.getModel();
						d.addElement(selected.getText());
						favorites.repaint();
						// toolPanel.repaint();
						// toolPanel.validate();
						//System.out.println("bla fav");
						favoritesTable.add(selected.getText());
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
		
		libraryFrame.add(p,BorderLayout.NORTH);
		libraryFrame.add(pane1,BorderLayout.WEST);
		libraryFrame.add(searchList,BorderLayout.CENTER);
		
		
		
		
		libraryFrame.setLocationRelativeTo(null);
		libraryFrame.setVisible(true);
	}
}
