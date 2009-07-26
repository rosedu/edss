package edss.gui;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class NewInternalFrame extends JInternalFrame {
	
	JSlider zoomSlider;
	private JPanel internalPanel;
	private int zoomFactor = 100;
//	private JSVGCanvas canvas;
	
	Mediator med = new Mediator(); 

	public NewInternalFrame(String s, int i)
	{
		super(s + " " + i, true, true, true, true);
		setBounds(i*10, i*10, 300, 300);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		internalPanel = new JPanel(new BorderLayout());
		zoomSlider = new JSlider(25, 400);
		zoomSlider.setValue(100);
		zoomSlider.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
				System.out.println("Zoom = " + zoomSlider.getValue());
				zoomFactor = zoomSlider.getValue();
				med.zoom(zoomFactor); // TODO
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
