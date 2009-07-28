package edss.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.batik.transcoder.TranscoderException;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;
import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;
import org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.skin.SubstanceDustCoffeeLookAndFeel;
import org.jvnet.substance.skin.SubstanceDustLookAndFeel;
import org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel;
import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;
import org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel;
import org.jvnet.substance.skin.SubstanceMistSilverLookAndFeel;
import org.jvnet.substance.skin.SubstanceModerateLookAndFeel;
import org.jvnet.substance.skin.SubstanceNebulaBrickWallLookAndFeel;
import org.jvnet.substance.skin.SubstanceNebulaLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenLookAndFeel;
import org.jvnet.substance.skin.SubstanceSaharaLookAndFeel;
import org.jvnet.substance.skin.SubstanceTwilightLookAndFeel;

import edss.canvas.StateConstant;
import edss.gui.Piece;
import edss.interf.GuiMediator;

// import com.jgoodies.forms.factories.Borders;


// Main Class
public class GuiImpl implements edss.interf.Gui{
	
	public String fileName;
	public Vector v = new Vector();
	public int i = 0;
	public final String	CONFIG_FILE = "config.xml";
	public final int WIDTH = 800;
	public final int HEIGHT = 600;
	// class variables
	JFrame mainFrame;
	JMenuBar mainMenu;
	
	JPanel customPanel;
	JPanel leftPanel;
	JPanel buttonPanel;
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
	// Mediator med = new Mediator();
	
	Vector <Piece> currentVector = new Vector <Piece> ();
	Vector <Piece> favoritesVector = new Vector <Piece> ();	
	
	final JToggleButton P = new JToggleButton("Piece");
	final JToggleButton N = new JToggleButton("Wire");
	final JToggleButton M = new JToggleButton("Mouse");
	final JToggleButton L = new JToggleButton("Library");
	final JToggleButton D = new JToggleButton("Delete");
	final JToggleButton G = new JToggleButton("Drag");
	final JToggleButton J = new JToggleButton("Junction");
	NewInternalFrame newInternalFrame;
	Vector <GuiMediator> mediatorVector = new Vector <GuiMediator> ();
	GuiMediator mediator; 
	
