package org.motion.ballsim;

import static org.junit.Assert.*;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Ignore;
import org.junit.Test;

public class CollisionTest {

	@Test
	@Ignore("Needs fixing")
	public final void testCollisionTime() 
	{
		Event e1 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(130));
		Event e2 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(-130));
		e2.pos = Vector3D.PLUS_I.scalarMultiply(Ball.R*4);
		double t = Collision.collisionTime(e1, e2);
		EventPair cols = Collision.collisionEvents(e1,e2, t);
		assertTrue("Rolling towards each other collide",t>0);
		System.out.println(Collision.startingSeperation(cols.getFirst(), cols.getSecond()));
		assertTrue("Balls seperated",Collision.startingSeperation(cols.getFirst(), cols.getSecond()) > 0);
	}

}
