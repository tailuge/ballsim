package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.EventType;
import org.motion.ballsim.physics.State;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;




public class PlotEvent 
{

	private static Color[] colours = new Color[] {Color.WHITE,Color.RED,Color.YELLOW};
	
	private static Color getColour(int id)
	{
		return colours[id-1];
	}
	
	public static void plotEvent(Ball b, Event e, GWTCanvas canvas, PlotScale scale, boolean alwaysShow, boolean showCushionEvent)
 	{
        int x = scale.scaledX(e.pos.getX());
        int y = scale.scaledY(e.pos.getY());

        if (!showCushionEvent && e.type == EventType.Cushion)
        	return;
    
        canvas.beginPath();
		canvas.setLineWidth(1);

        if (alwaysShow || e.type != EventType.Interpolated && e.type != EventType.RollEquilibrium)
        {
        	canvas.setStrokeStyle(getColour(b.id));
        	canvas.arc(x, y, scale.r, 0,7,false); 	
        }
        
        if (e.state == State.Sliding)
        	canvas.setStrokeStyle(Color.BLUE);

        if (e.state == State.Rolling)
        	canvas.setStrokeStyle(Color.RED);

        if (e.state == State.Stationary)
        	canvas.setStrokeStyle(Color.BLACK);

        if (alwaysShow || e.type != EventType.Interpolated && e.type != EventType.RollEquilibrium)
        {
        	canvas.arc(x, y, scale.r, 0,7,false); 	
        }
        int xvel = scale.scaledX(e.pos.getX() + e.vel.getX()/PlotScale.velscale);
        int yvel = scale.scaledY(e.pos.getY() + e.vel.getY()/PlotScale.velscale);

        int xavel = scale.scaledX(e.pos.getX() + e.angularVel.getX()/PlotScale.angscale);
        int yavel = scale.scaledY(e.pos.getY() + e.angularVel.getY()/PlotScale.angscale);

        canvas.moveTo(x,y);
        canvas.lineTo(xvel, yvel);

        canvas.moveTo(x,y);
        canvas.lineTo(xavel, yavel);
        canvas.stroke();
 	}
 	
}
