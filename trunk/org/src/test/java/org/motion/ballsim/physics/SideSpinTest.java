package org.motion.ballsim.physics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.gwtsafe.Vector3D;


public class SideSpinTest {

	
	@Test
	public final void testXCollisionsNoSpin() 
	{
		// place near cushion
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(-50));
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xn+Ball.R);
		Event c1 = Cushion.hit(e, e.next().t,false);
		assertNotNull("Collides",c1);
		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",Cushion.xn,c1.pos.getX(),0.001);
		assertTrue("Velocity reflected",c1.vel.getX() > 0);
	}

	@Ignore
	@Test
	public final void testXCollisionsPositiveSpin() 
	{
		// place near cushion
		Event e = Utilities.getRollingWithSideSpin(Vector3D.PLUS_I.scalarMultiply(-50),1);
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xn+Ball.R);
		Event c1 = Cushion.hit(e, e.next().t,false);
		assertNotNull("Collides",c1);
		assertTrue("Perpendicular velocity is changed",c1.vel.getY() > 0);
	}

	
	@Ignore
	@Test
	public final void testXCollisionsNegativeSpin() 
	{
		// place near cushion
		Event e = Utilities.getRollingWithSideSpin(Vector3D.PLUS_I.scalarMultiply(-50),-1);
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xn+Ball.R);
		Event c1 = Cushion.hit(e, e.next().t,false);
		assertNotNull("Collides",c1);
		assertTrue("Perpendicular velocity is changed",c1.vel.getY() > 0);
	}

}
