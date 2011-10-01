package org.motion.ballsim.physics;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.util.UtilEvent;

public final class PocketGeometry {

	public final static double sep = Ball.R * 4.0;
	public final static double depth = Ball.R * 1.5;

	public final static double midSep = Ball.R * 3.0;
	public final static double midDepth = Ball.R * 3.5;

	// bottom left
	public final static Vector3D p1k1 = new Vector3D(Cushion.xn - 2 * Ball.R,
			Cushion.yp + 2 * Ball.R - sep, 0);
	public final static Vector3D p1k2 = new Vector3D(Cushion.xn - 2 * Ball.R
			+ sep, Cushion.yp + 2 * Ball.R, 0);
	public final static Vector3D p1 = new Vector3D(Cushion.xn - depth,
			Cushion.yp + depth, 0);

	// bottom right
	public final static Vector3D p2k1 = new Vector3D(Cushion.xp + 2 * Ball.R,
			Cushion.yp + 2 * Ball.R - sep, 0);
	public final static Vector3D p2k2 = new Vector3D(Cushion.xp + 2 * Ball.R
			- sep, Cushion.yp + 2 * Ball.R, 0);
	public final static Vector3D p2 = new Vector3D(Cushion.xp + depth,
			Cushion.yp + depth, 0);

	// top right
	public final static Vector3D p3k1 = new Vector3D(Cushion.xp + 2 * Ball.R,
			Cushion.yn - 2 * Ball.R + sep, 0);
	public final static Vector3D p3k2 = new Vector3D(Cushion.xp + 2 * Ball.R
			- sep, Cushion.yn - 2 * Ball.R, 0);
	public final static Vector3D p3 = new Vector3D(Cushion.xp + depth,
			Cushion.yn - depth, 0);

	// top left
	public final static Vector3D p4k1 = new Vector3D(Cushion.xn - 2 * Ball.R,
			Cushion.yn - 2 * Ball.R + sep, 0);
	public final static Vector3D p4k2 = new Vector3D(Cushion.xn - 2 * Ball.R
			+ sep, Cushion.yn - 2 * Ball.R, 0);
	public final static Vector3D p4 = new Vector3D(Cushion.xn - depth,
			Cushion.yn - depth, 0);

	// middle left
	public final static Vector3D p5k1 = new Vector3D(Cushion.xn - 2 * Ball.R,
			-midSep, 0);
	public final static Vector3D p5k2 = new Vector3D(Cushion.xn - 2 * Ball.R,
			midSep, 0);
	public final static Vector3D p5 = new Vector3D(Cushion.xn - midDepth, 0, 0);

	// middle right
	public final static Vector3D p6k1 = new Vector3D(Cushion.xp + 2 * Ball.R,
			-midSep, 0);
	public final static Vector3D p6k2 = new Vector3D(Cushion.xp + 2 * Ball.R,
			midSep, 0);
	public final static Vector3D p6 = new Vector3D(Cushion.xp + midDepth, 0, 0);

	// expose for rendering
	public final static List<Vector3D> knuckleList;
	public final static List<Vector3D> holeList;

	// use as events
	public final static List<Event> knuckles;
	public final static List<Event> holes;

	// setup static lists
	static {
		knuckleList = new ArrayList<Vector3D>();
		knuckleList.add(p1k1);
		knuckleList.add(p1k2);
		knuckleList.add(p2k1);
		knuckleList.add(p2k2);
		knuckleList.add(p3k1);
		knuckleList.add(p3k2);
		knuckleList.add(p4k1);
		knuckleList.add(p4k2);
		knuckleList.add(p5k1);
		knuckleList.add(p5k2);
		knuckleList.add(p6k1);
		knuckleList.add(p6k2);

		holeList = new ArrayList<Vector3D>();
		holeList.add(p1);
		holeList.add(p2);
		holeList.add(p3);
		holeList.add(p4);
		holeList.add(p5);
		holeList.add(p6);

		knuckles = new ArrayList<Event>();

		for (Vector3D knuckle : knuckleList) {
			knuckles.add(UtilEvent.stationary(knuckle));
		}

		holes = new ArrayList<Event>();

		for (Vector3D hole : holeList) {
			holes.add(UtilEvent.stationary(hole));
		}
	}


}
