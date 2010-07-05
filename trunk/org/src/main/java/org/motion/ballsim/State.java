package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public enum State {
	
	Stationary, Sliding, Rolling, Unknown;

	public Vector3D acceleration(Event e)
	{
		switch (this)
		{
		case Rolling:
			return e.getRollingAccelerationVector();
		case Sliding:
			return e.getSlidingAccelerationVector();
		default:
			return Vector3D.ZERO;
		}
	}
}
