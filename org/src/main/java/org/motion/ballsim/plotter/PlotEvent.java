package org.motion.ballsim.plotter;

import java.awt.Color;
import java.util.List;

import org.motion.ballsim.Ball;
import org.motion.ballsim.BallSpot;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.EventType;
import org.motion.ballsim.motion.State;

public class PlotEvent 
{

	private static Color[] colours = new Color[] {Color.white,Color.red,Color.yellow};
	
	private static Color getColour(int id)
	{
		return colours[id-1];
	}
	
	public static void plotEvent(Ball b, Event e, PlotScale scale, boolean alwaysShow, boolean showCushionEvent)
 	{
        int x = scale.scaledX(e.pos.getX());
        int y = scale.scaledY(e.pos.getY());

        if (!showCushionEvent && e.type == EventType.Cushion)
        	return;
        
        if (alwaysShow || e.type != EventType.Interpolated && e.type != EventType.RollEquilibrium)
        {
        	scale.g2d.setColor(getColour(b.id));
        	scale.g2d.fillOval(x-scale.r, y-scale.r, 2*scale.r, 2*scale.r); 	
        }
        
        if (e.state == State.Sliding)
        	scale.g2d.setColor(Color.blue);

        if (e.state == State.Rolling)
        	scale.g2d.setColor(Color.red);

        if (e.state == State.Stationary)
        	scale.g2d.setColor(Color.black);

        scale.g2d.setStroke(PlotScale.normal);
        if (alwaysShow || e.type != EventType.Interpolated && e.type != EventType.RollEquilibrium)
        {
        	scale.g2d.drawOval(x-scale.r, y-scale.r, 2*scale.r, 2*scale.r); 	
        	//scale.g2d.drawChars(e.state.toString().toCharArray(), 0, e.state.toString().length(), x+scale.r, y);
        }
 //       int xvel = scale.scaledX(e.pos.getX() + e.vel.getX()/PlotScale.velscale);
 //       int yvel = scale.scaledY(e.pos.getY() + e.vel.getY()/PlotScale.velscale);

 //       int xavel = scale.scaledX(e.pos.getX() + e.angularVel.getX()/PlotScale.angscale);
 //       int yavel = scale.scaledY(e.pos.getY() + e.angularVel.getY()/PlotScale.angscale);

        scale.g2d.setStroke(PlotScale.normal);
        //scale.g2d.drawLine(x, y, xvel, yvel);

        // spot on ball
        
        List<Vector3D> spots = BallSpot.getVisibleSpots(e);
        scale.g2d.setColor(Color.black);
        for(Vector3D spot : spots)
        {
            int spotx=scale.scaledX(spot.getX());
            int spoty=scale.scaledY(spot.getY());
            scale.g2d.drawLine(spotx, spoty, spotx, spoty);
        }

        //scale.g2d.setStroke(PlotScale.thindashed);
        //scale.g2d.drawLine(x, y, xavel, yavel);
        
        
 	}
 	
}
