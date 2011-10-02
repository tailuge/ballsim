package org.motion.ballsim.physics.ball;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilVector3D;

/**
 * 
 */
public final class RollingMotion {

	public static boolean isState(Event e) {
		return (e.vel.add(UtilVector3D.crossUp(e.angularVel)).getNorm() < Ball.equilibriumTolerance);
	}

	/**
	 * When rolling the acceleration opposes roll, magnitude independent of
	 * speed / spin
	 * 
	 * @return acceleration vector when rolling
	 */
	public static Vector3D acceleration(Event e) {
		return e.vel.unitScale(Table.accelRoll);
	}

	public static Vector3D angularAcceleration(Event e) {
		return UtilVector3D.crossUpScale(1.0 * Ball.R,acceleration(e));
	}

	/**
	 * When a ball is rolling the next state it will achieve is stationary
	 * 
	 * @return event when stationary
	 */
	public static Event next(Event e) {
		Event stationary = e.advanceDelta(timeToNext(e));
		stationary.state = State.Stationary;
		stationary.type = Transition.FinishedRoll;
		return stationary;
	}

	/**
	 * When a ball is rolling the time it takes to stop can be found from simple
	 * equation of motion
	 * 
	 * solve v = v0 + a * t when v = 0 where acceleration is rolling friction
	 * gives t = -v0/a
	 * 
	 * @return
	 */
	private static double timeToNext(Event e) {
		return -e.vel.getNorm() / Table.accelRoll;
	}

	/**
	 * When ball is rolling, side spin decays to zero at stationary
	 * point(simplest model)
	 * 
	 * @return vector of acceleration
	 */
	public static Vector3D sidespinAcceleration(Event e) {
		return e.sidespin.scalarMultiply(-1.0 / timeToNext(e));
	}
}
