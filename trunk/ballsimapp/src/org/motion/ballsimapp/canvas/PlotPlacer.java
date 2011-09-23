package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Cushion;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotPlacer {


	private static CssColor red = CssColor.make("rgba(155,0,0,0.1)");
	
	private Vector3D cueBallPosition = Vector3D.ZERO;

	private final PlotScale scale;
	
	public PlotPlacer(PlotScale scale)
	{
		this.scale = scale;		
	}
	
	public void setCueBallPosition(Vector3D cueBallPosition)
 	{		
		this.cueBallPosition = clipToTable(cueBallPosition);
 	}

	private Vector3D clipToTable(Vector3D pos)
	{
		double x = pos.getX();
		double y = pos.getY();
		if (x>Cushion.xp) x=Cushion.xp;
		if (x<Cushion.xn) x=Cushion.xn;
		if (y>Cushion.yp) y=Cushion.yp;
		if (y<Cushion.yn) y=Cushion.yn;
		return new Vector3D(x,y,0);
	}
	
	public void plotPlacer(Context2d context)
 	{
	    context.beginPath();
		context.setFillStyle(red);

	    double x = scale.screenX(cueBallPosition.getX());
	    double y = scale.screenY(cueBallPosition.getY());
	
		context.arc(x, y, scale.scaled(5*Ball.R), 0,Math.PI * 2.0, true); 	
	    context.fill();        
	    context.stroke();
	    context.closePath();
 	}

	public Vector3D getCueBallPosition() {
		return cueBallPosition;
	}

}
