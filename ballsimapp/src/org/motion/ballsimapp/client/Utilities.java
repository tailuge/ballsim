package org.motion.ballsimapp.client;

import org.motion.ballsimapp.gwtsafe.Vector3D;
import org.motion.ballsimapp.logic.Ball;
import org.motion.ballsimapp.logic.Event;
import org.motion.ballsimapp.logic.EventType;
import org.motion.ballsimapp.logic.State;
import org.motion.ballsimapp.logic.UtilVector3D;


public class Utilities {

	public static Event getSimpleEvent() 
	{
		return new Event(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO,
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
