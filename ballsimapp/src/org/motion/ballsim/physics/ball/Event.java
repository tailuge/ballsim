package org.motion.ballsim.physics.ball;

import org.motion.ballsim.physics.gwtsafe.Rotation;
import org.motion.ballsim.physics.gwtsafe.Vector3D;



/**
 * @author luke
 * 
 *         Primary concept in simulation, an Event represents the transition
 *         between states of a ball. It captures initial conditions at time of
 *         event, and State (Rolling,Sliding,Stationary)
 * 
 *         Events can evolve naturally (Sliding->Rolling, Rolling->Stationary)
 *         Events can evolve due to external agents (Ball is hit, Balls collide,
 *         Ball hits cushion)
 * 
 *         Events are only recorded at transitions, Intermediate states can be
 *         interpolated using simple equations of motion.
 * 
 */
public final class Event {

	/*
	 * Main characteristics of an event
	 */
		
	public Vector3D pos;
	public Vector3D vel;
	public Vector3D angularPos;
	public Vector3D angularPosPerp;
	public Vector3D angularVel;
	public Vector3D spin;
	public Vector3D sidespin;
	public State state;
	public double t;
	public Transition type;
	public int ballId;
	public int otherBallId;
	
	public Event(Vector3D pos, Vector3D vel, 
			Vector3D angularPos, Vector3D angularPosPerp,
			Vector3D angularVel, Vector3D sidespin, State state, double t,
			Transition type, int ballId, int otherBallId) {
		this.pos = pos;
		this.vel = vel;
		this.angularPos = angularPos;
		this.angularPosPerp = angularPosPerp;
		this.angularVel = angularVel;
		this.sidespin = sidespin;
		this.state = state;
		this.t = t;
		this.type = type;
		this.ballId = ballId;
		this.otherBallId = otherBallId;
	}



	public Event(Event e) {
		pos = e.pos;
		vel = e.vel;
		angularPos = e.angularPos;
		angularPosPerp = e.angularPosPerp;
		angularVel = e.angularVel;
		spin = e.spin;
		sidespin = e.sidespin;
		state = e.state;
		t = e.t;
		type = e.type;
		ballId = e.ballId;
		otherBallId = e.otherBallId;
	}


	private Vector3D acceleration;
	private Vector3D angularAcceleration;
	private Vector3D sidespinAcceleration;
	
	/**
	 * Acceleration magnitude is always independent speed / spin
	 * but depends on state so we delegate.
	 * 
	 * @return acceleration vector depending on state
	 */
	public Vector3D acceleration() {
		if (acceleration == null)
			acceleration = state.acceleration(this);
		return acceleration;
	}

	public Vector3D angularAcceleration() {
		if (angularAcceleration == null)
			angularAcceleration = state.angularAcceleration(this);
		return angularAcceleration;
	}

	public Vector3D sidespinAcceleration() {
		if (sidespinAcceleration == null)
			sidespinAcceleration = state.sidespinAcceleration(this);
		return sidespinAcceleration;
	}

	/**
	 * Will determine the next 'natural' event from this event
	 * i.e sliding->rolling->stationary.
	 * @return 
	 */
	public Event next()
	{
		return state.next(this);
	}

	public double timeToNext()
	{
		return state.next(this).t - t;
	}

	public Vector3D advanceDeltaPosition(double delta)
	{
		return pos.addScaledPair(delta,vel,delta * delta / 2.0,acceleration());
	}
	
	/**
	 * Produces an event interpolated delta seconds into the future
	 * 
	 * @param seconds
	 *            to advance event
	 * @return Event t seconds into the future
	 */
	public Event advanceDelta(double delta) 
	{

		if (state.isMotionlessEndState())
			return stationaryAdvanceDelta(delta);
		
		// v = v0 + a * t

		Vector3D vel_ = vel.add(delta,acceleration());

		// p = p0 + v0*t + a*t*t/2

		Vector3D pos_ = advanceDeltaPosition(delta);//pos.add(delta,vel).add(delta * delta / 2.0,acceleration());

		// av = av0 + aa * t

		Vector3D angularVel_ = angularVel.add(delta,angularAcceleration());

		// ap = ap0 + av0*t + aa*t*t/2

		Rotation matrix = BallSpot.rotationMatrixTo(angularVel,angularAcceleration(),delta);
		Vector3D angularPos_ = BallSpot.progressTo(matrix,angularPos);
		Vector3D angularPosPerp_ = BallSpot.progressTo(matrix,angularPosPerp);

		// ss = ss0 + sa * t
		
		Vector3D sidespin_ = sidespin.add(delta,sidespinAcceleration());
		
		return new Event(pos_,vel_,angularPos_,angularPosPerp_,angularVel_,sidespin_,state,t+delta,Transition.Interpolated,ballId,otherBallId);
	}

	private Event stationaryAdvanceDelta(double delta)
	{
		return new Event(pos,vel,angularPos,angularPosPerp,angularVel,sidespin,state,t+delta,Transition.Interpolated,ballId,otherBallId);
	}
	
	public String toString() {
		return state+" t:" + t + " "+type 
		+ " p:" + pos + " v:" + vel
				+ " ap:" + angularPos + " av:" + angularVel;
	}
	
	public String format()
	{
		return state+" t:" + t + " "+type 
		+ " p:" + pos + " v:" +vel+ " av:" + angularVel;
		
	}
}
