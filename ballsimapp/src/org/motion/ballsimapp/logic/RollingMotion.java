package org.motion.ballsimapp.logic;

import org.motion.ballsimapp.gwtsafe.Vector3D;





public class RollingMotion 
{

	public static boolean isState(Event e)
	{
		return (e.vel.add(UtilVector3D.crossUp(e.angularVel)).getNorm() < Ball.equilibriumTolerance);
	}

	/**
	 * When rolling the acceleration opposes roll, magnitude independent of
	 * speed / spin
	 * 
	 * @return acceleration vector when rolling
	 */
	public static Vector3D acceleration(Event e) 
	{
		try 
		{
			return e.vel.normalize().scalarMultiply(Ball.accelRoll);
		} 
		catch (ArithmeticException ae) 
		{
			return Vector3D.ZERO;
		}
	}
	
	
	public static Vector3D angularAcceleration(Event e) {
		return UtilVector3D.crossUp(acceleration(e)).scalarMultiply(1.0 * Ball.R);
	}
	/**
	 * When a ball is rolling the next state it will achieve is stationary
	 * 
	 * @return event when stationary
	 */
	public static Event next(Event e) 
	{
		Event stationary = e.advanceDelta(timeToNext(e));
		stationary.state = State.Stationary;
		stationary.type = EventType.FinishedRoll;
		return stationary;
	}
	
	
	/**
	 * When a ball is rolling the time it takes to stop can be found from
	 * simple equation of motion
	 * 
	 * solve v = v0 + a * t when v = 0
	 * where acceleration is rolling friction
	 * gives t = -v0/a
	 * 
	 * @return
	 */
	private static double timeToNext(Event e) 
	{
		return -e.vel.getNorm() / Ball.accelRoll;
	}
}
