package org.motion.ballsim;

import org.motion.ballsim.gwtsafe.Vector3D;



public class SlidingMotion 
{

	/**
	 * Acceleration is constant during sliding phase and works to
	 * reach rolling equilibrium. Magnitude is independent of speed / spin
	 * 
	 * @return acceleration vector when sliding
	 */
	public static Vector3D acceleration(Event e) {
		try 
		{
			return getChangeToNr(e).normalize().scalarMultiply(-Ball.accelSlide);
		} 
		catch (ArithmeticException ae) 
		{
			return Vector3D.ZERO;
		}

	}
	
	public static Vector3D angularAcceleration(Event e) {
		return UtilVector3D.crossUp(acceleration(e)).scalarMultiply((-5.0 / 2.0) * Ball.R);
	}
	
	/**
	 * Velocity at natural roll given a starting state that is sliding
	 * is well analysed (see Amateur Physics by Sheperd - Vnr = V0*5/7 +Rw0*2/7) 
	 * This returns the change from current velocity to that equilibrium point
	 * @return
	 */
	private static Vector3D getChangeToNr(Event e) 
	{	
		return e.vel
				.scalarMultiply(5.0 / 7.0)
				.add(UtilVector3D.crossUp(e.angularVel)
				.scalarMultiply(-Ball.R * 2.0 / 7.0)).subtract(e.vel);		
	}
	
	private static double timeToNaturalRollEquilibrium(Event e) 
	{
		return getChangeToNr(e).getNorm() / -Ball.accelSlide;
	}
	
	
	/**
	 * When a ball is sliding the next state it will achieve is rolling
	 * 
	 * @return event when stationary
	 */
	public static Event next(Event e) 
	{
		Event rolling = e.advanceDelta(timeToNaturalRollEquilibrium(e));
		rolling.state = State.Rolling;
		rolling.type = EventType.RollEquilibrium;
		return rolling;
	}
}
