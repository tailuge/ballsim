package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.State;
import org.motion.ballsim.physics.Table;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class TableCanvas {

	// handle aiming events
	
	// plot a table
	

	private Canvas canvas;
	private Canvas backBuffer;

	private Context2d context;
	private Context2d backBufferContext;

	private PlotAim aim = new PlotAim();
	
	//private final CssColor whiteColor = CssColor.make("rgba(170,200,200,0.8)");
	private final CssColor redrawColor = CssColor.make("rgba(95,95,205,0.2)");

    private final int width,height;
    private int mouseX,mouseY;
	private boolean active = false;
	
	
	public TableCanvas(int w, int h)
	{
		width = w;
		height = h;
		mouseX=width/2;
		mouseY=height/2;
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
		return canvas;
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

		Event cueBall = Interpolator.interpolate(table.ball(1), t);
		if (cueBall.state == State.Stationary)
		{
			aim.setAim(PlotScale.mouseToWorld(mouseX, mouseY), cueBall.pos);
			aim.plotAim(backBufferContext);
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
	
	
	private void initHandlers() 
	{
	    canvas.addMouseMoveHandler(
	    		new MouseMoveHandler() 
	    		{
	    			public void onMouseMove(MouseMoveEvent event) {
	    				if(active)
	    					{
	    					mouseX = event.getRelativeX(canvas.getElement());
	    					mouseY = event.getRelativeY(canvas.getElement());    				
	    					}
	    			}
	    		});
	
	    canvas.addMouseDownHandler(
	    		new MouseDownHandler() {
	    			public void onMouseDown(MouseDownEvent event) {
	    				active = true;	    				
	    				mouseX = event.getRelativeX(canvas.getElement());
	    				mouseY = event.getRelativeY(canvas.getElement());
	    			}
	    		});

	    canvas.addMouseUpHandler(
	    		new MouseUpHandler() {
	    			public void onMouseUp(MouseUpEvent event) {
	    				active = false;
	    			}
	    		});

	    canvas.addMouseOutHandler(new MouseOutHandler() {
	        public void onMouseOut(MouseOutEvent event) {
	          active = false;
	        }
	      });
	}

	public Vector3D getAim()
	{
		return aim.getAim();
	}
	
}
