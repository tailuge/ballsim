package org.motion.ballsim;

import java.text.DecimalFormat;

import org.apache.commons.math.geometry.Vector3D;

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

	private static final DecimalFormat TWO_DP_FORMAT = new DecimalFormat("0.00");

	public Vector3D pos;
	public Vector3D vel;
	public Vector3D angularPos;
	public Vector3D angularVel;
	public Vector3D spin;
	public Vector3D sidespin;
	public State state;
	public double t;
	public EventType type;

	private Event(Vector3D pos, Vector3D vel, Vector3D angularPos,
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
	}

	public static Event getSimpleEvent() {
		return new Event(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO,
				Vector3D.ZERO, Vector3D.ZERO, State.Unknown, 0,
				EventType.InitialHit);
	}

	/**
	 * Acceleration magnitude is always independent speed / spin.
	 * 
	 * @return acceleration vector depending on state
	 */
	public Vector3D getAccelerationVector() {
		return state.accelerate(this);
	}

	public Vector3D getAngularAccelerationVector() {
		// todo
		double scale = state == State.Sliding ? -5.0 / 2.0 : 1.0;

		return UtilVector3D.crossUp(getAccelerationVector()).scalarMultiply(
				scale * Ball.R);
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

		result.vel = vel.add(getAccelerationVector().scalarMultiply(delta));

		// p = p0 + v0*t + a*t*t/2

		result.pos = pos.add(vel.scalarMultiply(delta)).add(
				getAccelerationVector().scalarMultiply(delta * delta / 2.0));

		// av = av0 + aa * t

		result.angularVel = angularVel.add(getAngularAccelerationVector()
				.scalarMultiply(delta));

		// ap = ap0 + av0*t + aa*t*t/2

		result.angularPos = angularPos.add(angularVel.scalarMultiply(delta))
				.add(getAngularAccelerationVector().scalarMultiply(
						delta * delta / 2.0));

		// advance time

		result.t = t + delta;
		result.type = EventType.Interpolated;

		return result;
	}

	public double timeToStopRolling() {

		// solve v = v0 + a * t when v = 0
		// where acceleration is rolling friction
		// gives t = -v0/a

		return -vel.getNorm() / Ball.accelRoll;
	}

	public double timeToNaturalRollEquilibrium() {

		// notes:
		// Vnr = V0*5/7 +Rw0*2/7
		// acc = diff in v / t
		// t = diff in v / acc
		// |acc| for sliding ball is always same. fslide

		double timeToNr = getChangeToNr().getNorm() / -Ball.accelSlide;

		return timeToNr;
	}

	// private but for unit test
	public Vector3D getChangeToNr() {
		Vector3D nr = vel.scalarMultiply(5.0 / 7.0).add(
				UtilVector3D.crossUp(angularVel).scalarMultiply(
						-Ball.R * 2.0 / 7.0));
		Vector3D changeInV = nr.subtract(vel);
		return changeInV;
	}

	public Event stationaryEventFromRolling() {
		return state.stop(this);
	}

	public Event rollingEventFromSliding() {
		return state.roll(this);
	}

	public State infereState() {
		return State.deriveStateOf(this);
	}

	public String toString() {
		return "t:" + TWO_DP_FORMAT.format(t) + " p:" + pos + " v:" + vel
				+ " ap:" + angularPos + " av:" + angularVel + " s:" + state;
	}
}
