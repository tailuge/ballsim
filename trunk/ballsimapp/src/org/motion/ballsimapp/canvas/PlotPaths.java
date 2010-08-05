package org.motion.ballsimapp.canvas;

import org.motion.ballsimapp.logic.Ball;
import org.motion.ballsimapp.logic.Event;
import org.motion.ballsimapp.logic.Table;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class PlotPaths 
{

	public static void plot(PlotScale scale, GWTCanvas canvas, Table t)
 	{

		double maxt = t.getMaxTime();
		
		double step = maxt/300.0;
	
		canvas.setStrokeStyle(Color.GREY);
		
		for(Ball b: t.balls())
		{
			double time = 0;
			Event e1 = Interpolator.interpolate(b, time);
			canvas.beginPath();

			canvas.moveTo(scale.scaledX(e1.pos.getX()), scale.scaledY(e1.pos.getY()));
			
			while(time <= maxt-0.05)
			{
				Event e = Interpolator.interpolate(b, time);
				canvas.lineTo(scale.scaledX(e.pos.getX()), scale.scaledY(e.pos.getY()));
				time += step;
			}
			canvas.moveTo(scale.scaledX(e1.pos.getX()), scale.scaledY(e1.pos.getY()));
			
			canvas.stroke();

		}

		
 	}
	
}
