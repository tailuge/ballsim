package org.motion.ballsim.physics;

import org.motion.ballsim.gwtsafe.Function;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.util.Events;
import org.motion.ballsim.physics.util.Position;
import org.motion.ballsim.util.Guard;
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
	 * need to get the coefficients of quadratic who's solution is: 
	 * 
	 * cushion = pos0 + vel0*t + 1/2 acc * t^2
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

		Guard.isTrue(Guard.active && tCollision > 0);

		tCollision = org.motion.ballsim.gwtsafe.Quadratic.latestTrueTime(
				onTable, tCollision);

		Guard.isTrue(Guard.active && tCollision > 0);
		Guard.isTrue(Guard.active && tCollision < maxt);

		Event reflected = Events.reflect(e.advanceDelta(tCollision), axis);
		if (hasPockets && Position.isCushionEventInPocketRegion(reflected))
			return null;

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
		Guard.isTrue(Guard.active && Position.onTable(e));
		Event next = null;
		next = Events.first(next,
				hits(e, Vector3D.PLUS_I, Position.onX(e), xp, maxt, hasPockets));
		next = Events.first(next,
				hits(e, Vector3D.PLUS_I, Position.onX(e), xn, maxt, hasPockets));
		next = Events.first(next,
				hits(e, Vector3D.PLUS_J, Position.onY(e), yp, maxt, hasPockets));
		next = Events.first(next,
				hits(e, Vector3D.PLUS_J, Position.onY(e), yn, maxt, hasPockets));
		Guard.isTrue(Guard.active && ((next == null) || Position.onTable(next)));
		return next;
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
				Guard.isTrue(Guard.active && next.t > e.t);
				Guard.isTrue(Guard.active && Position.onTable(next));
			}
		}

		if ((next != null) && (next.t < maxt))
			return next;
		return null;
	}


}
