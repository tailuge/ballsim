package org.motion.ballsim.plotter;

import java.awt.Color;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Cushion;

public class PlotAim {

	public static void plot(Vector3D ballPos, Vector3D aimPoint, PlotScale scale)
	{
        int x = scale.scaledX(aimPoint.getX());
        int y = scale.scaledY(aimPoint.getY());

        scale.g2d.setColor(Color.black);
        scale.g2d.setStroke(PlotScale.normal);
       	scale.g2d.drawOval(x-scale.r, y-scale.r, 2*scale.r, 2*scale.r); 	

        scale.g2d.setStroke(PlotScale.normal);

        int xvel = scale.scaledX(ballPos.getX());
        int yvel = scale.scaledY(ballPos.getY());

        scale.g2d.drawLine(x, y, xvel, yvel);


        //scale.g2d.setStroke(PlotScale.thindashed);
        //scale.g2d.drawLine(x, y, xavel, yavel);
        
   
	}
	
	public static void power(double power, PlotScale scale)
	{
		scale.g2d.setColor(Color.green);

		int xp = scale.scaledX(Cushion.xp)+scale.r;
		
		int yp = scale.scaledY(Cushion.yp)+scale.r;
		int yn = scale.scaledY(Cushion.yn)-scale.r;
		
		scale.g2d.drawLine(xp+5, yp-(int)((yp-yn)*power/300.0), xp+5, yp);

	}
}
