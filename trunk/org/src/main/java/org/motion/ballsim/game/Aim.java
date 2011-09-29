package org.motion.ballsim.game;

import org.motion.ballsim.gwtsafe.Vector3D;

public class Aim {
	
	final public int ballId;
	final public Vector3D pos;
	final public Vector3D dir;
	final public Vector3D spin;
	final public double speed;
	
	public Aim(int ballId, Vector3D pos,Vector3D dir, Vector3D spin, double speed) {
		this.ballId = ballId;
		this.pos = pos;
		this.dir = dir;
		this.spin = spin;
		this.speed = speed;
	}

}
