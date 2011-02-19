package org.motion.ballsimapp.canvas;


import java.util.List;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.BallSpot;
import org.motion.ballsim.physics.Event;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;





public class PlotEvent 
{

	private static CssColor[] colours = new CssColor[] {CssColor.make("rgba(255,255,255,1)"),CssColor.make("rgba(255,0,0,1)"),CssColor.make("rgba(255,255,0,1)")};
	private static CssColor black = CssColor.make("rgba(5,5,5,0.7)");
	private static CssColor getColour(int id)
	{
		return colours[id-1];
	}
	
	public static void plotEvent(Event e, Context2d context)
 	{
        double x = PlotScale.screenX(e.pos.getX());
        double y = PlotScale.screenY(e.pos.getY());

        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(black);
		context.arc(x+1, y+1, PlotScale.scaled(Ball.R)+0.5, 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        

	    context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(getColour(e.ballId));
		context.arc(x, y, PlotScale.scaled(Ball.R), 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.stroke();
	    context.fill();        
		plotSpots(e,context);

 	}
	
	private static void plotSpots(Event e, Context2d context)
	{
        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.setFillStyle(black);
		List<Vector3D> spots = BallSpot.getVisibleSpots(e);
    
		for(Vector3D spot : spots)
		{
			int x=PlotScale.screenX(spot.getX());
			int y=PlotScale.screenY(spot.getY());
			context.rect(x, y, 1, 1);
		}
		
	    context.closePath();
	    context.fill();        

	}

	
}
