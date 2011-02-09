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
	
	public final static double sep = Ball.R * 3.5;
	public final static double depth = Ball.R * 1.5;

	public final static double midSep = Ball.R * 2.5;
	public final static double midDepth = Ball.R * 3;

	public final static Vector3D p1k1 = new Vector3D(Cushion.xn - 2*Ball.R, Cushion.yp + 2*Ball.R - sep,0);
	public final static Vector3D p1k2 = new Vector3D(Cushion.xn - 2*Ball.R + sep, Cushion.yp + 2*Ball.R,0);
	public final static Vector3D p1 = new Vector3D(Cushion.xn  - depth, Cushion.yp + depth,0);

	public final static Vector3D p2k1 = new Vector3D(Cushion.xp + 2*Ball.R, Cushion.yp + 2*Ball.R - sep,0);
	public final static Vector3D p2k2 = new Vector3D(Cushion.xp + 2*Ball.R - sep, Cushion.yp + 2*Ball.R,0);
	public final static Vector3D p2 = new Vector3D(Cushion.xp  + depth, Cushion.yp + depth,0);

	public final static Vector3D p3k1 = new Vector3D(Cushion.xp + 2*Ball.R, Cushion.yn - 2*Ball.R + sep,0);
	public final static Vector3D p3k2 = new Vector3D(Cushion.xp + 2*Ball.R - sep, Cushion.yn - 2*Ball.R,0);
	public final static Vector3D p3 = new Vector3D(Cushion.xp  + depth, Cushion.yn - depth,0);

	public final static Vector3D p4k1 = new Vector3D(Cushion.xn - 2*Ball.R, Cushion.yn - 2*Ball.R + sep,0);
	public final static Vector3D p4k2 = new Vector3D(Cushion.xn - 2*Ball.R + sep, Cushion.yn - 2*Ball.R,0);
	public final static Vector3D p4 = new Vector3D(Cushion.xn  - depth, Cushion.yn - depth,0);

	public final static Vector3D p5k1 = new Vector3D(Cushion.xn - 2*Ball.R, - midSep,0);
	public final static Vector3D p5k2 = new Vector3D(Cushion.xn - 2*Ball.R,   midSep,0);
	public final static Vector3D p5 = new Vector3D(Cushion.xn  - midDepth, 0,0);

	public final static Vector3D p6k1 = new Vector3D(Cushion.xp + 2*Ball.R, - midSep,0);
	public final static Vector3D p6k2 = new Vector3D(Cushion.xp + 2*Ball.R,   midSep,0);
	public final static Vector3D p6 = new Vector3D(Cushion.xp  + midDepth, 0,0);

}
