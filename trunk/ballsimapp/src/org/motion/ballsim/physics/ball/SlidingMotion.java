package org.motion.ballsim.physics.ball;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.UtilVector3D;

public final class SlidingMotion {

	/**
	 * Acceleration is constant during sliding phase and works to reach rolling
	 * equilibrium. Magnitude is independent of speed / spin
	 * 
	 * @return acceleration vector when sliding
	 */
	public static Vector3D acceleration(Event e) {
		return getChangeToNr(e).unitScale(-Table.accelSlide);
	}

	public static Vector3D angularAcceleration(Event e) {
		return UtilVector3D.crossUpScale((-5.0 / 2.0) * Ball.R,acceleration(e));
	}

	/**
	 * When ball is sliding side spin remains constant (simplest model)
	 * 
	 * @return vector of acceleration
	 */
	public static Vector3D sidespinAcceleration(Event e) {
		return Vector3D.ZERO;
	}

	/**
	 * Velocity at natural roll given a starting state that is sliding is well
	 * analysed (see Amateur Physics by Sheperd - Vnr = V0*5/7 +Rw0*2/7) This
	 * returns the change from current velocity to that equilibrium point
	 * 
	 * @return
	 */
	private static Vector3D getChangeToNr(Event e) {
		return e.vel.addScaledPair(-9.0/7.0, e.vel, -Ball.R * 2.0 / 7.0, UtilVector3D.crossUp(e.angularVel));
		/*
		return e.vel
				.scalarMultiply(5.0 / 7.0)
				.add(-Ball.R * 2.0 / 7.0,UtilVector3D.crossUp(e.angularVel)).subtract(e.vel);
				*/
	}

	private static double timeToNaturalRollEquilibrium(Event e) {
		return getChangeToNr(e).getNorm() / -Table.accelSlide;
	}

	/**
	 * When a ball is sliding the next state it will achieve is rolling
	 * 
	 * @return event when stationary
	 */
	public static Event next(Event e) {
		Event rolling = e.advanceDelta(timeToNaturalRollEquilibrium(e));
		rolling.state = State.Rolling;
		rolling.type = Transition.RollEquilibrium;
		return rolling;
	}

}