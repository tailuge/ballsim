package org.motion.ballsimapp.canvas;

import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.gwtsafe.Vector3D;



public class PlotScale {

 	 int w,h;
 	 double scale;

 	
	public void setWindowInfo( int w_, int h_) 
	{
		w = w_;
		h = h_;
		
		// scale to fit table in y dimension
		
		scale = (double)h/Cushion.yp*0.364;
	}

	public  int screenX(double x)
 	{
 		return (int) (x*scale) + w/2;
 	}
 	
	public  int screenY(double y)
 	{
 		return (int) (y*scale) + h/2;
 	}
 	
	public  int scaled(double r)
	{
		return (int) (r*scale);
	}
 	
	public Vector3D mouseToWorld(int mouseX, int mouseY)
	{
		return new Vector3D((mouseX-w/2)/scale,(mouseY-h/2)/scale,0);
	}
}
