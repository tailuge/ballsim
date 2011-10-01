package org.motion.ballsim.physics;

import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.ball.Transition;
import org.motion.ballsim.physics.util.Position;
import org.motion.ballsim.util.Guard;
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
				Guard.isTrue(Guard.active && next.t > e.t);
				Guard.isTrue(Guard.active && Position.onTable(next));
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

		for (Event knuckle : PocketGeometry.knuckles) {
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
		reflected.type = Transition.KnuckleCushion;
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
				Guard.isTrue(Guard.active && next.t > e.t);
				Guard.isTrue(Guard.active && Position.onTable(next));
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

		for (Event hole : PocketGeometry.holes) {
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
			pot.type = Transition.Potting;

			pot.vel = soonestPocket.pos.subtract(pot.pos).normalize()
					.scalarMultiply(15);
			// store target position of pocket in side spin for now.
			pot.sidespin = soonestPocket.pos;
			return pot;
		}

		return null;
	}


}
