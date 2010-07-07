package org.motion.ballsim;

import static org.junit.Assert.*;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Test;

public class CollisionTest {

	@Test
	public final void testCollisionTime() 
	{
		Event e1 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(130));
		Event e2 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(-130));
		e2.pos = Vector3D.PLUS_I.scalarMultiply(Ball.R*3);
		double t = Collision.collisionTime(e1, e2);
		assertTrue("Rolling towards each other collide",t>0);
	}

}
