package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.PocketGeometry;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.gwtsafe.Vector3D;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;




public class PlotCushion 
{

	final static CssColor knuckleColour = CssColor.make("rgba(0,0,255,1)");
	final static CssColor holeColour = CssColor.make("rgba(0,0,0,1)");

	public static void plot(Context2d context, PlotScale scale)
	{
		for(Vector3D pos : PocketGeometry.knuckleList)
		{
			plotKnuckle(context,pos,scale);
		}
		
		for(Vector3D pos : PocketGeometry.holeList)
		{
			plotHole(context,pos,scale);
		}

		plotCushions(context,scale);
	}
	
	private static void plotCushions(Context2d context, PlotScale scale)
	{
        context.beginPath();
		context.setStrokeStyle(knuckleColour);
		context.setFillStyle(knuckleColour);
		context.setLineWidth(0.25);
		
		scaledLine(context,Cushion.xn-Ball.R, PocketGeometry.p4k1.getY(), Cushion.xn-Ball.R, PocketGeometry.p6k1.getY(),scale); 	
		scaledLine(context,Cushion.xn-Ball.R, PocketGeometry.p6k2.getY(), Cushion.xn-Ball.R, PocketGeometry.p1k1.getY(),scale); 	

		scaledLine(context,Cushion.xp+Ball.R, PocketGeometry.p4k1.getY(), Cushion.xp+Ball.R, PocketGeometry.p6k1.getY(),scale); 	
		scaledLine(context,Cushion.xp+Ball.R, PocketGeometry.p6k2.getY(), Cushion.xp+Ball.R, PocketGeometry.p1k1.getY(),scale); 	

		scaledLine(context,PocketGeometry.p4k2.getX(),Cushion.yn-Ball.R, PocketGeometry.p3k2.getX(), Cushion.yn-Ball.R,scale); 	
		scaledLine(context,PocketGeometry.p4k2.getX(),Cushion.yp+Ball.R, PocketGeometry.p3k2.getX(), Cushion.yp+Ball.R,scale); 	

		context.closePath();
	    //context.fill();        	
	}
	
	private static void scaledLine(Context2d context, double x, double y, double x2, double y2, PlotScale scale)
	{
		context.moveTo(scale.screenX(x), scale.screenY(y));
		context.lineTo(scale.screenX(x2), scale.screenY(y2));
		context.stroke();
	}
	
	private static void plotKnuckle(Context2d context, Vector3D pos, PlotScale scale)
	{
        double x = scale.screenX(pos.getX());
        double y = scale.screenY(pos.getY());

        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(knuckleColour);
		context.setFillStyle(knuckleColour);
		context.arc(x, y, scale.scaled(Ball.R), 0,Math.PI * 2.0, true); 	
		context.stroke();
	    context.closePath();
	}
	
	private static void plotHole(Context2d context, Vector3D pos, PlotScale scale)
	{
        double x = scale.screenX(pos.getX());
        double y = scale.screenY(pos.getY());

        context.beginPath();
		context.setLineWidth(1);
		context.setStrokeStyle(knuckleColour);
		context.setFillStyle(holeColour);
		context.arc(x, y, scale.scaled(Ball.R*2), 0,Math.PI * 2.0, true); 	
	    context.closePath();
	    context.fill();        
	}

}
