package org.motion.ballsimapp.canvas;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Cushion;



public class PlotScale {

 	static int w,h;
 	static double scale;

 	
	public static void setWindowInfo( int w_, int h_) 
	{
		w = w_;
		h = h_;
		
		// scale to fit table in y dimension
		
		scale = (double)h/Cushion.yp*0.3*1.35;
	}

	public static int screenX(double x)
 	{
 		return (int) (x*scale) + w/2;
 	}
 	
	public static int screenY(double y)
 	{
 		return (int) (y*scale) + h/2;
 	}
 	
	public static int scaled(double r)
	{
		return (int) (r*scale);
	}
 	
	public static Vector3D mouseToWorld(int mouseX, int mouseY)
	{
		return new Vector3D((mouseX-w/2)/scale,(mouseY-h/2)/scale,0);
	}
}
