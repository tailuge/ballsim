package org.motion.ballsim.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.motion.ballsim.Ball;
import org.motion.ballsim.BallEvent;
import org.motion.ballsim.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimatedPlot  extends JPanel implements ActionListener
{	 
	private final static Logger logger = LoggerFactory.getLogger(AnimatedPlot.class);

	private static final long serialVersionUID = 1224879637869008694L;

	private Collection<BallEvent> events = new ArrayList<BallEvent>();
	private PlotScale scale;
	
	private Timer timer;
	private Table table;
	
	public AnimatedPlot(Table table_, int interpolatedCount)
	{
		scale = new PlotScale(table_.getAllEvents());
		//delta = scale.maxt / (double)interpolatedCount;		
		events.addAll(table_.getAllBallEvents());
		table = table_;
		timer = new Timer(20, this);
	}

	public AnimatedPlot(Table table_)
	{
		events = table_.getAllBallEvents();
		scale = new PlotScale(table_.getAllEvents());
	}

	public void draw() 
    {
		JFrame frame = new JFrame("Table plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setSize(412/2, 824/2);
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
		for(Ball b: table.balls)
		{
			PlotEvent.plotEvent(b,Interpolator.interpolate(b, t),scale,true);
		}
		timer.start();
	}

	double t = 0;
	
	public void actionPerformed(ActionEvent arg0) 
	{
		logger.info("t:{}",t);
		for(Ball b: table.balls)
		{
			PlotEvent.plotEvent(b,Interpolator.interpolate(b, t),scale,true);
		}
		t=t+0.001;
		repaint();
	}
}
