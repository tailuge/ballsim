package org.motion.ballsim.motion;

import org.motion.ballsim.gwtsafe.Vector3D;

public class PottingMotion {

	
	/**
	 * Ball should fall towards middle of pocket
	 * 
	 * 
	 * @return acceleration vector when falling into pocket
	 */
	public static Vector3D acceleration(Event e) 
	{
		try 
		{
			return e.vel.normalize();
		} 
		catch (ArithmeticException ae) 
		{
			return Vector3D.ZERO;
		}
	}
	
	
	private static double timeToFinishPotting(Event e) 
	{
		return 0.1;
	}
	
	
	/**
	 * When a ball is falling into pocket, next state is in pocket
	 * 
	 * @return event when off table
	 */
	public static Event next(Event e) 
	{
		Event offTable = e.advanceDelta(timeToFinishPotting(e));
		offTable.state = State.InPocket;
		offTable.type = EventType.Potted;
		return offTable;
	}

}
