package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotAim {

	private static CssColor white = CssColor.make("rgba(255,255,255,0.1)");
	private static CssColor brown = CssColor.make("rgba(155,55,55,1)");
	
	private Vector3D cueBallPosition = Vector3D.ZERO;
	private Vector3D aimDirection = Vector3D.PLUS_I;

	private final PlotScale scale;
	
	public PlotAim(PlotScale scale)
	{
		this.scale = scale;		
	}
	
	public void setAimDirection(Vector3D aimDirection)
 	{
		this.aimDirection = aimDirection;
 	}

	public void setCueBallPosition(Vector3D cueBallPosition_)
 	{
		cueBallPosition = cueBallPosition_;
 	}
	
	public void setAimToTarget(Vector3D target)
	{
		aimDirection = target.subtract(cueBallPosition).normalize();
	}

	public Vector3D getAimDirection()
	{
		return aimDirection;
	}
	

	
	public void plotAim(Context2d context)
 	{	
		plotAimPath(context);
		plotCue(context);
 	}

	
	private void plotAimPath(Context2d context)
 	{
	    context.beginPath();
		context.setStrokeStyle(white);
	    context.setLineWidth(scale.scaled(Ball.R*2));

	    double start = -scale.scaled(Ball.R * 1);
	    double end = scale.scaled(Ball.R*130);
	    
	    plotAimDirection(start,end,context);
	    
	    context.stroke();
	    context.closePath();
	    
	    plotCue(context);
 	}
	
	private void plotCue(Context2d context)
 	{
	    context.beginPath();
		context.setStrokeStyle(brown);
	    context.setLineWidth(scale.scaled(Ball.R*0.5));
	    
	    double start = -scale.scaled(Ball.R * 1.5);
	    double end = -scale.scaled(Ball.R*30);
	    
	    plotAimDirection(start,end,context);
	    
	    context.stroke();
	    context.closePath();		
 	}	

	private void plotAimDirection(double start, double end,Context2d context)
 	{
	    double dirX = aimDirection.getX();
	    double dirY = aimDirection.getY();	
	    double x = scale.screenX(cueBallPosition.getX());
	    double y = scale.screenY(cueBallPosition.getY());
	    
	    context.moveTo(x+(dirX*start),y+(dirY*start));
	    context.lineTo(x+(dirX*end),y+(dirY*end));
 	}	

}
