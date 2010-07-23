package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public class UtilEvent 
{

	private static final Vector3D zero = Vector3D.ZERO;

	public static Event hit(Vector3D pos, Vector3D dir, double speed, double cueHeight)
	{
		Event e = new Event(
				pos,
				dir.scalarMultiply(speed),
				zero,
				UtilVector3D.crossUp(dir.scalarMultiply(speed/Ball.R)).scalarMultiply(cueHeight),
				zero,
				State.Sliding,
				0,
				EventType.InitialHit,
				0,
				0
				);
		e.state = State.deriveStateOf(e);
		return e;
	}
}
