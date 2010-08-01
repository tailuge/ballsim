package org.motion.ballsimapp.client;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class CanvasTable {

	private GWTCanvas canvas;
	
	int width = 20;
	public CanvasTable(GWTCanvas canvas_)
	{
		canvas = canvas_;
		
	}
	
	public void renderLoop()
	{
		if (width>500)
			return;
		canvas.clear();
		canvas.setLineWidth(1);
	    canvas.setStrokeStyle(new Color(width,6,7));
	    
	    canvas.beginPath();
	      canvas.moveTo(1,1);
	      canvas.lineTo(1,50);
	      canvas.lineTo(50,width++);
	      canvas.lineTo(50, 1);
	      canvas.closePath();
	      
	      canvas.setFillStyle(Color.RED);
	      canvas.arc(width, width, 10, 0, 7, false);
	      
	    canvas.stroke();
	}
}
