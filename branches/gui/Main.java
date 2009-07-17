import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

// import com.jgoodies.forms.factories.Borders;


// Main Class
public class Main {
	
	public Vector v = new Vector();
	public int i = 0;
	public final String	CONFIG_FILE = "config.xml";
	
	// class variables
	JFrame mainFrame;
	JMenuBar mainMenu;
	
	JPanel customPanel;
	JPanel leftPanel;
	JToolBar current;
	JToolBar favorites;
	JList lCurrent;
	JList lFavorites;
	JPanel preview;
	JLabel previewLabel;
	
	JDesktopPane centerPanel;
	JPanel rightPanel;
	
	Vector <NewInternalFrame> w = new Vector <NewInternalFrame> ();  
	
	int coordonates = 1;
	Mediator med = new Mediator();
	
	// constructor method
	public Main()
	{
		// mainFrame initialization
		mainFrame = new JFrame("EDSS");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(1000,550);
		
		centerPanel = new JDesktopPane();
		// centerPanel.setLayout(new GridLayout(0, 1));
		
		mainMenu();
		
		med.registerDesktop(centerPanel);
		
		Configuration config = new Configuration(CONFIG_FILE);
		// config.show();
		
		customPanel = new JPanel(new GridLayout(1,0));
		
		for(int i=0; i<config.toolbarsNr; i++)
		{
			JToolBar customToolBar = new JToolBar();
			Vector<Integer> aux = config.toolButtons.get(i);
			int j;
			for(j=0; j<aux.size(); j++)
			{
				if(aux.get(j).intValue() == -1)
				{
					customToolBar.addSeparator();
					continue;
				}
				 JButton auxiliaryButton = new JButton(((JMenuItem)v.get(aux.get(j).intValue())).getIcon());
				 auxiliaryButton.setToolTipText(((JMenuItem)v.get(aux.get(j).intValue())).getText());
				 if(((JMenuItem)v.get(aux.get(j).intValue())).getActionListeners().length != 0)
					 auxiliaryButton.addActionListener(((JMenuItem)v.get(aux.get(j).intValue())).getActionListeners()[0]);
				 customToolBar.add(auxiliaryButton);
			}
			customPanel.add(customToolBar);
		}
		
		mainFrame.add(customPanel, BorderLayout.NORTH);
		// panel definition
		
		leftPanel = new JPanel(new FlowLayout());//new GridLayout(3,1));
		// leftPanel.setBackground(Color.RED);
		leftPanel();
		
		//centerPanel.setBackground(Color.CYAN);
		
		rightPanel = new JPanel(new FlowLayout());
		rightPanel.setBackground(Color.BLUE);
		rightPanel();
		
		mainFrame.add(leftPanel, BorderLayout.WEST);
		mainFrame.add(centerPanel,BorderLayout.CENTER);
		mainFrame.add(rightPanel, BorderLayout.EAST);
		
		mainFrame.setVisible(true);
	}
	
	
	private void mainMenu()
	{
		// mainMenu initialization
		mainMenu = new JMenuBar();
		
		// static menu building
		JMenu file = new JMenu("File");
			JMenuItem fNew = new JMenuItem("New",new ImageIcon("Icons\\new.jpg"));
				fNew.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					NewInternalFrame newInternalFrame = new NewInternalFrame("New project", coordonates++);
					centerPanel.add(newInternalFrame);
					w.add(newInternalFrame);
					centerPanel.getDesktopManager().activateFrame(w.get(coordonates - 2));
					System.out.println("New");
					}					
				});
				
			JMenuItem fOpen = new JMenuItem("Open",new ImageIcon("Icons\\open.jpg"));
				fOpen.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Open");
					JFileChooser chOpen = new JFileChooser();
					FileFilter myFilter = new FileNameExtensionFilter("*.svg", "svg");
					chOpen.addChoosableFileFilter(myFilter);
					chOpen.showOpenDialog(mainFrame);
					
					if(chOpen.getSelectedFile().getName() != null)
						System.out.println(chOpen.getSelectedFile().getAbsolutePath());
					}					
				});
			
			JMenuItem fSave = new JMenuItem("Save",new ImageIcon("Icons\\save.jpg"));
				fSave.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Save");
					}					
				});
				
			JMenuItem fSaveAs = new JMenuItem("Save as",new ImageIcon("Icons\\saveas.jpg"));
				fSaveAs.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Save as");
					}					
				});
				
			JMenuItem fExit = new JMenuItem("Exit",new ImageIcon("Icons\\exit.jpg"));
				fExit.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Exit");
					}					
				});
			
			file.add(fNew);
			add(fNew);
			file.add(fOpen);
			add(fOpen);
			file.add(fSave);
			add(fSave);
			file.add(fSaveAs);
			add(fSaveAs);
			file.add(fExit);
			add(fExit);
			
		JMenu view = new JMenu("View");
			JMenuItem vZoomIn = new JMenuItem("Zoom in",new ImageIcon("Icons\\zoomin.jpg"));
				vZoomIn.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Zoom in");
					if((NewInternalFrame) centerPanel.getSelectedFrame() != null)
					{
						((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor() + 10);
						med.zoom(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
						System.out.println("Zoom = " + ((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
					}	
					}
				});
			JMenuItem vZoomOut = new JMenuItem("Zoom out",new ImageIcon("Icons\\zoomout.jpg"));
				vZoomOut.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Zoom out");
					if((NewInternalFrame) centerPanel.getSelectedFrame() != null)
					{
						((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor() - 10);
						med.zoom(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
						System.out.println("Zoom = " + ((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
					}
					}					
				});
			
			view.add(vZoomIn);
			add(vZoomIn);
			view.add(vZoomOut);
			add(vZoomOut);
			
		JMenu edit = new JMenu("Edit");
			JMenuItem eUndo = new JMenuItem("Undo",new ImageIcon("Icons\\undo.jpg"));
				eUndo.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Undo");
					}					
				});
				
			JMenuItem eRedo = new JMenuItem("Redo",new ImageIcon("Icons\\redo.jpg"));
				eRedo.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Redo");
					}					
				});
				
			JMenuItem eFind = new JMenuItem("Find",new ImageIcon("Icons\\find.jpg"));
				eFind.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Find");
					}					
				});
				
			JMenuItem eCut = new JMenuItem("Cut",new ImageIcon("Icons\\cut.jpg"));
				eCut.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Cut");
					}					
				});
				
			JMenuItem eCopy = new JMenuItem("Copy",new ImageIcon("Icons\\copy.jpg"));
				eCopy.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
				{
					System.out.println("Copy");
				}					
				});
				
			JMenuItem ePaste = new JMenuItem("Paste",new ImageIcon("Icons\\paste.jpg"));
				ePaste.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Paste");
					}					
				});
			
			JMenuItem eCustomize = new JMenuItem("Customize",new ImageIcon("Icons\\customize.jpg"));
				eCustomize.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Customize");
					}					
				});
			
			
			edit.add(eUndo);
			add(eUndo);
			edit.add(eRedo);
			add(eRedo);
			edit.add(eFind);
			add(eFind);
			edit.add(eCut);
			add(eCut);
			edit.add(eCopy);
			add(eCopy);
			edit.add(ePaste);
			add(ePaste);
			edit.add(eCustomize);
			add(eCustomize);
			
		JMenu library = new JMenu("Library");
			JMenuItem lOpenLibrary = new JMenuItem("Open library",new ImageIcon("Icons\\openlibrary.jpg"));
			lOpenLibrary.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					LibrarySelection l = new LibrarySelection(mainFrame, lCurrent, lFavorites);
				}
			});
			JMenuItem lLibraryManager = new JMenuItem("Library manager",new ImageIcon("Icons\\librarymanager.jpg"));
			
			library.add(lOpenLibrary);
			add(lOpenLibrary);
			library.add(lLibraryManager);
			add(lLibraryManager);
			
		JMenu tools = new JMenu("Tools");
			JMenuItem tRotateLeft = new JMenuItem("Rotate left",new ImageIcon("Icons\\rotateleft.jpg"));
			tRotateLeft.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Rotate left");
				}
			});
			
			JMenuItem tRotateRight = new JMenuItem("Rotate right",new ImageIcon("Icons\\rotateright.jpg"));
			tRotateRight.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Rotate right");
				}
			});
			
			tools.add(tRotateLeft);
			add(tRotateLeft);
			tools.add(tRotateRight);
			add(tRotateRight);
			
		JMenu help = new JMenu("Help");
			JMenuItem hIndex = new JMenuItem("Index",new ImageIcon("Icons\\info.jpg"));
			JMenuItem hAbout = new JMenuItem("About",new ImageIcon("Icons\\about.jpg"));
			
			help.add(hIndex);
			add(hIndex);
			help.add(hAbout);
			add(hAbout);
		
		mainMenu.add(file);
		mainMenu.add(view);
		mainMenu.add(edit);
		mainMenu.add(library);
		mainMenu.add(tools);
		mainMenu.add(help);
		
		mainMenu.setVisible(true);
		
		mainFrame.setJMenuBar(mainMenu);
	}
	
	private void leftPanel()
	{
		current = new JToolBar("Current", 1);
			// current.add(new JButton("no1"));
		favorites = new JToolBar("Favorites", 1);
			// favorites.add(new JButton("no2"));
		lCurrent = new JList(new DefaultListModel());
		lCurrent.setName("Current");
		lFavorites = new JList(new DefaultListModel());
		lFavorites.setName("Favorites");
		
		preview = new JPanel();
		preview.setBackground(Color.BLACK);
		previewLabel = new JLabel("preview");
		previewLabel.setForeground(Color.WHITE);
		preview.add(previewLabel);
		
		JScrollPane sp1 = new JScrollPane(lCurrent);
		sp1.setSize(125, 75);
		sp1.setPreferredSize(new Dimension(125, 75));
		current.add(sp1);
		
		JScrollPane sp2 = new JScrollPane(lFavorites);
		sp2.setSize(125, 75);
		sp2.setPreferredSize(new Dimension(125, 75));
		favorites.add(sp2);
		
		leftPanel.setLayout(new GridLayout(0,1));
		leftPanel.add(current);//, BorderLayout.NORTH);
		leftPanel.add(favorites);//, BorderLayout.CENTER);
		leftPanel.add(preview);//, BorderLayout.SOUTH);
	}

	private void rightPanel()
	{
		JToolBar rightToolBar = new JToolBar(1);
		final ButtonGroup buttonGroup = new ButtonGroup();
		
		int s = 20;
		final JToggleButton N = new JToggleButton("N");
		N.setSize(s, s);
		N.setPreferredSize(new Dimension(s, s));
		final JToggleButton M = new JToggleButton("M");
		M.setSize(s, s);
		M.setPreferredSize(new Dimension(s, s));
		final JToggleButton L = new JToggleButton("L");
		L.setSize(s, s);
		L.setPreferredSize(new Dimension(s, s));
		L.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.setSelected((JToggleButton.ToggleButtonModel) M.getModel(),true);
				LibrarySelection l = new LibrarySelection(mainFrame, lCurrent, lFavorites);
			}
		});
		
		JToggleButton D = new JToggleButton("D");
		D.setSize(s, s);
		D.setPreferredSize(new Dimension(s, s));
		JToggleButton P = new JToggleButton("P");
		P.setSize(s, s);
		P.setPreferredSize(new Dimension(s, s));
		JToggleButton G = new JToggleButton("G");
		G.setSize(s, s);
		G.setPreferredSize(new Dimension(s, s));
		
		D.setToolTipText("Delete");
		L.setToolTipText("Library");
		P.setToolTipText("Place");
		G.setToolTipText("Drag item");
		M.setToolTipText("Mouse");
		N.setToolTipText("Line");
		
		buttonGroup.add(L);
		buttonGroup.add(D);
		buttonGroup.add(P);
		buttonGroup.add(G);
		buttonGroup.add(M);
		buttonGroup.add(N);
		
		rightToolBar.add(L);
		rightToolBar.add(D);
		rightToolBar.add(P);
		rightToolBar.add(G);
		rightToolBar.add(M);
		rightToolBar.add(N);
		
		//rightToolBar.add(buttonGroup);
		rightPanel.add(rightToolBar);
		
	}
	// add button to data structure v
	public void add(JComponent b)
	{
		v.add(b);
		i=i+1;
	}
	
	// main
	public static void main(String args[])
	{
		Main window = new Main();
	}
	

}
