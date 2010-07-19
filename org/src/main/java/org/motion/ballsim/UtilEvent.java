package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public class UtilEvent 
{

	private static final Vector3D zero = Vector3D.ZERO;

	public static Event hit(Vector3D pos, Vector3D dir, double speed, double cueHeight)
	{
		return new Event(
				pos,
				dir.scalarMultiply(speed),
				zero,
				zero,
				zero,
				State.Sliding,
				0,
				EventType.InitialHit
				);
	}
}
