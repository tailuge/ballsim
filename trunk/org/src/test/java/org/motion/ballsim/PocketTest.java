package org.motion.ballsim;

import static org.junit.Assert.*;
import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.EventType;
import org.motion.ballsim.motion.Pocket;
import org.motion.ballsim.motion.State;


public class PocketTest {

	
	@Test
	public final void testPocketKnuckle() 
	{
		// roll to middle right pocket knuckle
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = Pocket.p6k1.add(new Vector3D(-3*Ball.R,+0.2,0));
		Event c1 = Pocket.nextKnuckleCollision(e, e.next().t);

		assertNotNull("Collides",c1);
		assertTrue("event is after time zero",c1.t > 0);
		assertTrue("velocity is reflected",c1.vel.getX() < 0);
		assertTrue("velocity is reflected partially downward",c1.vel.getY() > 0);
		assertTrue("ball is sliding",c1.state == State.Sliding);
		assertTrue("event type is knuckle collision",c1.type == EventType.KnuckleCushion);
		
		System.out.println(e);
		System.out.println(c1);
		
	}
	
	@Test
	public final void testPocketPot() 
	{
		// roll to middle right pocket knuckle
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = Pocket.p6.add(new Vector3D(-6*Ball.R,+0.2,0));
		Event c1 = Pocket.nextPot(e, e.next().t);

		assertNotNull("Collides",c1);
		assertTrue("event is after time zero",c1.t > 0);
		assertTrue("ball is potted",c1.state == State.FallingInPocket);
		assertTrue("event type is knuckle collision",c1.type == EventType.Potting);
		
		System.out.println(e);
		System.out.println(c1);
		
	}
	
	@Test
	public final void testPocketThenOtherCushion() 
	{
		// start in pocket beyond cushion, but still on table, fire outwards.
		// TODO
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(250));
		e.pos = Pocket.p6k1.add(new Vector3D(-3*Ball.R,+0.2,0));
		Event c1 = Pocket.nextKnuckleCollision(e, e.next().t);

		assertNotNull("Collides",c1);
		assertTrue("event is after time zero",c1.t > 0);
		assertTrue("velocity is reflected",c1.vel.getX() < 0);
		assertTrue("velocity is reflected partially downward",c1.vel.getY() > 0);
		assertTrue("ball is sliding",c1.state == State.Sliding);
		assertTrue("event type is knuckle collision",c1.type == EventType.KnuckleCushion);
		
		System.out.println(e);
		System.out.println(c1);
		
	}

}
