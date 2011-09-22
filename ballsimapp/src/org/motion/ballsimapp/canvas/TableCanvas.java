package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.pool.AimChange;

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

	private final PlotScale scale = new PlotScale();
	
	private Canvas canvas;
	private Canvas backBuffer;
	private Canvas background;

	private Context2d context;
	private Context2d backBufferContext;
	private Context2d backgroundContext;

	private final PlotAim aim;	
	private final CssColor redrawColor = CssColor.make("rgba(95,95,205,0.5)");
    private final int width,height;
	private final AimChange aimChangeHandler;

	private int mouseX,mouseY;
	private boolean active = false;
	
	public TableCanvas(int w, int h,AimChange aimChangeHandler)
	{
		width = w;
		height = h;
		mouseX=width/2;
		mouseY=height/2;
		scale.setWindowInfo(width, height);
		aim = new PlotAim(scale);
		this.aimChangeHandler = aimChangeHandler;
	}
	
	private void initialiseBackground()
	{
		background = Canvas.createIfSupported();
	    background.setWidth(width + "px");
	    background.setHeight(height + "px");
	    background.setCoordinateSpaceWidth(width);
	    background.setCoordinateSpaceHeight(height);
	    backgroundContext = background.getContext2d();
	    backgroundContext.setFillStyle(redrawColor);
	    backgroundContext.fillRect(0, 0, width, height);
	    PlotCushion.plot(backgroundContext,scale);
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
	    
	    initialiseBackground();
	    
	    initHandlers();
	    
		clearBackBuffer(); 
		moveBackBufferToFront(backBufferContext,context);
		
		return canvas;
	}


	
	public void plotAtTime(Table table,double t)
	{
		clearBackBuffer(); 

		for(Ball ball : table.balls())
		{
			Event e = Interpolator.interpolate(ball, t);
			PlotEvent.plotEvent(e, backBufferContext,scale);
		}

		moveBackBufferToFront(backBufferContext,context);

	}

	void clearBackBuffer() 
	{
		backBufferContext.drawImage(backgroundContext.getCanvas(), 0, 0);
	}
	
	  
	public void moveBackBufferToFront(Context2d back, Context2d front) 
	{
		front.drawImage(back.getCanvas(), 0, 0);
	}
	
	private void updateAim(int x, int y)
	{
		moveBackBufferToFront(backBufferContext,context);
		aim.setAim(scale.mouseToWorld(x, y));
		aim.plotAim(context);
		aimChangeHandler.handle();
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
	    					updateAim(mouseX,mouseY);
	    					}
	    			}
	    		});
	
	    canvas.addMouseDownHandler(
	    		new MouseDownHandler() {
	    			public void onMouseDown(MouseDownEvent event) {
	    				active = true;	    				
	    				mouseX = event.getRelativeX(canvas.getElement());
	    				mouseY = event.getRelativeY(canvas.getElement());
    					updateAim(mouseX,mouseY);
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

	public void beginAim(Table table) 
	{
		Event cueBall = Interpolator.interpolate(table.ball(1), 0);
		aim.setAimFrom(cueBall.pos);
	}

	public Vector3D getAim()
	{
		return aim.getAim().subtract(aim.getAimPoint());
	}

	public Vector3D getAimPoint()
	{
		return aim.getAimPoint();	
	}

}
