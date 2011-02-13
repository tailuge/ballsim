package demo.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import demo.plotter.Interpolator;
import demo.plotter.PlotCushion;
import demo.plotter.PlotEvent;
import demo.plotter.PlotScale;
import org.motion.ballsim.search.Table;

public class StaticPlot  extends JPanel 
{	 
	private static final long serialVersionUID = 1224879637869008694L;

	private Collection<Event> events = new ArrayList<Event>();
	private PlotScale scale;
	
	public StaticPlot(Collection<Table> tables_, int interpolatedCount)
	{

		scale = new PlotScale();
		for(Table table : tables_)
		{
			double t = 0;
			double maxt = table.getMaxTime();
			double delta = maxt / (double)interpolatedCount;		

			events.addAll(table.getAllEvents());
			
			while(t<=maxt+0.1)
			{
				for(Ball b: table.balls())
				{
					events.add(new Event(Interpolator.interpolate(b, t)));
				}
				t += delta;
			}
			
		}
	}
	
	public StaticPlot(Table table_, int interpolatedCount)
	{
		scale = new PlotScale(table_.getAllEvents());
		double delta = scale.maxt / (double)interpolatedCount;		
		events.addAll(table_.getAllEvents());
		double t = 0;
		while(t<=scale.maxt+0.1)
		{
			for(Ball b: table_.balls())
			{
				events.add(new Event(Interpolator.interpolate(b, t)));
			}
			t += delta;
		}
		
	}

	public StaticPlot(Table table_)
	{
		events = table_.getAllEvents();
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
 		for(Event e : events)
 		{
 			PlotEvent.plotEvent(e, scale,false,true);
 		}
	}
}
