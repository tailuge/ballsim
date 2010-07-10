package org.motion.ballsim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.Cushion;
import org.motion.ballsim.Event;
import org.motion.ballsim.Interpolator;
import org.motion.ballsim.State;



/**
 * @author 
 *
 * Don't want any graphics in this project, but sometimes needed to
 * understand and debug problems.
 * 
 */
public class Plotter extends JPanel {

 	private static final long serialVersionUID = -5923733218322952123L;

 	int w,h,r;
 	double scale;
 	double minx,maxx,miny,maxy;
 	
 	final static double velscale = 50;
 	final static double angscale = 50;

 	List<Event> events = new ArrayList<Event>();
 	Graphics2D g2d;

 	final static Stroke thindashed = new BasicStroke(1.0f,
 		      BasicStroke.CAP_BUTT,
 		      BasicStroke.JOIN_BEVEL, 1.0f,
 		      new float[] { 8.0f, 3.0f, 2.0f, 3.0f },
 		      0.0f); 

 	final static Stroke normal = new BasicStroke(1.0f);

 	
 	public void generateTestCollision()
 	{
 		/*
 		Event e1 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(80));
 		Event e2 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(0));
 		e1.pos = new Vector3D(0,Ball.R*1.0,0);
 		e2.pos = Vector3D.PLUS_I.scalarMultiply(Ball.R*7);
 		*/
 		
		Event e1 = Utilities.getRolling(new Vector3D(-13.8, -15.48, -81.34));		
		Event e2 = Utilities.getStationary();
		e2.pos = new Vector3D(-5.92, -6.21, -33.19);		

 		
 		double t = Collision.collisionTime(e1, e2);
 		EventPair res = Collision.collisionEvents(e1, e2, t);

		List<Event> init  = new ArrayList<Event>();
		init.add(e1);
		init.add(res.getFirst());
//		init.add(res.getFirst().rollingEventFromSliding());		
//		init.add(res.getFirst().rollingEventFromSliding().stationaryEventFromRolling());		
//		Interpolator i = new Interpolator(init, 10);
		//events.addAll(i.getInterpolated());
		events.addAll(init);
		init.clear();
		init.add(e2);
		init.add(res.getSecond());
//		init.add(res.getSecond().rollingEventFromSliding());
//		init.add(res.getSecond().rollingEventFromSliding().stationaryEventFromRolling());
//		i = new Interpolator(init, 10);
		//events.addAll(i.getInterpolated());
		events.addAll(init);
		System.out.println("sep:"+Collision.seperationAt(res.getFirst(), res.getSecond(), t));
		System.out.println("1st:"+res.getFirst());
		System.out.println("2nd:"+res.getSecond());
		

 	}
 	
 	public void generateTestEventsCushion()
 	{
 		Event e = Utilities.getRolling(new Vector3D(20,20,0));
 		Event nr = e.next();
 		Event c1 = Cushion.xCollisionsWith(e, nr.pos.getX()/2.0, nr.t);
 		Event nr2 = c1.next();
 		Event s = nr2.next();
 		
 		System.out.println("c1:"+c1);
		List<Event> init  = new ArrayList<Event>();
		init.add(e);
		init.add(c1);
		init.add(nr2);
		init.add(s);
		Interpolator i = new Interpolator(init, 21);
		events.addAll(i.getInterpolated());
 	}
 	
 	public void generateTestEventsSlide()
 	{
		Event slide = Utilities.getSliding(new Vector3D(20,-40,0),Vector3D.PLUS_I.scalarMultiply(190));
		Event roll = slide.next();
		Event stationary = roll.next();
		
		List<Event> init  = new ArrayList<Event>();
		init.add(slide);
		init.add(roll);
		init.add(stationary);		
		Interpolator i = new Interpolator(init, 10);
		events.addAll(i.getInterpolated());
 	}

