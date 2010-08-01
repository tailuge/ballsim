package org.motion.ballsimapp.canvas;

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

	    canvas.stroke();
	    width++;
	}
}
