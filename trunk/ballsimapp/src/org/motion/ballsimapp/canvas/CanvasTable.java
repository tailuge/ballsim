package org.motion.ballsimapp.canvas;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

public class CanvasTable {

	private GWTCanvas canvas;
	
	ImageElement bg;
	
	int width = 20;
	public CanvasTable(GWTCanvas canvas_)
	{
		canvas = canvas_;
		
	    String[] urls = new String[] {Resources.INSTANCE.logo().getURL()};

	    ImageLoader.loadImages(urls, new ImageLoader.CallBack()
	    {
	          public void onImagesLoaded(ImageElement[] imageElements)
	          {
	              bg = imageElements[0];
	          }
	    });	
	}
	
	public void renderLoop(PlotScale scale)
	{
		if (width>600)
			return;
		canvas.clear();

		PlotCushion.plot(scale, canvas);
		
		if(bg!=null)
		{
//			canvas.saveContext();
//			canvas.scale(1.4, 1.4);
//			canvas.drawImage(bg, 0, 0);			
//			canvas.scale(1, 1);
//			canvas.restoreContext();
		}
		
		canvas.stroke();
	    width++;
	}
}
