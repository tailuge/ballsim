package org.motion.ballsim.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.search.Table;
import org.motion.ballsim.util.Logger;

public class AnimatedPlot  extends JPanel implements ActionListener
{	 
	private final static Logger logger = new Logger("AnimatedPlot",false);

	private static final long serialVersionUID = 1224879637869008694L;

//	private Collection<BallEvent> events = new ArrayList<BallEvent>();
	private PlotScale scale;
	private boolean scaleSet = false;
	private Timer timer;
	private Table table;
	
	public AnimatedPlot(Table table_, int interpolatedCount)
	{
		scale = new PlotScale(table_.getAllEvents());
		//delta = scale.maxt / (double)interpolatedCount;		
		//events.addAll(table_.getAllBallEvents());
		table = table_;
		timer = new Timer(1, this);
	}

	public AnimatedPlot(Table table_)
	{
//		events = table_.getAllBallEvents();
		scale = new PlotScale(table_.getAllEvents());
	}

	public void draw() 
    {
		JFrame frame = new JFrame("Table plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setSize(400, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		timer.start();
	 }
	
	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);             
        Graphics2D g2d = (Graphics2D) g;
        scale.g2d = g2d;
        if (!scaleSet)
        {
            
            Dimension size = getSize();
            Insets insets = getInsets();
        	logger.info("w:{}",size.width);
        	scale.setWindowInfo(g2d,size.width - insets.left - insets.right,size.height - insets.top - insets.bottom);           
        	scaleSet = true;
        }
        PlotCushion.plot(scale);
        plotTable();    		
    }
	
	private void plotTable()
	{	
		for(Ball b: table.balls())
		{
			PlotEvent.plotEvent(Interpolator.interpolate(b, t),scale,true,true);
		}
		
	}

	double t = 0;
	
	public void actionPerformed(ActionEvent arg0) 
	{
		logger.info("t:{}",t);
		t=t+0.0001;
		repaint();
	}
}
