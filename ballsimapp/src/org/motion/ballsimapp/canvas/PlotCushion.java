package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Cushion;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;




public class PlotCushion 
{
	
	public static void plot(PlotScale scale, GWTCanvas canvas)
 	{
		canvas.setStrokeStyle(Color.GREEN);

		int xp = scale.scaledX(Cushion.xp)+scale.r;
		int xn = scale.scaledX(Cushion.xn)-scale.r;
		int yp = scale.scaledY(Cushion.yp)+scale.r;
		int yn = scale.scaledY(Cushion.yn)-scale.r;
		
		canvas.moveTo(xn, yn);
		canvas.lineTo(xp, yn);
		canvas.lineTo(xp, yp);
		canvas.lineTo(xn, yp);
		canvas.lineTo(xn, yn);
				
 	}

}
