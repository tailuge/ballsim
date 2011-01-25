package org.motion.ballsim.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Cushion;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.search.Table;
import org.motion.ballsim.util.UtilEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MousePlot extends JPanel implements ActionListener, MouseListener
{	 
	private final static Logger logger = LoggerFactory.getLogger(AnimatedPlot.class);

	private static final long serialVersionUID = 1224879637869008694L;

	private PlotScale scale;
	private boolean scaleSet = false;
	private Timer timer;
	private Table table;
	
	public MousePlot(Table table_, int interpolatedCount)
	{
		scale = new PlotScale(table_.getAllEvents());
		table = table_;
		timer = new Timer(1, this);
		addMouseListener(this);
	}

	public MousePlot(Table table_)
	{
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
			PlotEvent.plotEvent(b,Interpolator.interpolate(b, t),scale,true,true);
		}
		
	}

	double t = 0;
	
	public void actionPerformed(ActionEvent arg0) 
	{
		logger.info("t:{}",t);
		t=t+0.0001;
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
		
		table.resetToCurrent(t);
	//	Event current = Interpolator.interpolate(table.ball(1), t);
		Event current = table.ball(1).lastEvent();

		double ballx = current.pos.getX();
		double bally = current.pos.getY();
		
		double tablex = 2*Cushion.xp*((double)(e.getX() - scale.w/2 )/(double)scale.w);
		double tabley = 2*Cushion.yp*((double)(e.getY() - scale.h/2 )/(double)scale.h);

		System.out.println("tablex:"+tablex);
		System.out.println("tabley:"+tabley);

		System.out.println("ballx:"+ballx);
		System.out.println("bally:"+bally);

		Vector3D dir = new Vector3D(
				 tablex-ballx,
				 tabley-bally,0);
		
		
		table.ball(1).setFirstEvent(UtilEvent.hit(current.pos, dir.normalize(), 180.0, 0.0));
		table.generateSequence();
		t=0;
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
