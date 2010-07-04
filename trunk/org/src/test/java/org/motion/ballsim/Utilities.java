package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.Ball;
import org.motion.ballsim.Event;
import org.motion.ballsim.State;

public class Utilities {

	public static Event getRolling(Vector3D vel)
	{
		Event e = Event.getSimpleEvent();
		e.state = State.Rolling;
		e.vel = vel;
		e.angularVel = Vector3D.crossProduct(vel.scalarMultiply(1.0/Ball.R),Vector3D.PLUS_K);
		return e;
	}

	public static Event getSliding(Vector3D vel, Vector3D angularVel)
	{
		Event e = Event.getSimpleEvent();
		e.state = State.Sliding;
		e.vel = vel;
		e.angularVel = angularVel;
		return e;
	}

	public static Event getStationary()
	{
		Event e = Event.getSimpleEvent();
		e.state = State.Stationary;
		return e;
	}

}
