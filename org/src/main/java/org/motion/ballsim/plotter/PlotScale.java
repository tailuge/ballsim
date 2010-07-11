package org.motion.ballsim.plotter;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.Ball;
import org.motion.ballsim.Event;

public class PlotScale {

 	int w,h,r;
 	double scale;
 	double minx,maxx,miny,maxy;
 	
 	final static double velscale = 50;
 	final static double angscale = 50;

 	List<Event> events = new ArrayList<Event>();
 	
 	public PlotScale(List<Event> events_)
 	{
 		events.clear();
 		events.addAll(events_);
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

}
