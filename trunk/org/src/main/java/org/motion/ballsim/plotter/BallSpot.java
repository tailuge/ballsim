package org.motion.ballsim.plotter;

public class BallSpot 
{
	
	static public double getOffsetX(double thetaX, double thetaY)
	{
		return Math.sin(thetaX)*Math.cos(thetaY);
	}

	static public double getOffsetY(double thetaX, double thetaY)
	{
		return Math.sin(thetaY)*Math.cos(thetaX);
	}

	static public boolean isVisible(double thetaX, double thetaY)
	{
		return Math.sin(thetaY)*Math.sin(thetaX) >= 0.0;
	}
}
