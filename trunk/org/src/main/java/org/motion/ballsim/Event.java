package org.motion.ballsim;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.math.geometry.Vector3D;
import org.apache.commons.math.geometry.Vector3DFormat;

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
public class Event {


	/*
	 * Main characteristics of an event
	 */
	
	public Vector3D pos;
	public Vector3D vel;
	public Vector3D angularPos;
	public Vector3D angularVel;
	public Vector3D spin;
	public Vector3D sidespin;
	public State state;
	public double t;
	public EventType type;
	public Ball ball;
	
	public Event(Vector3D pos, Vector3D vel, Vector3D angularPos,
			Vector3D angularVel, Vector3D sidespin, State state, double t,
			EventType type) {
		this.pos = pos;
		this.vel = vel;
		this.angularPos = angularPos;
		this.angularVel = angularVel;
		this.sidespin = sidespin;
		this.state = state;
		this.t = t;
		this.type = type;
	}

	public Event(Vector3D pos, Vector3D vel, Vector3D angularPos,
			Vector3D angularVel, Vector3D sidespin, State state, double t,
			EventType type, Ball ball) {
		this.pos = pos;
		this.vel = vel;
		this.angularPos = angularPos;
		this.angularVel = angularVel;
		this.sidespin = sidespin;
		this.state = state;
		this.t = t;
		this.type = type;
		this.ball = ball;
	}

	public Event(Event e) {
		pos = e.pos;
		vel = e.vel;
		angularPos = e.angularPos;
		angularVel = e.angularVel;
		spin = e.spin;
		sidespin = e.sidespin;
		state = e.state;
		t = e.t;
		type = e.type;
		ball = e.ball;		
	}



	/**
	 * Acceleration magnitude is always independent speed / spin
	 * but depends on state so we delegate.
	 * 
	 * @return acceleration vector depending on state
	 */
	public Vector3D acceleration() {
		return state.acceleration(this);
	}

	public Vector3D angularAcceleration() {
		return state.angularAcceleration(this);
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

	/**
	 * Produces an event interpolated delta seconds into the future
	 * 
	 * @param seconds
	 *            to advance event
	 * @return Event t seconds into the future
	 */
	public Event advanceDelta(double delta) {
		Event result = new Event(this);

		// v = v0 + a * t

		result.vel = vel.add(acceleration().scalarMultiply(delta));

		// p = p0 + v0*t + a*t*t/2

		result.pos = pos.add(vel.scalarMultiply(delta)).add(
				acceleration().scalarMultiply(delta * delta / 2.0));

		// av = av0 + aa * t

		result.angularVel = angularVel.add(angularAcceleration()
				.scalarMultiply(delta));

		// ap = ap0 + av0*t + aa*t*t/2

		result.angularPos = angularPos.add(angularVel.scalarMultiply(delta))
				.add(angularAcceleration().scalarMultiply(
						delta * delta / 2.0));

		// advance time

		result.t = t + delta;
		result.type = EventType.Interpolated;

		return result;
	}



	


	
	private static final DecimalFormat SECONDS_FORMAT = new DecimalFormat("0.00");

	public String toString() {
		return state+" t:" + SECONDS_FORMAT.format(t) + " "+type 
		+ " p:" + pos + " v:" + vel
				+ " ap:" + angularPos + " av:" + angularVel;
	}
	
	public String format()
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setRoundingMode(RoundingMode.UNNECESSARY);
		nf.setMinimumFractionDigits(17);
		Vector3DFormat f = new Vector3DFormat(nf);
		return state+" t:" + t + " "+type 
		+ " p:" + f.format(pos) + " v:" + f.format(vel)+ " av:" + f.format(angularVel);
		
	}
}
