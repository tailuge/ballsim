package org.motion.ballsim.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.motion.ballsim.Event;
import org.motion.ballsim.Table;

public class StaticPlot  extends JPanel 
{	 
	private static final long serialVersionUID = 1224879637869008694L;

	private Table table;
	private PlotScale scale;
	
	public StaticPlot(Table table_)
	{
		table = table_;
		scale = new PlotScale(table.getAllEvents());
	}
	
	public void draw() 
    {
		JFrame frame = new JFrame("Table plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setSize(300, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		try 
		{
			Thread.sleep(10000);
		} 
		catch (InterruptedException e) 
		{
		}
	 }
	
	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);             
        Graphics2D g2d = (Graphics2D) g;
        Dimension size = getSize();
        Insets insets = getInsets();
        scale.setWindowInfo(g2d,size.width - insets.left - insets.right,size.height - insets.top - insets.bottom);           
        PlotCushion.plot(scale);
        plotTable();    		
    }
	
	private void plotTable()
	{
		Collection<Event> events = table.getAllEvents();
 		for(Event e : events)
 		{
 			PlotEvent.plotEvent(e, scale);
 		}
	}
}
