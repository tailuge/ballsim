package org.motion.ballsim.plotter;

import java.awt.Color;

import org.motion.ballsim.motion.Cushion;

public class PlotCushion 
{
	
	public static void plot(PlotScale scale)
 	{
		scale.g2d.setColor(Color.gray);

		int xp = scale.scaledX(Cushion.xp)+scale.r;
		int xn = scale.scaledX(Cushion.xn)-scale.r;
		int yp = scale.scaledY(Cushion.yp)+scale.r;
		int yn = scale.scaledY(Cushion.yn)-scale.r;
		
		scale.g2d.drawLine(xn, yn, xp, yn);
		scale.g2d.drawLine(xn, yp, xp, yp);

		scale.g2d.drawLine(xn, yn, xn, yp);
		scale.g2d.drawLine(xp, yn, xp, yp);
		
//		scale.g2d.fillRect(xp+scale.r, yn, scale.r, (yp-yn)/1);
//		scale.g2d.fillRect(xn-scale.r, yn, scale.r, (yp-yn)/1);
		
//		scale.g2d.fillRect(xn, yp+scale.r, (xp-xn)/1, scale.r);
//		scale.g2d.fillRect(xn, yn-scale.r, (xp-xn)/1, scale.r);
		
 	}

}
