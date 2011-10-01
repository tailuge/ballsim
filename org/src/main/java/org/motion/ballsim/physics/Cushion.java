package org.motion.ballsim.physics;

import org.motion.ballsim.gwtsafe.Function;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.ball.Transition;
import org.motion.ballsim.physics.util.Position;
import org.motion.ballsim.util.Assert;
import org.motion.ballsim.util.UtilVector3D;

/**
 * @author luke
 * 
 *         Static class that computes Event at intersection of ball with cushion
 */
public final class Cushion {

	public final static double yp = 30 * Ball.R;
	public final static double yn = -yp;
	public final static double xp = 14.55 * Ball.R;
	public final static double xn = -xp;

	/**
	 * Collides with cushion when equation of motion intersects cushion
	 * 
	 * need to get the coefficients of quadratic who's solution is: cush = pos0
	 * + vel0*t + 1/2 acc * t^2
	 * 
	 * @param e
	 *            - input event
	 * @param axis
	 *            - unit vector of X or Y indicating perpendicular to cushion
	 * @param cush
	 *            - position of cushion
	 * @param maxt
	 *            - time beyond which equation of motion invalid
	 * @return reflected cushion collision event if it occurs within maxt
	 */
	private static Event hits(final Event e, Vector3D axis,
			Function<Double, Boolean> onTable, double cush, double maxt,
			boolean hasPockets) {
		double A = UtilVector3D.projectionOnAxis(e.acceleration(), axis) * 0.5;
		double B = UtilVector3D.projectionOnAxis(e.vel, axis);
		double C = UtilVector3D.projectionOnAxis(e.pos, axis) - cush;

		double tCollision = org.motion.ballsim.gwtsafe.Quadratic
				.leastPositiveRoot(A, B, C);

		if ((tCollision <= 0) || (tCollision > maxt))
			return null;

		Assert.isTrue(tCollision > 0);

		tCollision = org.motion.ballsim.gwtsafe.Quadratic.latestTrueTime(
				onTable, tCollision);

		Assert.isTrue(tCollision > 0);
		Assert.isTrue(tCollision < maxt);

		Event reflected = reflect(e.advanceDelta(tCollision), axis);
		if (hasPockets && Pocket.isCushionEventInPocketRegion(reflected))
			return null;

		return reflected;
	}

	/**
	 * Given a ball at collision point determine its new state after reflection
	 * in the cushion
	 * 
	 * @param e
	 * @param axis
	 * @return
	 */
	private static Event reflect(Event e, Vector3D axis) {
		Event reflected = new Event(e);
		reflected.vel = UtilVector3D.reflectAlongAxis(e.vel, axis);
		reflected.angularVel = e.angularVel.scalarMultiply(0.4); // until done
		reflected.state = State.deriveStateOf(reflected);
		reflected.type = Transition.Cushion;

		// Vector3D sideSpinAffectOnVel =
		// Vector3D.crossProduct(axis,e.sidespin);

		return reflected;
	}

	/**
	 * Given an event determines if a cushion is hit before maxt and returns the
	 * new event representing the reflection in the cushion
	 * 
	 * @param e
	 * @param maxt
	 * @return
	 */
	public static Event hit(Event e, double maxt, boolean hasPockets) {
		Assert.isTrue(Position.onTable(e));
		Event next = null;
		next = sooner(next,
				hits(e, Vector3D.PLUS_I, Position.onX(e), xp, maxt, hasPockets));
		next = sooner(next,
				hits(e, Vector3D.PLUS_I, Position.onX(e), xn, maxt, hasPockets));
		next = sooner(next,
				hits(e, Vector3D.PLUS_J, Position.onY(e), yp, maxt, hasPockets));
		next = sooner(next,
				hits(e, Vector3D.PLUS_J, Position.onY(e), yn, maxt, hasPockets));
		Assert.isTrue((next == null) || Position.onTable(next));
		return next;
	}

	private static Event sooner(Event current, Event proposed) {
		if ((proposed == null) || (proposed.t < 0))
			return current;
		if (current == null)
			return proposed;
		if (proposed.t < current.t)
			return proposed;

		return current;
	}

	public static Event nextCushionHit(Table table, double maxt) {
		Event next = null;
		for (Ball ball : table.balls()) {
			Event e = ball.lastEvent();
			if (!e.state.canCollideWithCushions())
				continue;

			Event eCushion = Cushion.hit(e, maxt, table.hasPockets);
			if (eCushion == null)
				continue;

			// No impact with cushion if in pocket region.

			if ((next == null) || (eCushion.t < next.t)) {
				next = eCushion;
				Assert.isTrue(next.t > e.t);
				Assert.isTrue(Position.onTable(next));
			}
		}

		if ((next != null) && (next.t < maxt))
			return next;
		return null;
	}


}
