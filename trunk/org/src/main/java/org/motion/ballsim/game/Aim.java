package org.motion.ballsim.game;

import org.motion.ballsim.gwtsafe.Vector3D;

public class Aim {
	
	final public Vector3D dir;
	final public Vector3D spin;
	final public double speed;
	
	public Aim(Vector3D dir, Vector3D spin, double speed) {
		this.dir = dir;
		this.spin = spin;
		this.speed = speed;
	}

}
