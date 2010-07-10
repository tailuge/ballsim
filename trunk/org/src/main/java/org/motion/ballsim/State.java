package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public enum State {

	Stationary {

		@Override
		public Vector3D accelerate(Event event) {
			return Vector3D.ZERO;
		}

	},
	Sliding {
		/**
		 * when sliding acceleration provided by angularVel since a moving but
		 * non spinning ball would also experience acceleration its relative to
		 * motion. magnitude independent of speed / spin
		 */
		public Vector3D accelerate(Event e) {
			return e.getChangeToNr().normalize()
					.scalarMultiply(-Ball.accelSlide);
		}

		public Event roll(Event event) {
			Event rolling = event.advanceDelta(event
					.timeToNaturalRollEquilibrium());
			rolling.state = State.Rolling;
			rolling.type = EventType.RollEquilibrium;
			return rolling;
		}

	},
	Rolling {
		/**
		 * When rolling acceleration opposes roll, magnitude independent of
		 * speed / spin
		 * 
		 * @return acceleration vector when rolling
		 */
		public Vector3D accelerate(Event event) {
			try {
				return event.vel.normalize().scalarMultiply(Ball.accelRoll);
			} catch (ArithmeticException e) {
				return Vector3D.ZERO;
			}
		}

		public Event stop(Event event) {
			Event stationary = event.advanceDelta(event.timeToStopRolling());
			stationary.state = State.Stationary;
			stationary.type = EventType.Stationary;
			return stationary;
		}

	},
	Unknown {
		public Vector3D accelerate(Event e) {
			return Vector3D.ZERO;
		}

	};

	public abstract Vector3D accelerate(Event e);

	public Event roll(Event e) {
		return e;
	}

	public Event stop(Event e) {
		return e;
	}

	public static State deriveStateOf(Event event) {

		if ((event.vel.getNorm() < Ball.stationaryTolerance)
				&& (event.angularVel.getNorm() < Ball.stationaryAngularTolerance))
			return State.Stationary;

		if (event.vel.add(UtilVector3D.crossUp(event.angularVel)).getNorm() < Ball.equilibriumTolerance)
			return State.Rolling;

		return State.Sliding;
	}

}
