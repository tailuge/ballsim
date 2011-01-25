package org.motion.ballsim.motion;

import org.motion.ballsim.gwtsafe.Vector3D;



public enum State {


	Stationary 
	{
		@Override
		public Vector3D acceleration(Event e) 
		{
			return Vector3D.ZERO;
		}
		
		@Override
		public Vector3D angularAcceleration(Event e) 
		{
			return Vector3D.ZERO;
		}

		@Override
		public Vector3D sidespinAcceleration(Event e) 
		{
			return Vector3D.ZERO;
		}

		@Override
		public Event next(Event e) 
		{
			return e;
		}

	},
	Sliding 
	{
		@Override
		public Vector3D acceleration(Event e) 
		{
			return SlidingMotion.acceleration(e);
		}
		
		@Override
		public Vector3D angularAcceleration(Event e) 
		{
			return SlidingMotion.angularAcceleration(e);
		}

		@Override
		public Vector3D sidespinAcceleration(Event e) 
		{
			return SlidingMotion.sidespinAcceleration(e);
		}

		@Override
		public Event next(Event e) 
		{
			return SlidingMotion.next(e);
		}

	},
	Rolling {
		@Override
		public Vector3D acceleration(Event e) 
		{
			return RollingMotion.acceleration(e);
		}

		@Override
		public Vector3D angularAcceleration(Event e) 
		{
			return RollingMotion.angularAcceleration(e);
		}

		@Override
		public Vector3D sidespinAcceleration(Event e) 
		{
			return RollingMotion.sidespinAcceleration(e);
		}

		@Override
		public Event next(Event e) 
		{
			return RollingMotion.next(e);
		}

	};


	
	public abstract Vector3D acceleration(Event e);
	public abstract Vector3D angularAcceleration(Event e);
	public abstract Vector3D sidespinAcceleration(Event e);
	public abstract Event next(Event e);



	/**
	 * Given an event on indeterminate state compare 
	 * velocity and angular velocity to determine if 
	 * its sliding, rolling or stationary.
	 * 
	 * @param event
	 * @return
	 */
	public static State deriveStateOf(Event e) 
	{
		if ((e.vel.getNorm() < Ball.stationaryTolerance)
				&& (e.angularVel.getNorm() < Ball.stationaryAngularTolerance))
			return State.Stationary;

		if (RollingMotion.isState(e))
			return State.Rolling;

		return State.Sliding;
	}

}
