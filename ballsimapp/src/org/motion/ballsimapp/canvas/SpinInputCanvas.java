package org.motion.ballsimapp.canvas;


import org.motion.ballsim.gwtsafe.Vector3D;

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

public class SpinInputCanvas {
	
	private Canvas canvas;
	private Context2d context;
	private final CssColor redColor = CssColor.make("rgba(255,0,0,0.6)");
	private final CssColor whiteColor = CssColor.make("rgba(170,200,200,0.8)");

    private final int width,height;
    private int mouseX,mouseY;
	private boolean active = false;
	
	private double spinX, spinY;
	
	public Vector3D getSpin()
	{
		return new Vector3D(spinX,spinY,0);
	}
	
	public SpinInputCanvas(int width_, int height_)
	{
		height = height_;
		width = width_;
		mouseX = width/2;
		mouseY = height/2;
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
	    updateSpin();
		return canvas;
	}
	
	
	private void initHandlers() 
	{
	    canvas.addMouseMoveHandler(
	    		new MouseMoveHandler() 
	    		{
	    			public void onMouseMove(MouseMoveEvent event) {
	    				mouseX = event.getRelativeX(canvas.getElement());
	    				mouseY = event.getRelativeY(canvas.getElement());    				
	    				if (active) updateSpin();
	    			}
	    		});
	
	    canvas.addMouseDownHandler(
	    		new MouseDownHandler() {
	    			public void onMouseDown(MouseDownEvent event) {
	    				mouseX = event.getRelativeX(canvas.getElement());
	    				mouseY = event.getRelativeY(canvas.getElement());
	    				active = true;
	    				updateSpin();
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

	private void updateSpin()
	{
		if (computeSpin())
		{
			plotSpinBall();
			plotSpinAim(mouseX,mouseY);
		}
	}
	private boolean computeSpin()
	{
		int dx = (mouseX - width/2);
		int dy = (mouseY - height/2);
		
		if (Math.sqrt(dx*dx +dy*dy)<width/2.1)
		{
			spinX = (double)dx/(double)width;
			spinY = (double)dy/(double)height;
			return true;
		}
		
		return false;
	}
	
	private void plotSpinAim(int x, int y)
 	{
        context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(redColor);
		context.arc(x, y, width/10, 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        
 	}
	
	private void plotSpinBall()
 	{
        context.beginPath();
		context.setLineWidth(1);
		context.setFillStyle(whiteColor);
		context.arc(width/2, height/2,width/2, 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        
 	}
}
