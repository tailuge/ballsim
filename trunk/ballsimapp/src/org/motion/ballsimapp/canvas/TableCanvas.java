package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class TableCanvas {

	// handle aiming events
	
	// plot a table
	

	private Canvas canvas;
	private Canvas backBuffer;

	private Context2d context;
	private Context2d backBufferContext;

	
	//private final CssColor whiteColor = CssColor.make("rgba(170,200,200,0.8)");
	private final CssColor redrawColor = CssColor.make("rgba(95,95,205,0.2)");

    private final int width,height;
    //private int mouseX,mouseY;
	//private boolean active = false;
	
	
	public TableCanvas(int w, int h)
	{
		width = w;
		height = h;
	}
	
	public Canvas getInitialisedCanvas()
	{
		canvas = Canvas.createIfSupported();	
		backBuffer = Canvas.createIfSupported();
		
	    canvas.setWidth(width + "px");
	    canvas.setHeight(height + "px");
	    canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);

	    backBuffer.setWidth(width + "px");
	    backBuffer.setHeight(height + "px");
	    backBuffer.setCoordinateSpaceWidth(width);
	    backBuffer.setCoordinateSpaceHeight(height);

	    
	    context = canvas.getContext2d();
	    backBufferContext = backBuffer.getContext2d();
	    
	    initHandlers();
	    updateAim();
		return canvas;
	}

	public void updateAim()
	{
		 doUpdate() ;
	}

	public void initHandlers()
	{
		
	}
	
	public void plotAtTime(Table table,double t)
	{
		clearBackBuffer(); 

		PlotCushion.plot(backBufferContext);

		for(Ball ball : table.balls())
		{
			Event e = Interpolator.interpolate(ball, t);
			PlotEvent.plotEvent(e, backBufferContext);
		}
		
		moveBackBufferToFront(backBufferContext,context);

	}

	void clearBackBuffer() 
	{
		backBufferContext.setFillStyle(redrawColor);
		backBufferContext.fillRect(0, 0, width, height);
		PlotCushion.plot(backBufferContext);	
	}
	
	void doUpdate() 
	{
		clearBackBuffer(); 

		moveBackBufferToFront(backBufferContext,context);
 	}
	  
	public void moveBackBufferToFront(Context2d back, Context2d front) 
	{
		front.drawImage(back.getCanvas(), 0, 0);
	}
	
	
}
