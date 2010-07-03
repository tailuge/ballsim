package ballsim.org;

import org.apache.commons.math.geometry.Vector3D;

public class Utilities {

	// get a rolling ball, only used in testing
	public static Event getRolling(Vector3D vel)
	{
		Event e = Event.getSimpleEvent();
		e.state = State.Rolling;
		e.vel = vel;
		e.angularVel = Vector3D.crossProduct(vel.scalarMultiply(1.0/Ball.R),Vector3D.PLUS_K);
		return e;
	}
}
