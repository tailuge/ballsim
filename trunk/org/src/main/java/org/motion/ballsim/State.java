package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public enum State {

	Stationary 
	{
		@Override
		public Vector3D acceleration(Event event) 
		{
			return Vector3D.ZERO;
		}
		public Event next(Event event) 
		{
			return event;
		}

	},
	Sliding 
	{
		public Vector3D acceleration(Event e) 
		{
			return SlidingMotion.acceleration(e);
		}

		public Event next(Event event) 
		{
			return SlidingMotion.next(event);
		}

	},
	Rolling {
		public Vector3D acceleration(Event event) 
		{
			return RollingMotion.acceleration(event);
		}

		public Event next(Event event) 
		{
			return RollingMotion.next(event);
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
	public static State deriveStateOf(Event event) 
	{
		if ((event.vel.getNorm() < Ball.stationaryTolerance)
				&& (event.angularVel.getNorm() < Ball.stationaryAngularTolerance))
			return State.Stationary;

		if (RollingMotion.isState(event))
			return State.Rolling;

		return State.Sliding;
	}

}
