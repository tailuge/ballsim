package org.motion.ballsim.motion;

import org.motion.ballsim.gwtsafe.Vector3D;

public class Pocket {

	/*
	 * Use ball collision logic to get knuckle events
	 * Use ball collision logic to get in pocket event
	 */
	public static Event hits(
			final Event e, 
			Vector3D knuckle1, 
			Vector3D knuckle2, 
			Vector3D center, 
			double pocketRadius, 
			double maxt)
	{
		return null;
	}
	
	public final static double sep = Ball.R * 1.5;
	public final static Vector3D p1k1 = new Vector3D(Cushion.xn - Ball.R, Cushion.yp + Ball.R - sep,0);
	public final static Vector3D p1k2 = new Vector3D(Cushion.xn - Ball.R + sep, Cushion.yp + Ball.R,0);

}
