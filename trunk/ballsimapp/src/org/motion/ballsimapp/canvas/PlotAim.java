package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotAim {

	private static CssColor black = CssColor.make("rgba(5,5,5,0.1)");
	private static CssColor white = CssColor.make("rgba(255,255,255,9)");
	
	private Vector3D cueBall = Vector3D.ZERO;
	private Vector3D aimPoint = Vector3D.PLUS_I;

	public void setAim(Vector3D aimPoint_,Vector3D cueBall_)
 	{
		aimPoint = aimPoint_;
		cueBall = cueBall_;
 	}

	public Vector3D getAim()
	{
		return aimPoint.subtract(cueBall).normalize();
	}
	
	public void plotAim(Context2d context)
 	{
		plotAim(PlotScale.screenX(aimPoint.getX()),
				PlotScale.screenY(aimPoint.getY()),
				PlotScale.screenX(cueBall.getX()),
				PlotScale.screenY(cueBall.getY()),context);
 	}

	
	private static void plotAim(double x, double y, double fromX, double fromY,Context2d context)
 	{
	    context.beginPath();
		context.setStrokeStyle(white);
	    context.setLineWidth(PlotScale.scaled(Ball.R*0.02));

	    context.moveTo(fromX,fromY);
	    context.lineTo(x,y);
	    context.stroke();
	    context.closePath();

	    context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(black);
		context.arc(x, y, PlotScale.scaled(Ball.R), 0,Math.PI * 2.0); 	
	    context.stroke();    
	    context.closePath();

	    context.beginPath();
		context.setStrokeStyle(black);
		context.setLineWidth(0.5);
	    context.moveTo(x-PlotScale.scaled(Ball.R)*1.3, y);
	    context.lineTo(x+PlotScale.scaled(Ball.R)*1.3, y);
	    context.moveTo(x,y-PlotScale.scaled(Ball.R)*1.3);
	    context.lineTo(x,y+PlotScale.scaled(Ball.R)*1.3);
	    context.stroke();
	    context.closePath();
 	}
}