	// constructor method
	public GuiImpl()
	{
		// this.mediator = mediator;
		edss.gui.Configuration cf = new edss.gui.Configuration("config.xml");
		final int skin = cf.skinId;
		// ParseDatabase dataBase = new ParseDatabase("database.xml");
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					switch(skin)
					{
					case 0 : UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel()); break;
					case 1 : UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel()); break;
					case 2 : UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel()); break;
					case 3 : UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel()); break;
					case 4 : UIManager.setLookAndFeel(new SubstanceChallengerDeepLookAndFeel()); break;
					case 5 : UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel()); break;
					case 6 : UIManager.setLookAndFeel(new SubstanceCremeCoffeeLookAndFeel()); break;
					case 7 : UIManager.setLookAndFeel(new SubstanceDustLookAndFeel()); break;
					case 8 : UIManager.setLookAndFeel(new SubstanceDustCoffeeLookAndFeel()); break;
					case 9 : UIManager.setLookAndFeel(new SubstanceEmeraldDuskLookAndFeel()); break;
					case 10 : UIManager.setLookAndFeel(new SubstanceMagmaLookAndFeel()); break;
					case 11 : UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel()); break;
					case 12 : UIManager.setLookAndFeel(new SubstanceMistSilverLookAndFeel()); break;
					case 13 : UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel()); break;
					case 14 : UIManager.setLookAndFeel(new SubstanceNebulaLookAndFeel()); break;
					case 15 : UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel()); break;
					case 16 : UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel()); break;
					case 17 : UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel()); break;
					case 18 : UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel()); break;
					case 19 : UIManager.setLookAndFeel(new SubstanceRavenGraphiteLookAndFeel()); break;
					case 20 : UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel()); break;
					case 21 : UIManager.setLookAndFeel(new SubstanceSaharaLookAndFeel()); break;
					case 22 : UIManager.setLookAndFeel(new SubstanceTwilightLookAndFeel()); break;
					default : UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
					}
					
					//UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JFrame.setDefaultLookAndFeelDecorated(true);
				auxMeth();
		
			}
		});
	}
	
	public void auxMeth()
	{
		// mediator.registerGui(this);
		// this.mediator = mediator;
		
		
		ParseFavorites p = new ParseFavorites("favorites.xml");
		favoritesVector = p.getPiece();
		
		
		// mainFrame initialization
		mainFrame = new JFrame("EDSS");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(WIDTH, HEIGHT);
		
		centerPanel = new JDesktopPane();
		
		mainMenu();
		
		// med.registerDesktop(centerPanel);
		
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
		
		rightPanel = new JPanel(new FlowLayout());
		rightPanel();
		
		leftPanel = new JPanel(new BorderLayout());
		leftPanel();
		

		
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
			JMenuItem fNew = new JMenuItem("New",new ImageIcon("Icons/new.jpg"));
				fNew.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					newInternalFrame = new NewInternalFrame("New project", coordonates++, null);
					centerPanel.add(newInternalFrame);
					w.add(newInternalFrame);
					// if(w.size() == 1)
					newInternalFrame.addMouseListener(new MouseListener()
					{

						@Override
						public void mouseClicked(MouseEvent e) {
							mediator = newInternalFrame.getMediator();
							if(getSelectedPiece() != null)
								mediator.setPiece(getSelectedPiece().getName(), getSelectedPiece().getCategory(), getSelectedPiece().getSubCategory());
							P.setSelected(true);
							mediator.enterState(StateConstant.PIECESTATE);
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});
						centerPanel.getDesktopManager().activateFrame(newInternalFrame);
					// else
						 //centerPanel.getDesktopManager().activateFrame(w.get(coordonates - 2));
					mediator = newInternalFrame.getMediator();
					register();
					if(getSelectedPiece() != null)
					mediator.setPiece(getSelectedPiece().getName(), getSelectedPiece().getCategory(), getSelectedPiece().getSubCategory());
					mediator.enterState(StateConstant.PIECESTATE);
					//System.out.println(mediator);
					
	
					
					mediator.addPanel();
					System.out.println("New");
					}					
				});
			KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK);
			fNew.setAccelerator(key);
			
			JMenuItem fOpen = new JMenuItem("Open",new ImageIcon("Icons/open.jpg"));
				fOpen.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
						System.out.println("Open");
						JFileChooser chOpen = new JFileChooser();
						FileFilter myFilter1 = new FileNameExtensionFilter("*.svg", "svg");
						FileFilter myFilter2 = new FileNameExtensionFilter("*.sch", "sch");
						chOpen.addChoosableFileFilter(myFilter1);
						chOpen.addChoosableFileFilter(myFilter2);
						chOpen.showOpenDialog(mainFrame);
					
						if(chOpen.getSelectedFile() != null)
						{
							System.out.println(chOpen.getSelectedFile().getAbsolutePath());
							String toBeOpened = chOpen.getSelectedFile().getAbsolutePath();
							if(toBeOpened.contains(".svg") || toBeOpened.contains(".sch"))
							{
								String auxTBO = "";
								for(int i=0; i<toBeOpened.length()-4; i++)
									auxTBO = auxTBO + toBeOpened.charAt(i);
								File f1 = new File(auxTBO + ".sch");
								File f2 = new File(auxTBO + ".svg");
								if(f1.exists() == false || f2.exists() == false)
									JOptionPane.showMessageDialog(null, "File missing", "ERROR!", JOptionPane.ERROR_MESSAGE);
								else
								{
									NewInternalFrame n = new NewInternalFrame("New project", coordonates++, auxTBO);
									mediator = n.getMediator();
									register();
									centerPanel.add(n);
									w.add(n);
									centerPanel.getDesktopManager().activateFrame(w.get(coordonates - 2));
									mediator.open(auxTBO);
								}
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "File type not suportted", "ERROR!", JOptionPane.ERROR_MESSAGE);
							}
						}
					}					
				});
			
			key = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK);
			fOpen.setAccelerator(key);
			
			JMenuItem fSave = new JMenuItem("Save",new ImageIcon("Icons/save.jpg"));
				fSave.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Save");
					if(getActiveFrame() != null)
					{
						if(getActiveFrame().fileName != null)
						{
							// System.out.println(getActiveFrame().fileName);
							String aux = getActiveFrame().fileName.substring(0, getActiveFrame().fileName.lastIndexOf('.'));
							mediator = getActiveFrame().getMediator();
							mediator.save(aux);
						}
						else
						{
							JFileChooser chSave = new JFileChooser();
							chSave.setDialogType(JFileChooser.SAVE_DIALOG);
							FileFilter myFilter = new FileNameExtensionFilter("*.svg", "svg");
							chSave.addChoosableFileFilter(myFilter);
							chSave.showSaveDialog(mainFrame);
						
							if(chSave.getSelectedFile() != null)
							{
								System.out.println(chSave.getSelectedFile().getAbsolutePath());
								if(chSave.getSelectedFile().getAbsolutePath().contains(".svg"))
								{
									getActiveFrame().fileName = chSave.getSelectedFile().getAbsolutePath();
								}
								else
								{
									getActiveFrame().fileName = chSave.getSelectedFile().getAbsolutePath() + ".svg";
								}
								mediator = getActiveFrame().getMediator();
								mediator.save(chSave.getSelectedFile().getAbsolutePath());
								getActiveFrame().setTitle(getActiveFrame().fileName);
								
							}
						}
					}
					else JOptionPane.showMessageDialog(null, "There is no file to save!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
			fSave.setAccelerator(key);
				
			JMenuItem fSaveAs = new JMenuItem("Save as",new ImageIcon("Icons/save.jpg"));
				fSaveAs.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					if(getActiveFrame() != null) // conditie mai buna pe viitor
					{
						JFileChooser chSave = new JFileChooser();
						chSave.setDialogType(JFileChooser.SAVE_DIALOG);
						FileFilter myFilter = new FileNameExtensionFilter("*.svg", "svg");
						chSave.addChoosableFileFilter(myFilter);
						chSave.showSaveDialog(mainFrame);
					
						if(chSave.getSelectedFile() != null)
						{
							System.out.println(chSave.getSelectedFile().getAbsolutePath());
							mediator = getActiveFrame().getMediator();
							mediator.save(chSave.getSelectedFile().getAbsolutePath());
							getActiveFrame().setTitle(getActiveFrame().fileName);
						}
					}
					else JOptionPane.showMessageDialog(null, "There is no file to save!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					
					System.out.println("Save as");
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK);
			fSaveAs.setAccelerator(key);
			
			JMenuItem fExportToPdf = new JMenuItem("Export to pdf", new ImageIcon("Icons/exportToPdf.jpg"));
			fExportToPdf.addActionListener(new ActionListener() 
			{	public void actionPerformed(ActionEvent e)
				{
					System.out.println("Export to pdf");
					if(getActiveFrame() != null && getActiveFrame().fileName != null)
					{
					ExampleSVG2PDF pdf = new ExampleSVG2PDF();
					try {
						pdf.convertSVG2PDF(new File(getActiveFrame().fileName), new File(getActiveFrame().fileName.substring(0, getActiveFrame().fileName.lastIndexOf('.')) + ".pdf"));
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (TranscoderException e1) {
						e1.printStackTrace();
					}
					}
					else JOptionPane.showMessageDialog(null, "You fail!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}					
			});
		key = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK);
		fExportToPdf.setAccelerator(key);
		
			JMenuItem fExit = new JMenuItem("Exit",new ImageIcon("Icons/exit.jpg"));
				fExit.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Exit");
					writeFavorites();
					System.exit(0);
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK);
			fExit.setAccelerator(key);
			
			file.add(fNew);
			add(fNew);
			file.add(fOpen);
			add(fOpen);
			file.add(fSave);
			add(fSave);
			file.add(fSaveAs);
			add(fSaveAs);
			file.add(fExportToPdf);
			add(fExportToPdf);
			file.add(fExit);
			add(fExit);
			
		JMenu view = new JMenu("View");
			JMenuItem vZoomIn = new JMenuItem("Zoom in",new ImageIcon("Icons/zoomin.jpg"));
				vZoomIn.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Zoom in");
					if((NewInternalFrame) centerPanel.getSelectedFrame() != null)
					{
						((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor() * 2);
						if(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor()<25)
							((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(25);
						if(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor()>400)
							((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(400);
						int y = ((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor();
						switch(y)
						{
						case 25 : y = 1; break;
						case 50 : y = 2; break;
						case 100 : y = 3; break;
						case 200 : y = 4; break;
						case 400 : y = 5; break;
						default : y=3; break;
						}
						((NewInternalFrame) centerPanel.getSelectedFrame()).zoomSlider.setValue(y);
						mediator = getActiveFrame().getMediator();
						mediator.scale(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
						System.out.println("Zoom = " + ((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
					}	
					}
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_MASK);
			vZoomIn.setAccelerator(key);
			
			JMenuItem vZoomOut = new JMenuItem("Zoom out",new ImageIcon("Icons/zoomout.jpg"));
				vZoomOut.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Zoom out");
					if((NewInternalFrame) centerPanel.getSelectedFrame() != null)
					{
						((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor() / 2);
						if(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor()<25)
							((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(25);
						if(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor()>400)
							((NewInternalFrame) centerPanel.getSelectedFrame()).setZoomFactor(400);
						int y = ((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor();
						switch(y)
						{
						case 25 : y = 1; break;
						case 50 : y = 2; break;
						case 100 : y = 3; break;
						case 200 : y = 4; break;
						case 400 : y = 5; break;
						default : y=3; break;
						}
						((NewInternalFrame) centerPanel.getSelectedFrame()).zoomSlider.setValue(y);
						mediator = getActiveFrame().getMediator();
						mediator.scale(((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
						System.out.println("Zoom = " + ((NewInternalFrame) centerPanel.getSelectedFrame()).getZoomFactor());
					}
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_MASK);
			vZoomOut.setAccelerator(key);
			
			view.add(vZoomIn);
			add(vZoomIn);
			view.add(vZoomOut);
			add(vZoomOut);
			
		JMenu edit = new JMenu("Edit");
			JMenuItem eUndo = new JMenuItem("Undo",new ImageIcon("Icons/undo.jpg"));
				eUndo.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Undo");
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK);
			eUndo.setAccelerator(key);
			
			JMenuItem eRedo = new JMenuItem("Redo",new ImageIcon("Icons/redo.jpg"));
				eRedo.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Redo");
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK);
			eRedo.setAccelerator(key);
				
			JMenuItem eFind = new JMenuItem("Find",new ImageIcon("Icons/find.jpg"));
				eFind.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Find");
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK);
			eFind.setAccelerator(key);
				
			JMenuItem eCut = new JMenuItem("Cut",new ImageIcon("Icons/cut.jpg"));
				eCut.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Cut");
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK);
			eCut.setAccelerator(key);
				
			JMenuItem eCopy = new JMenuItem("Copy",new ImageIcon("Icons/copy.jpg"));
				eCopy.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
				{
					System.out.println("Copy");
				}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK);
			eCopy.setAccelerator(key);
				
			JMenuItem ePaste = new JMenuItem("Paste",new ImageIcon("Icons/paste.jpg"));
				ePaste.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Paste");
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK);
			ePaste.setAccelerator(key);
			
			JMenuItem eCustomize = new JMenuItem("Customize",new ImageIcon("Icons/customize.jpg"));
				eCustomize.addActionListener(new ActionListener() 
				{	public void actionPerformed(ActionEvent e)
					{
					System.out.println("Customize");
					
					Configuration config = new Configuration(CONFIG_FILE);
					
					Customize x = new Customize(mainFrame,v,customPanel, config.skinId);
//////////////////////////////////////////////////////////////////////////////////////////
					config = new Configuration(CONFIG_FILE);
					customPanel.removeAll();
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
					customPanel.validate();
					}					
				});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK);
			eCustomize.setAccelerator(key);
			
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
			JMenuItem lOpenLibrary = new JMenuItem("Open library",new ImageIcon("Icons/openlibrary.jpg"));
			lOpenLibrary.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					LibrarySelection l = new LibrarySelection(mainFrame, lCurrent, lFavorites, currentVector, favoritesVector);
				}
			});
			key = KeyStroke.getKeyStroke('l');
			lOpenLibrary.setAccelerator(key);
			
			JMenuItem lLibraryManager = new JMenuItem("Library manager",new ImageIcon("Icons/librarymanager.jpg"));
			key = KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK);
			lLibraryManager.setAccelerator(key);
			
			
			library.add(lOpenLibrary);
			add(lOpenLibrary);
			library.add(lLibraryManager);
			add(lLibraryManager);
			
		JMenu tools = new JMenu("Tools");
			JMenuItem tRotateLeft = new JMenuItem("Rotate left",new ImageIcon("Icons/rotateleft.jpg"));
			tRotateLeft.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Rotate left");
					mediator = getActiveFrame().getMediator();
					mediator.rotate(-90);
				}
			});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, InputEvent.CTRL_MASK);
			tRotateLeft.setAccelerator(key);
			
			JMenuItem tRotateRight = new JMenuItem("Rotate right",new ImageIcon("Icons/rotateright.jpg"));
			tRotateRight.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Rotate right");
					mediator = getActiveFrame().getMediator();
				}
			});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, InputEvent.CTRL_MASK);
			tRotateRight.setAccelerator(key);
			
			JMenuItem tPiece = new JMenuItem("Place piece",new ImageIcon("Icons/piece.jpg"));
			tPiece.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						P.setSelected(true);
					}
				});
			key = KeyStroke.getKeyStroke('p');
			tPiece.setAccelerator(key);
			
			JMenuItem tWire = new JMenuItem("Wire",new ImageIcon("Icons/wire.jpg"));
			tWire.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						N.setSelected(true);
					}
				});
			key = KeyStroke.getKeyStroke('w');
			tWire.setAccelerator(key);
			
			JMenuItem tSelect = new JMenuItem("Mouse",new ImageIcon("Icons/mouse.jpg"));
			tSelect.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						M.setSelected(true);
					}
				});
			key = KeyStroke.getKeyStroke('m');
			tSelect.setAccelerator(key);
			
			JMenuItem tDrag = new JMenuItem("Drag",new ImageIcon("Icons/drag.jpg"));
			tDrag.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						G.setSelected(true);
					}
				});
			key = KeyStroke.getKeyStroke('g');
			tDrag.setAccelerator(key);
			
			JMenuItem tDelete = new JMenuItem("Delete",new ImageIcon("Icons/delete.jpg"));
			tDelete.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						D.setSelected(true);
					}
				});
			key = KeyStroke.getKeyStroke('d');
			tDelete.setAccelerator(key);
			
		
			tools.add(tDelete);
			add(tDelete);
			tools.add(tDrag);
			add(tDrag);
			tools.add(tSelect);
			add(tSelect);
			tools.add(tWire);
			add(tWire);
			tools.add(tPiece);
			add(tPiece);
			tools.addSeparator();
			tools.add(tRotateLeft);
			add(tRotateLeft);
			tools.add(tRotateRight);
			add(tRotateRight);
	
			
		JMenu help = new JMenu("Help");
			JMenuItem hIndex = new JMenuItem("Index",new ImageIcon("Icons/info.jpg"));
			JMenuItem hAbout = new JMenuItem("About",new ImageIcon("Icons/about.jpg"));
			
			help.add(hIndex);
			add(hIndex);
			hIndex.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Index");
				}				
			});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, InputEvent.CTRL_MASK);
			hIndex.setAccelerator(key);
			

			help.add(hAbout);
			add(hAbout);
			hAbout.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("About");
					JOptionPane.showMessageDialog(
							null, "EDSS Summer Project", "About", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			key = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_QUOTE, InputEvent.CTRL_MASK);
			hAbout.setAccelerator(key);
		
		mainMenu.add(file);
		mainMenu.add(view);
		mainMenu.add(edit);
		mainMenu.add(library);
		mainMenu.add(tools);
		mainMenu.add(help);
		
		mainMenu.setVisible(true);
		
		mainFrame.setJMenuBar(mainMenu);
		
		mainFrame.addWindowListener(new WindowListener()
		{

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// scriere in fisierul xml
				writeFavorites(); 
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		centerPanel.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("in");
				if(getActiveFrame() != null)
				{
					System.out.println("click!");
					mediator = getActiveFrame().getMediator();
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	} 
	
	private void leftPanel()
	{
		
		leftPanel.setSize(2*WIDTH/10, 9*HEIGHT/10);
		
		current = new JToolBar("Current", 1);
			// current.add(new JButton("no1"));
		favorites = new JToolBar("Favorites", 1);
			// favorites.add(new JButton("no2"));
		lCurrent = new JList(new DefaultListModel());
		lCurrent.setName("Current");
		lFavorites = new JList(new DefaultListModel());

		
		for(int i=0; i<favoritesVector.size(); i++)
			((DefaultListModel)lFavorites.getModel()).addElement((favoritesVector).get(i).getName());
		lFavorites.setName("Favorites");
	
		lCurrent.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(lCurrent.isSelectionEmpty() == false)
				{
					lFavorites.clearSelection();
					mediator.setPiece(getSelectedPiece().getName(), getSelectedPiece().getCategory(), getSelectedPiece().getSubCategory());
				}
				P.setSelected(true);
				mediator = getActiveFrame().getMediator();
				mediator.setPreview();
				mediator.enterState(StateConstant.PIECESTATE);
					
			}
			
		});
		
	
		
		lFavorites.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(getActiveFrame() != null)
					mediator = getActiveFrame().getMediator();
				if(lFavorites.isSelectionEmpty() == false)
				{
					lCurrent.clearSelection();
					if(getActiveFrame() != null)
						mediator.setPiece(getSelectedPiece().getName(), getSelectedPiece().getCategory(), getSelectedPiece().getSubCategory());
				}
				P.setSelected(true);
				if(getActiveFrame() != null)
					mediator.enterState(StateConstant.PIECESTATE);
				if(mediator != null)
					mediator.setPreview();
			}
			
		});
		
		preview = new JPanel();
		preview.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 4, true)); 
		preview.setBackground(Color.BLACK);
		preview.setSize(2*WIDTH/10,2*HEIGHT/10);
		previewLabel = new JLabel("preview");
		previewLabel.setForeground(Color.WHITE);
		preview.add(previewLabel);
		
		JScrollPane sp1 = new JScrollPane(lCurrent);
		sp1.setSize(2*WIDTH/10, 3*HEIGHT/10);
		sp1.setPreferredSize(new Dimension(2*WIDTH/10, 3*HEIGHT/10));
		current.add(sp1);
		
		JScrollPane sp2 = new JScrollPane(lFavorites);
		sp2.setSize(2*WIDTH/10, 3*HEIGHT/10);
		sp2.setPreferredSize(new Dimension(2*WIDTH/10, 3*HEIGHT/10));
		favorites.add(sp2);
		
		leftPanel.setLayout(new GridLayout(0,1));
		leftPanel.add(current);// BorderLayout.NORTH);
		leftPanel.add(favorites);// BorderLayout.CENTER);
		
		buttonPanel = new JPanel(new BorderLayout());
		JButton remove = new JButton("Remove");
		remove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    remove.setMaximumSize(new Dimension(2*WIDTH/10, HEIGHT/20));
	    remove.setMinimumSize(new Dimension(2*WIDTH/10, HEIGHT/20));
		remove.setSize(2*WIDTH/10, HEIGHT/20);
		remove.setPreferredSize(new Dimension(2*WIDTH/10, HEIGHT/20));
		//remove.validate();
		buttonPanel.add(remove,BorderLayout.NORTH);

	
		
		remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(lCurrent.getSelectedIndex()!= - 1)
				{
					currentVector.remove(lCurrent.getSelectedIndex());
//					// currentVector.remove(((DefaultListModel)lCurrent.getModel()).getElementAt(lCurrent.getSelectedIndex()));
//					for(int i=0; i<currentVector.size(); i++)
//					{
//						if(currentVector.get(i).getName().equals((((DefaultListModel)lCurrent.getModel()).getElementAt(lCurrent.getSelectedIndex()))))
//							currentVector.remove(i);
//							break;
//					}
					((DefaultListModel)lCurrent.getModel()).removeElementAt(lCurrent.getSelectedIndex());				
				}
				if(lFavorites.getSelectedIndex()!= - 1)
				{
					favoritesVector.remove(lFavorites.getSelectedIndex());
//					// favoritesVector.remove(((DefaultListModel)lFavorites.getModel()).getElementAt(lFavorites.getSelectedIndex()));
//					for(int i=0; i<favoritesVector.size(); i++)
//					{
//						if(favoritesVector.get(i).getName().equals(((DefaultListModel)lFavorites.getModel()).getElementAt(lFavorites.getSelectedIndex())))
//							favoritesVector.remove(i);
//							break;
//					}
//					System.out.println("am sters " + ((DefaultListModel)lFavorites.getModel()).get(lFavorites.getSelectedIndex()));
					((DefaultListModel)lFavorites.getModel()).removeElementAt(lFavorites.getSelectedIndex());	
				}
			}
			
		});
		buttonPanel.add(preview,BorderLayout.CENTER);
		//leftPanel.add(preview);//, BorderLayout.SOUTH);
		leftPanel.add(buttonPanel);
		leftPanel.repaint();
		
	}

	private void rightPanel()
	{
		JToolBar rightToolBar = new JToolBar(1);
		final ButtonGroup buttonGroup = new ButtonGroup();
		
		int s = 20;
        N.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        N.setMaximumSize(new Dimension(WIDTH/10, s));
        N.setMinimumSize(new Dimension(WIDTH/10, s));
		N.setSize(WIDTH/10, s);
		N.setPreferredSize(new Dimension(WIDTH/10, s));
		N.setBorder(BorderFactory.createRaisedBevelBorder());
		N.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getActiveFrame() != null)
				{
					mediator = getActiveFrame().getMediator();
					mediator.enterState(StateConstant.WIRESTATE);
				}
				
			}
			
		});
		
		

		M.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    M.setMaximumSize(new Dimension(WIDTH/10, s));
	    M.setMinimumSize(new Dimension(WIDTH/10, s));
		M.setSize(WIDTH/10, s);
		M.setPreferredSize(new Dimension(WIDTH/10, s));
		M.setBorder(BorderFactory.createRaisedBevelBorder());
		M.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getActiveFrame() != null)
				{
					mediator = getActiveFrame().getMediator();
					mediator.enterState(StateConstant.MOUSESTATE);
				}
				
			}
			
		});
		

		P.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    P.setMaximumSize(new Dimension(WIDTH/10, s));
	    P.setMinimumSize(new Dimension(WIDTH/10, s));
		P.setSize(WIDTH/10, s);
		P.setPreferredSize(new Dimension(WIDTH/10, s));
		P.setBorder(BorderFactory.createRaisedBevelBorder());
		P.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(getActiveFrame() != null)
				{
					mediator = getActiveFrame().getMediator();
					mediator.enterState(StateConstant.PIECESTATE);
				}
			}
			
		});
		
		L.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    L.setMaximumSize(new Dimension(WIDTH/10, s));
	    L.setMinimumSize(new Dimension(WIDTH/10, s));   
		L.setSize(WIDTH/10, s);
		L.setPreferredSize(new Dimension(WIDTH/10, s));
		L.setBorder(BorderFactory.createRaisedBevelBorder());
		L.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LibrarySelection l = new LibrarySelection(mainFrame, lCurrent, lFavorites, currentVector, favoritesVector);
				if(l.last.isEmpty() == false)
				{
					if(l.last.get(0) == 0)
					{
						lCurrent.setSelectedIndex(((DefaultListModel)lCurrent.getModel()).getSize() - 1);
						lFavorites.clearSelection();
					}
					if(l.last.get(0) == 1)
					{
						lFavorites.setSelectedIndex(((DefaultListModel)lFavorites.getModel()).getSize() - 1);
						lCurrent.clearSelection();
					}
				}
				buttonGroup.setSelected((JToggleButton.ToggleButtonModel) P.getModel(),true);
			}
		});
		
		D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    D.setMaximumSize(new Dimension(WIDTH/10, s));
	    D.setMinimumSize(new Dimension(WIDTH/10, s));
		D.setSize(WIDTH/10, s);
		D.setPreferredSize(new Dimension(WIDTH/10, s));
		D.setBorder(BorderFactory.createRaisedBevelBorder());
		D.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(getActiveFrame() != null)
				{
					mediator = getActiveFrame().getMediator();
					mediator.enterState(StateConstant.DELETESTATE);			
				}
			}
			
		});
		
		G.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    G.setMaximumSize(new Dimension(WIDTH/10, s));
	    G.setMinimumSize(new Dimension(WIDTH/10, s));
		G.setSize(WIDTH/10, s);
		G.setPreferredSize(new Dimension(WIDTH/10, s));
		G.setBorder(BorderFactory.createRaisedBevelBorder());
		G.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(getActiveFrame() != null)
				{
					mediator = getActiveFrame().getMediator();
					mediator.enterState(StateConstant.DRAGSTATE);	
				}
			}
			
		});
		
		J.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	    J.setMaximumSize(new Dimension(WIDTH/10, s));
	    J.setMinimumSize(new Dimension(WIDTH/10, s));
		J.setSize(WIDTH/10, s);
		J.setPreferredSize(new Dimension(WIDTH/10, s));
		J.setBorder(BorderFactory.createRaisedBevelBorder());
		J.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				mediator = getActiveFrame().getMediator();
