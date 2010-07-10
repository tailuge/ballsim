package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public enum State {

	Stationary {

		@Override
		public Vector3D acceleration(Event event) {
			return Vector3D.ZERO;
		}
		public Event next(Event event) {
			return event;
		}

	},
	Sliding {
		/**
		 * Acceleration is constant during sliding phase and works to
		 * reach rolling equilibrium. Magnitude independent of speed / spin
		 * 
		 * @return acceleration vector when sliding
		 */
		public Vector3D acceleration(Event e) {
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
		public Event next(Event event) {
			return roll(event);
		}

	},
	Rolling {
		/**
		 * When rolling acceleration opposes roll, magnitude independent of
		 * speed / spin
		 * 
		 * @return acceleration vector when rolling
		 */
		public Vector3D acceleration(Event event) {
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
		public Event next(Event event) {
			return stop(event);
		}

	},
	Unknown {
		public Vector3D acceleration(Event e) {
			return Vector3D.ZERO;
		}
		public Event next(Event event) {			
			return deriveStateOf(event).next(event);
		}

	};

	public abstract Vector3D acceleration(Event e);
	public abstract Event next(Event e);


	/**
	 * Given an event on indeterminate state compare 
	 * velocity and angular velocity to determine if 
	 * its sliding, rolling or stationary.
	 * 
	 * @param event
	 * @return
	 */
	public static State deriveStateOf(Event event) {

		if ((event.vel.getNorm() < Ball.stationaryTolerance)
				&& (event.angularVel.getNorm() < Ball.stationaryAngularTolerance))
			return State.Stationary;

		if (event.vel.add(UtilVector3D.crossUp(event.angularVel)).getNorm() < Ball.equilibriumTolerance)
			return State.Rolling;

		return State.Sliding;
	}

}
