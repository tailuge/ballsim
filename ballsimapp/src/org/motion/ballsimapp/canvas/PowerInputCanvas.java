package org.motion.ballsimapp.canvas;


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

public class PowerInputCanvas {
	
	private Canvas canvas;
	private Context2d context;
	private final CssColor redColor = CssColor.make("rgba(255,0,0,0.6)");
	private final CssColor whiteColor = CssColor.make("rgba(170,200,200,0.8)");

    private final int width,height;
    private int mouseX;
	private boolean active = false;
	
	private double power = 0.75;
	
	public PowerInputCanvas(int width_, int height_)
	{
		height = height_;
		width = width_;
		mouseX = width/2;
	}

	public double getPower()
	{
		return power;
	}
	
	public Canvas getInitialisedCanvas()
	{
		canvas = Canvas.createIfSupported();	
		
	    canvas.setWidth(width + "px");
	    canvas.setHeight(height + "px");
	    canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);
	    
	    context = canvas.getContext2d();
	    initHandlers();
	    updatePower();
		return canvas;
	}
	
	
	void initHandlers() 
	{
	    canvas.addMouseMoveHandler(
	    		new MouseMoveHandler() 
	    		{
	    			public void onMouseMove(MouseMoveEvent event) {
	    				mouseX = event.getRelativeX(canvas.getElement());
	    				if (active) updatePower();
	    			}
	    		});
	
	    canvas.addMouseDownHandler(
	    		new MouseDownHandler() {
	    			public void onMouseDown(MouseDownEvent event) {
	    				mouseX = event.getRelativeX(canvas.getElement());
	    				active = true;
	    				updatePower();
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

	private void updatePower()
	{
		computePower();
		plotPowerScale();
		plotPower(mouseX);
	}
	
	private void computePower()
	{
		power = (double)mouseX/(double)width;
	}
	
	private void plotPower(int x)
 	{
        context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(redColor);
		context.fillRect(0, 0, mouseX, height);
	    context.closePath();
	    context.fill();        
 	}
	
	private void plotPowerScale()
 	{
        context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(whiteColor);
		context.fillRect(0, 0, width, height);
	    context.closePath();
	    context.fill();        
 	}
}
