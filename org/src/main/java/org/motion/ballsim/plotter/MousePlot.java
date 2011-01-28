package org.motion.ballsim.plotter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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

public class MousePlot extends JPanel implements ActionListener, MouseListener, MouseMotionListener
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
		addMouseMotionListener(this);
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
        if (t<0.001)plotAim();
    }
	
	private void plotTable()
	{	
		for(Ball b: table.balls())
		{
			PlotEvent.plotEvent(Interpolator.interpolate(b, t),scale,true,true);
		}
		
	}

	Vector3D aimPoint = Vector3D.ZERO;
	
	private void plotAim()
	{	
		Event e = UtilEvent.hit(aimPoint, Vector3D.PLUS_I, 0, 0);
		e.ballId=1;
		PlotEvent.plotEvent(e,scale,true,true);		
	}
	
	double t = 0;
	
	public void actionPerformed(ActionEvent arg0) 
	{
		logger.info("t:{}",t);
		t=t+0.0001;
		repaint();
	}

	public Vector3D getModelCoordFromMouse(MouseEvent e)
	{
		double tablex = 2*Cushion.xp*((double)(e.getX() - scale.w/2 )/(double)scale.w);
		double tabley = 2*Cushion.yp*((double)(e.getY() - scale.h/2 )/(double)scale.h);

		return new Vector3D(tablex,tabley,0);
	}
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		table.resetToCurrent(t);
		System.out.println(e);
		timer.stop();
		t=0;
	}

	public void mouseReleased(MouseEvent e) {
		
		
		Event current = table.ball(1).lastEvent();

		Vector3D mouse = getModelCoordFromMouse(e);
		Vector3D dir = mouse.subtract(current.pos).normalize();
		
		table.ball(1).setFirstEvent(UtilEvent.hit(current.pos, dir.normalize(), 180.0, 0.0));
		table.generateSequence();
		t=0;
		timer.start();
	}

	public void mouseDragged(MouseEvent e) {

		t=0;
		aimPoint = getModelCoordFromMouse(e);
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		
		
		
	}
}
