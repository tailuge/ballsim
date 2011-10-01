package org.motion.ballsim.physics.util;

import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;

/**
 * @author luke
 * 
 *         Given a list of events construct interpolated event at arbitrary time
 */
public final class Interpolate {

	/**
	 * Interpolated event at time
	 */
	public static Event toTime(Ball ball, double t) {
		Event previous = null;
		for (Event e : ball.getAllEvents()) {
			if (previous != null && t > previous.t && t < e.t)
				return previous.advanceDelta(t - previous.t);
			previous = e;
		}
		return previous;
	}
}
