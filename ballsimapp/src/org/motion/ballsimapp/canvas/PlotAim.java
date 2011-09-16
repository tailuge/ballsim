package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotAim {

	private static CssColor black = CssColor.make("rgba(5,5,5,0.1)");
	private static CssColor white = CssColor.make("rgba(255,255,255,9)");
	
	private Vector3D cuePoint = Vector3D.ZERO;
	private Vector3D aimPoint = Vector3D.PLUS_I;

	private final PlotScale scale;
	
	public PlotAim(PlotScale scale)
	{
		this.scale = scale;		
	}
	
	public void setAim(Vector3D aimPoint_)
 	{
		aimPoint = aimPoint_;
 	}

	public void setAimFrom(Vector3D cuePoint_)
 	{
		cuePoint = cuePoint_;
 	}
	
	public Vector3D getAim()
	{
		return aimPoint.subtract(cuePoint).normalize();
	}
	
	
	public void plotAim(Context2d context)
 	{		
		plotAim(scale.screenX(aimPoint.getX()),
				scale.screenY(aimPoint.getY()),
				scale.screenX(cuePoint.getX()),
				scale.screenY(cuePoint.getY()),context);
 	}

	
	private void plotAim(double x, double y, double fromX, double fromY,Context2d context)
 	{
	    context.beginPath();
		context.setStrokeStyle(white);
	    context.setLineWidth(scale.scaled(Ball.R*0.02));

	    context.moveTo(fromX,fromY);
	    context.lineTo(x,y);
	    context.stroke();
	    context.closePath();

	    context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.arc(x, y, scale.scaled(Ball.R), 0,Math.PI * 2.0); 	
	    context.stroke();    
	    context.closePath();

	    context.beginPath();
		context.setStrokeStyle(black);
		context.setLineWidth(0.5);
	    context.moveTo(x-scale.scaled(Ball.R)*1.3, y);
	    context.lineTo(x+scale.scaled(Ball.R)*1.3, y);
	    context.moveTo(x,y-scale.scaled(Ball.R)*1.3);
	    context.lineTo(x,y+scale.scaled(Ball.R)*1.3);
	    context.stroke();
	    context.closePath();
 	}
}
