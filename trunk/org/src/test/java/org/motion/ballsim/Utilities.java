package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.Event;
import org.motion.ballsim.State;

public class Utilities {

	public static Event getSimpleEvent() 
	{
		return new Event(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO,
				Vector3D.ZERO, Vector3D.ZERO, State.Unknown, 0,
				EventType.InitialHit);
	}
	
	public static Event getRolling(Vector3D vel)
	{
		Event e = getSimpleEvent();
		e.state = State.Rolling;
		e.vel = vel;
		e.angularVel = UtilVector3D.crossUp(vel.scalarMultiply(1.0/Ball.R));
		return e;
	}

	public static Event getSliding(Vector3D vel, Vector3D angularVel)
	{
		Event e = getSimpleEvent();
		e.state = State.Sliding;
		e.vel = vel;
		e.angularVel = angularVel;
		return e;
	}

	public static Event getStationary()
	{
		Event e = getSimpleEvent();
		e.state = State.Stationary;
		return e;
	}

	public static Event getStationary(Vector3D pos)
	{
		Event e = getSimpleEvent();
		e.state = State.Stationary;
		e.pos = pos;
		return e;
	}

}
