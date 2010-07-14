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
		public Vector3D acceleration(Event event) 
		{
			return RollingPhysics.acceleration(event);
		}

		public Event next(Event event) 
		{
			return RollingPhysics.next(event);
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

		if (RollingPhysics.isState(event))
			return State.Rolling;

		return State.Sliding;
	}

}
