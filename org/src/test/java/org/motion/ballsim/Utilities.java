package org.motion.ballsim;


import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.EventType;
import org.motion.ballsim.motion.State;
import org.motion.ballsim.util.UtilVector3D;

public class Utilities {

	public static Event getSimpleEvent() 
	{
		return new Event(Vector3D.ZERO, Vector3D.ZERO, Vector3D.PLUS_K,Vector3D.PLUS_J,
				Vector3D.ZERO, Vector3D.ZERO, State.Stationary, 0,
				EventType.InitialHit,0,0);
	}
	
	public static Event getRolling(Vector3D vel)
	{
		Event e = getSimpleEvent();
		e.state = State.Rolling;
		e.vel = vel;
		e.angularVel = UtilVector3D.crossUp(vel.scalarMultiply(1.0/Ball.R));
		return e;
	}

	public static Event getRollingWithSideSpin(Vector3D vel, double spin)
	{
		Event e = getSimpleEvent();
		e.state = State.Rolling;
		e.vel = vel;
		e.angularVel = UtilVector3D.crossUp(vel.scalarMultiply(1.0/Ball.R));
		e.sidespin = Vector3D.PLUS_K.scalarMultiply(spin);
		return e;
	}

	public static Event getRolling(Vector3D vel,Vector3D pos)
	{
		Event e = getRolling(vel);
		e.pos = pos;
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

	public static Event getSlidingWithSideSpin(Vector3D vel, Vector3D angularVel, double spin)
	{
		Event e = getSimpleEvent();
		e.state = State.Sliding;
		e.vel = vel;
		e.angularVel = angularVel;
		e.sidespin = Vector3D.PLUS_K.scalarMultiply(spin);
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
