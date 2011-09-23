package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.pool.handlers.AimChange;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

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

	
	public TableCanvas(int w, int h,AimChange aimChangeHandler)
	{
		width = w;
		height = h;
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
		aim.setAimToTarget(scale.mouseToWorld(x, y));
		moveBackBufferToFront(backBufferContext,context);
		aim.plotAim(context);
	}
	

	private void initHandlers() {

		new ActiveMouseMoveHandler(canvas,
				new ActiveMouseMoveHandler.MouseEvent() {

					@Override
					public void handle(int mouseX, int mouseY) {
						updateAim(mouseX,mouseY);
						aimChangeHandler.handle();
					}
				});
	}
	

	public void beginAim(Vector3D position) 
	{
		aim.setCueBallPosition(position);
	}

	public Vector3D getAim()
	{
		return aim.getAimDirection();
	}

	public void setAim(Vector3D aimPoint)
	{
		aim.setAimDirection(aimPoint);
		moveBackBufferToFront(backBufferContext,context);
		aim.plotAim(context);
	}


}
