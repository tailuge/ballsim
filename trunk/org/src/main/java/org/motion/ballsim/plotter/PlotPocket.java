package org.motion.ballsim.plotter;

import java.awt.Color;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Pocket;

public class PlotPocket {
	
	public static void plot(PlotScale scale)
 	{
		plotKnuckle(scale, Pocket.p1k1);
		plotKnuckle(scale, Pocket.p1k2);
		plotHole(scale, Pocket.p1);

		plotKnuckle(scale, Pocket.p2k1);
		plotKnuckle(scale, Pocket.p2k2);
		plotHole(scale, Pocket.p2);

		plotKnuckle(scale, Pocket.p3k1);
		plotKnuckle(scale, Pocket.p3k2);
		plotHole(scale, Pocket.p3);

		plotKnuckle(scale, Pocket.p4k1);
		plotKnuckle(scale, Pocket.p4k2);
		plotHole(scale, Pocket.p4);

 	}

	private static void plotHole(PlotScale scale, Vector3D hole) {

		int x = scale.scaledX(hole.getX());
		int y = scale.scaledY(hole.getY());
		
    	scale.g2d.setColor(Color.black);
    	scale.g2d.fillOval(x-2*scale.r, y-2*scale.r, 4*scale.r, 4*scale.r); 	
		
	}

	private static void plotKnuckle(PlotScale scale, Vector3D knuckle) 
	{
	
		int x = scale.scaledX(knuckle.getX());
		int y = scale.scaledY(knuckle.getY());
		
    	scale.g2d.setColor(Color.blue);
    	scale.g2d.drawOval(x-scale.r, y-scale.r, 2*scale.r, 2*scale.r); 	

	}

}
