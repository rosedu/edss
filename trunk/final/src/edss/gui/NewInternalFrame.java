package edss.gui;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import edss.interf.GuiMediator;


public class NewInternalFrame extends JInternalFrame {
	
	JSlider zoomSlider;
	private JPanel internalPanel;
	private int zoomFactor = 100;
//	private JSVGCanvas canvas;
	
	GuiMediator mediator; // = new Mediator(); 

	public NewInternalFrame(String s, int i, final GuiMediator mediator)
	{
		super(s + " " + i, true, true, true, true);
		this.mediator =  mediator;		
		setBounds(i*10, i*10, 300, 300);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		internalPanel = new JPanel(new BorderLayout());
		zoomSlider = new JSlider(1, 5);
		zoomSlider.setValue(3);
		zoomSlider.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				
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
				int auxZoom = zoomSlider.getValue();
//					if(auxZoom < 37)
//						zoomFactor = 25;
//					if(auxZoom >= 37 && auxZoom < 76)
//						zoomFactor = 50;
//					if(auxZoom >= 76 && auxZoom < 150)
//						zoomFactor = 100;
//					if(auxZoom >= 150 && auxZoom < 300)
//						zoomFactor = 200;
//					if(auxZoom >= 300)
//						zoomFactor = 400;
				
				zoomFactor = (int) ((Math.pow(2, (auxZoom - 3)) * 100));
				System.out.println("Zoom = " + zoomFactor);
				// zoomSlider.setValue(zoomFactor);
				mediator.scale(zoomFactor); 
			}

			
		});
		setLayout(new BorderLayout());
		add(internalPanel, BorderLayout.CENTER);
		add(zoomSlider, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	public int getZoomFactor()
	{
		return zoomFactor;
	}
	public void setZoomFactor(int z)
	{
		zoomFactor = z;
	}
	
	public JPanel getPanel()
	{
		return internalPanel;
	}
	
}