//				mediator.enterState(StateConstant.JUNCTIONSTATE);			
			}
			
		});
		
		D.setToolTipText("Delete");
		L.setToolTipText("Library");
		P.setToolTipText("Place");
		G.setToolTipText("Drag item");
		M.setToolTipText("Mouse");
		N.setToolTipText("Wire");
		J.setToolTipText("Junction");
		
		buttonGroup.add(L);
		buttonGroup.add(D);
		buttonGroup.add(P);
		buttonGroup.add(G);
		buttonGroup.add(M);
		buttonGroup.add(N);
		buttonGroup.add(J);
		
		rightToolBar.add(L);
		rightToolBar.add(D);
		rightToolBar.add(P);
		rightToolBar.add(G);
		rightToolBar.add(M);
		rightToolBar.add(N);
		rightToolBar.add(J);
		
		//rightToolBar.add(buttonGroup);
		rightPanel.add(rightToolBar);
		rightPanel.setSize(WIDTH/10, 9*HEIGHT/10);
		rightPanel.setPreferredSize(new Dimension(WIDTH/10, 9*HEIGHT/10));
}
		
	
	// add button to data structure v
	public void add(JComponent b)
	{
		v.add(b);
		i=i+1;
	}
	
	private void writeFavorites()
	{
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter("favorites.xml"));
			f.write("<?xml version=\"1.0\" ?>\n");
			f.write("<favorites>\n");
			for(int i=0; i<favoritesVector.size(); i++)
			{
				// System.out.println("Scriem " + favoritesVector.get(i).getName());
				f.write("\t<piece>\n");
				f.write("\t\t<category>" + favoritesVector.get(i).getCategory() + "</category>\n");
				f.write("\t\t<subcategory>" + favoritesVector.get(i).getSubCategory() + "</subcategory>\n");
				f.write("\t\t<name>" + favoritesVector.get(i).getName() + "</name>\n");
				f.write("\t</piece>\n");
			}
			f.write("</favorites>\n");
			f.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
	}

	@Override
	public void editPieceProperties(edss.interf.Piece piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getCenterPanel() {
		for(int i=0; i<centerPanel.getComponents().length; i++)
		{
			System.out.println("xx");
			NewInternalFrame nj = (NewInternalFrame)centerPanel.getComponents()[i];
//			if(nj.is == true)
				return nj.getPanel();		
		}
		return null;
	}

	@Override
	public JPanel getLeftPreview() {
		return preview;
	}
	
	@Override
	public boolean hasFrames() {
		if(centerPanel.getComponentCount() == 0)
			return false;
		return true;
	}
	
	public Piece getSelectedPiece()
	{
		if(lCurrent.getSelectedValue() != null)
			return	currentVector.get(lCurrent.getSelectedIndex());
		if(lFavorites.getSelectedValue() != null)
			return favoritesVector.get(lFavorites.getSelectedIndex());
		return null;
	}
	
	public NewInternalFrame getActiveFrame()
	{
		NewInternalFrame r = null;
		for(int i=0; i<w.size(); i++)
		{
			if(w.get(i).isSelected() == true)
			{
				r = w.get(i);
				return r;
			}
		}
		return r;
	}
	public void register()
	{
		mediator.registerGui(this);
	}
	
//	public Piece getPiece()
//	{
//		Piece p = null;
//		if(lCurrent.getSelectedValue() != null)
//		{
//			p = (Piece) ((DefaultListModel) lCurrent.getModel()).elementAt(lCurrent.getSelectedIndex());
//			return p;
//		}
//		if(lFavorites.getSelectedValue() != null)
//		{
//			p = (Piece) ((DefaultListModel) lFavorites.getModel()).elementAt(lFavorites.getSelectedIndex());
//			return p;
//		}
//		return p;
//	}
	
}



