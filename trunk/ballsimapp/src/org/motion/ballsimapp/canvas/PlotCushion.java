package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.Pocket;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;




public class PlotCushion 
{

	final static CssColor knuckleColour = CssColor.make("rgba(0,0,255,1)");
	final static CssColor holeColour = CssColor.make("rgba(0,0,0,1)");

	public static void plot(Context2d context)
	{
		for(Vector3D pos : Pocket.knuckleList)
		{
			plotKnuckle(context,pos);
		}
		
		for(Vector3D pos : Pocket.holeList)
		{
			plotHole(context,pos);
		}

		plotCushions(context);
	}
	
	private static void plotCushions(Context2d context)
	{
        context.beginPath();
		context.setStrokeStyle(knuckleColour);
		context.setFillStyle(knuckleColour);
		context.setLineWidth(0.25);
		
		scaledLine(context,Cushion.xn-Ball.R, Pocket.p4k1.getY(), Cushion.xn-Ball.R, Pocket.p6k1.getY()); 	
		scaledLine(context,Cushion.xn-Ball.R, Pocket.p6k2.getY(), Cushion.xn-Ball.R, Pocket.p1k1.getY()); 	

		scaledLine(context,Cushion.xp+Ball.R, Pocket.p4k1.getY(), Cushion.xp+Ball.R, Pocket.p6k1.getY()); 	
		scaledLine(context,Cushion.xp+Ball.R, Pocket.p6k2.getY(), Cushion.xp+Ball.R, Pocket.p1k1.getY()); 	

		scaledLine(context,Pocket.p4k2.getX(),Cushion.yn-Ball.R, Pocket.p3k2.getX(), Cushion.yn-Ball.R); 	
		scaledLine(context,Pocket.p4k2.getX(),Cushion.yp+Ball.R, Pocket.p3k2.getX(), Cushion.yp+Ball.R); 	

		context.closePath();
	    //context.fill();        	
	}
	
	private static void scaledLine(Context2d context, double x, double y, double x2, double y2)
	{
		context.moveTo(PlotScale.screenX(x), PlotScale.screenY(y));
		context.lineTo(PlotScale.screenX(x2), PlotScale.screenY(y2));
		context.stroke();
	}
	
	private static void plotKnuckle(Context2d context, Vector3D pos)
	{
        double x = PlotScale.screenX(pos.getX());
        double y = PlotScale.screenY(pos.getY());

        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(knuckleColour);
		context.setFillStyle(knuckleColour);
		context.arc(x, y, PlotScale.scaled(Ball.R), 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        
	}
	
	private static void plotHole(Context2d context, Vector3D pos)
	{
        double x = PlotScale.screenX(pos.getX());
        double y = PlotScale.screenY(pos.getY());

        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(knuckleColour);
		context.setFillStyle(holeColour);
		context.arc(x, y, PlotScale.scaled(Ball.R*2), 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        
	}

}