 	public void generateTestEventsRoll()
 	{
		Event roll = Utilities.getRolling(new Vector3D(30,10,0));
		Event stationary = roll.next();
		for(double dt = 0; dt < stationary.t*0.9; dt += stationary.t/5.0)
		{
			events.add(roll.advanceDelta(dt));
		}
		events.add(stationary);		
 	}

 	public void generateTestEvents()
 	{
 		events.clear();
// 		generateTestEventsRoll();
// 		generateTestEventsSlide();
// 		generateTestEventsCushion();
 		generateTestCollision();
 	}
 	
 	public void plotEventList(List<Event> events)
 	{
 		for(Event e : events)
 		{
 			plotEvent(e);
 		}
 	}

 	public int scaledX(double x)
 	{
 		return (int)((x-minx)*scale) ;
 	}
 	public int scaledY(double y)
 	{
 		return (int)((y-miny)*scale) ;
 	}
 	
 	public void plotEvent(Event e)
 	{
        int x = scaledX(e.pos.getX());
        int y = scaledY(e.pos.getY());
        
        
        if (e.state == State.Sliding)
            g2d.setColor(Color.blue);

        if (e.state == State.Rolling)
        	g2d.setColor(Color.red);

        if (e.state == State.Stationary)
        	g2d.setColor(Color.black);

        g2d.setStroke(normal);
        //if (e.type != EventType.Interpolated)
        {
        	g2d.drawOval(x-r, y-r, 2*r, 2*r); 	
        	g2d.drawChars(e.state.toString().toCharArray(), 0, e.state.toString().length(), x+r, y);
        }
        int xvel = scaledX(e.pos.getX() + e.vel.getX()/velscale);
        int yvel = scaledY(e.pos.getY() + e.vel.getY()/velscale);

        int xavel = scaledX(e.pos.getX() + e.angularVel.getX()/angscale);
        int yavel = scaledY(e.pos.getY() + e.angularVel.getY()/angscale);

        g2d.setStroke(normal);
        g2d.drawLine(x, y, xvel, yvel);

        g2d.setStroke(thindashed);
        g2d.drawLine(x, y, xavel, yavel);
 	}
 	
 	public void scaleToFit()
 	{
 		minx = events.get(0).pos.getX();
 		maxx = events.get(0).pos.getX();
		miny = events.get(0).pos.getY();
		maxy = events.get(0).pos.getY();
 		
 		for(Event e : events)
 		{
 			System.out.println(e);
 			if (minx > e.pos.getX())
 				minx = e.pos.getX();

 			if (maxx < e.pos.getX())
 				maxx = e.pos.getX();

 			if (miny > e.pos.getY())
 				miny = e.pos.getY();

 			if (maxy < e.pos.getY())
 				maxy = e.pos.getY();
 		} 		
 		
 		maxx += Ball.R;
 		minx -= Ball.R;
 		maxy += Ball.R;
 		miny -= Ball.R;
 		
 		double scalex = w/(maxx-minx) ;
 		double scaley = h/(maxy-miny) ;
 		
 		System.out.println(scalex + "," + scaley);
 		
 		//scale = (scalex+scaley)*1.3; 	
 		scale = scalex < scaley ? scalex : scaley;
 		scale *= 1.0;
 		if (scale==0)
 			scale = 0.05;
 		
        r = (int) (Ball.R*scale);

        System.out.println("minx:"+minx+" maxx:"+maxx);
        System.out.println("miny:"+miny+" maxy:"+maxy);
        System.out.println("scalex:"+scaley);
        System.out.println("scaley:"+scalex);
        System.out.println("scale:"+scale);
        
 	}
 	
	public void paintComponent(Graphics g) 
	{
            super.paintComponent(g); 

            g2d = (Graphics2D) g;

            g2d.setColor(Color.blue);
            Dimension size = getSize();
            Insets insets = getInsets();

            w =  size.width - insets.left - insets.right;
            h =  size.height - insets.top - insets.bottom;
 
            generateTestEvents();
            scaleToFit();
            plotEventList(events);
    		
    }

    public static void main(String[] args) 
    {
        Plotter points = new Plotter();
        JFrame frame = new JFrame("Test plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}