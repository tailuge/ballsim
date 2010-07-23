package org.motion.ballsim.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.motion.ballsim.Ball;
import org.motion.ballsim.BallEvent;
import org.motion.ballsim.Table;

public class StaticPlot  extends JPanel 
{	 
	private static final long serialVersionUID = 1224879637869008694L;

	private Collection<BallEvent> events = new ArrayList<BallEvent>();
	private PlotScale scale;
	
	public StaticPlot(Collection<Table> tables_, int interpolatedCount)
	{

		scale = new PlotScale();
		for(Table table : tables_)
		{
			double t = 0;
			double maxt = table.getMaxTime();
			double delta = maxt / (double)interpolatedCount;		

			events.addAll(table.getAllBallEvents());
			
			while(t<=maxt+0.1)
			{
				for(Ball b: table.balls())
				{
					events.add(new BallEvent(b,Interpolator.interpolate(b, t)));
				}
				t += delta;
			}
			
		}
	}
	
	public StaticPlot(Table table_, int interpolatedCount)
	{
		scale = new PlotScale(table_.getAllEvents());
		double delta = scale.maxt / (double)interpolatedCount;		
		events.addAll(table_.getAllBallEvents());
		double t = 0;
		while(t<=scale.maxt+0.1)
		{
			for(Ball b: table_.balls())
			{
				events.add(new BallEvent(b,Interpolator.interpolate(b, t)));
			}
			t += delta;
		}
		
	}

	public StaticPlot(Table table_)
	{
		events = table_.getAllBallEvents();
		scale = new PlotScale(table_.getAllEvents());
	}

	public void draw() 
    {
		JFrame frame = new JFrame("Table plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setSize(412, 824);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
 		for(BallEvent be : events)
 		{
 			PlotEvent.plotEvent(be.ball, be.event, scale,false);
 		}
	}
}
