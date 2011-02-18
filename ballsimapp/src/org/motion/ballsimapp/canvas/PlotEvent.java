package org.motion.ballsimapp.canvas;


import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;





public class PlotEvent 
{

	private static CssColor[] colours = new CssColor[] {CssColor.make("rgba(255,255,255,1)"),CssColor.make("rgba(255,0,0,1)"),CssColor.make("rgba(255,255,0,1)")};

	private static CssColor getColour(int id)
	{
		return colours[id-1];
	}
	
	public static void plotEvent(Ball b, Event e, Context2d context)
 	{
        double x = PlotScale.screenX(e.pos.getX());
        double y = PlotScale.screenY(e.pos.getY());

        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(getColour(b.id));
		context.arc(x, y, PlotScale.scaled(Ball.R), 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        
 	}
	
}
