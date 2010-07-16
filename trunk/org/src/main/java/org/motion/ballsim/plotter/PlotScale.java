package org.motion.ballsim.plotter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Collection;

import org.motion.ballsim.Ball;
import org.motion.ballsim.Cushion;
import org.motion.ballsim.Event;

public class PlotScale {

 	int w,h,r;
 	double scale;
 	double minx,maxx,miny,maxy;
 	Graphics2D g2d;
 	
 	public final static double velscale = 50;
 	public final static double angscale = 1000;

 	public final static Stroke thindashed = new BasicStroke(1.0f,
		      BasicStroke.CAP_BUTT,
		      BasicStroke.JOIN_BEVEL, 1.0f,
		      new float[] { 8.0f, 3.0f, 2.0f, 3.0f },
		      0.0f); 

	public final static Stroke normal = new BasicStroke(1.0f);

 	
 	public PlotScale(Collection<Event> events)
 	{
 		extractMinMax(events);
 	}
 	
 	private void extractMinMax(Collection<Event> events)
 	{
 		minx = 0;
 		maxx = 0;
		miny = 0;
		maxy = 0;
 		
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
 	}
 	
 	private void rescale()
 	{ 		
 		double scalex = w/(maxx-minx) ;
 		double scaley = h/(maxy-miny) ;
 		
 		System.out.println(scalex + "," + scaley);
 		
 		scale = scalex < scaley ? scalex : scaley;
 		scale *= 1.0;
 		if (scale==0)
 			scale = 0.05;
 		
        r = (int) (Ball.R*scale);
/*
        System.out.println("minx:"+minx+" maxx:"+maxx);
        System.out.println("miny:"+miny+" maxy:"+maxy);
        System.out.println("scalex:"+scaley);
        System.out.println("scaley:"+scalex);
        System.out.println("scale:"+scale);
*/        
 	}

	public void setWindowInfo(Graphics2D g2d_, int w_, int h_) 
	{
		g2d = g2d_;
		w = w_;
		h = h_;
		scaleToTable();
		rescale();
	}

	public void scaleToTable()
	{
		minx = Cushion.xn-Ball.R*2;
		maxx = Cushion.xp+Ball.R*2;
		miny = Cushion.yn-Ball.R*2;
		maxy = Cushion.yp+Ball.R*2;
	}
	public int scaledX(double x)
 	{
 		return (int)((x-minx)*scale) ;
 	}
 	public int scaledY(double y)
 	{
 		return (int)((y-miny)*scale) ;
 	}
}
