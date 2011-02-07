package org.motion.ballsim;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Cushion;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.Pocket;


public class PocketTest {

	
	@Test
	public final void testPocketCollision() 
	{
		// roll toward pocket
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xp-Ball.R);
		Event c1 = Pocket.hits(e, Pocket.p1k1, Pocket.p1k2, Pocket.p1k1, 1, e.next().t);
		assertNull("Collides",c1);
		
		System.out.println(e);
		System.out.println(c1);
		System.out.println(e.next());
	}
}
