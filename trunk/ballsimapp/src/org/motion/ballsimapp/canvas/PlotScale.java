package org.motion.ballsimapp.canvas;

import java.util.Collection;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.Event;



public class PlotScale {

 	int w,h,r;
 	double scale;
 	double minx,maxx,miny,maxy;
 	double maxt;
 	
 	public final static double velscale = 150;
 	public final static double angscale = 9200;



	public PlotScale()
 	{
 		scaleToTable();
 	}
 	
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
		maxt = 0;
		
 		for(Event e : events)
 		{
 			if (minx > e.pos.getX())
 				minx = e.pos.getX();

 			if (maxx < e.pos.getX())
 				maxx = e.pos.getX();

 			if (miny > e.pos.getY())
 				miny = e.pos.getY();

 			if (maxy < e.pos.getY())
 				maxy = e.pos.getY();
 			
 			if (e.t > maxt)
 				maxt = e.t;
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
 		 		
 		scale = scalex < scaley ? scalex : scaley;
 		scale *= 1.0;
 		if (scale==0)
 			scale = 0.05;
 		
        r = (int) (Ball.R*scale);
 	}

	public void setWindowInfo( int w_, int h_) 
	{
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
