package org.motion.ballsim.physics.util;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.ball.Transition;
import org.motion.ballsim.util.UtilVector3D;

public class Events {

	public static Event first(Event current, Event proposed) {
		if ((proposed == null) || (proposed.t < 0))
			return current;
		if (current == null)
			return proposed;
		if (proposed.t < current.t)
			return proposed;

		return current;
	}
	
	
	/**
	 * Given a ball at collision point determine its new state after reflection
	 * in the cushion
	 * 
	 * @param e
	 * @param axis
	 * @return
	 */
	public static Event reflect(Event e, Vector3D axis) {
		Event reflected = new Event(e);
		reflected.vel = UtilVector3D.reflectAlongAxis(e.vel, axis);
		reflected.angularVel = e.angularVel.scalarMultiply(0.4); // until done
		reflected.state = State.deriveStateOf(reflected);
		reflected.type = Transition.Cushion;

		// Vector3D sideSpinAffectOnVel =
		// Vector3D.crossProduct(axis,e.sidespin);

		return reflected;
	}

}
