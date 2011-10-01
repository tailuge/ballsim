package org.motion.ballsim.physics;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsim.util.UtilVector3D;

/**
 * Use ball collision logic to get knuckle events
 * Use ball collision logic to get in pocket event
 */
public final class Pocket {

	public static Event nextKnuckleCollision(Table table, double maxt) {
		Event next = null;
		for (Ball ball : table.balls()) {
			Event e = ball.lastEvent();

			if (!e.state.canCollideWithCushions())
				continue;

			Event eKnuckle = nextKnuckleCollision(e, maxt);
			if (eKnuckle == null)
				continue;

			if ((next == null) || (eKnuckle.t < next.t)) {
				next = eKnuckle;
				assert(next.t > e.t);
				assert(Cushion.onTable(next));
			}
		}

		if ((next != null) && (next.t < maxt))
			return next;

		return null;

	}

	public static Event nextKnuckleCollision(Event e1, double maxt) {
		// find next knuckle impact

		double soonest = Double.MAX_VALUE;
		Event soonestKnuckle = null;

		for (Event knuckle : knuckles) {
			double t = Collision.collisionTime(e1, knuckle, maxt);
			if ((t > 0) && (t < soonest)) {
				soonest = t;
				soonestKnuckle = knuckle;
			}
		}

		if (soonest < maxt) {
			// progress ball to point of impact, then reflect velocity
			return knuckleBounce(e1.advanceDelta(soonest), soonestKnuckle);
		}

		return null;
	}

	public static Event knuckleBounce(Event e1, Event knuckle) {
		Event reflected = e1;
		reflected.vel = UtilVector3D.reflectAlongAxis(e1.vel, knuckle.pos
				.subtract(e1.pos).normalize());
		reflected.state = State.deriveStateOf(reflected);
		reflected.type = EventType.KnuckleCushion;
		return reflected;
	}

	public static Event nextPot(Table table, double maxt) {
		Event next = null;
		for (Ball ball : table.balls()) {
			Event e = ball.lastEvent();

			if (!e.state.canCollideWithCushions())
				continue;

			Event ePot = nextPot(e, maxt);
			if (ePot == null)
				continue;

			if ((next == null) || (ePot.t < next.t)) {
				next = ePot;
				assert(next.t > e.t);
				assert(Cushion.onTable(next));
			}
		}

		if ((next != null) && (next.t < maxt))
			return next;

		return null;

	}

	public static Event nextPot(Event e1, double maxt) {
		// find next pocket impact

		double soonest = Double.MAX_VALUE;
		Event soonestPocket = null;

		for (Event hole : holes) {
			double t = Collision.collisionTime(e1, hole, maxt);
			if ((t > 0) && (t < soonest)) {
				soonest = t;
				soonestPocket = hole;
			}
		}

		if (soonest < maxt) {
			// progress ball to point of impact, set state as in pocket
			Event pot = e1.advanceDelta(soonest);
			pot.state = State.FallingInPocket;
			pot.type = EventType.Potting;

			pot.vel = soonestPocket.pos.subtract(pot.pos).normalize()
					.scalarMultiply(15);
			// store target position of pocket in side spin for now.
			pot.sidespin = soonestPocket.pos;
			return pot;
		}

		return null;
	}

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

	// use internally as events
	private final static List<Event> knuckles;
	private final static List<Event> holes;

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

	public static boolean isCushionEventInPocketRegion(Event e) {
		// bottom left
		if ((e.pos.getX() < p1k2.getX()) && (e.pos.getY() > p1k1.getY()))
			return true;

		// bottom right
		if ((e.pos.getX() > p2k2.getX()) && (e.pos.getY() > p2k1.getY()))
			return true;

		// top right
		if ((e.pos.getX() > p3k2.getX()) && (e.pos.getY() < p3k1.getY()))
			return true;

		// top left
		if ((e.pos.getX() < p4k2.getX()) && (e.pos.getY() < p4k1.getY()))
			return true;

		// middle pockets
		if ((e.pos.getY() > p5k1.getY()) && (e.pos.getY() < p5k2.getY()))
			return true;

		return false;
	}
}
