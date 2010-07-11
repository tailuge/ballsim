package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Test;

public class CushionTest {


	@Test
	public final void testXCollisionsPos() 
	{
		// place near cushion
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xp-Ball.R);
		Event c1 = Cushion.getNext(e, e.next().t);
		assertNotNull("Collides",c1);
		
		System.out.println(e);
		System.out.println(c1);
		System.out.println(e.next());

		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",Cushion.xp,c1.pos.getX(),0.001);
		assertTrue("Velocity reflected",c1.vel.getX() < 0);
	}

	@Test
	public final void testXCollisionsNeg() 
	{
		// place near cushion
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(-50));
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xn+Ball.R);
		Event c1 = Cushion.getNext(e, e.next().t);
		assertNotNull("Collides",c1);
		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",Cushion.xn,c1.pos.getX(),0.001);
		assertTrue("Velocity reflected",c1.vel.getX() > 0);
	}

	@Test
	public final void testYCollisionsPos() 
	{
		// place near cushion
		Event e = Utilities.getRolling(Vector3D.PLUS_J.scalarMultiply(50));
		e.pos = Vector3D.PLUS_J.scalarMultiply(Cushion.yp-Ball.R);
		Event c1 = Cushion.getNext(e, e.next().t);
		assertNotNull("Collides",c1);
		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",Cushion.yp,c1.pos.getY(),0.001);
		assertTrue("Velocity reflected",c1.vel.getY() < 0);
	}

	@Test
	public final void testYCollisionsNeg() 
	{
		// place near cushion
		Event e = Utilities.getRolling(Vector3D.PLUS_J.scalarMultiply(-50));
		e.pos = Vector3D.PLUS_J.scalarMultiply(Cushion.yn+Ball.R);
		Event c1 = Cushion.getNext(e, e.next().t);
		assertNotNull("Collides",c1);
		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",Cushion.yn,c1.pos.getY(),0.001);
		assertTrue("Velocity reflected",c1.vel.getY() > 0);

	}

	@Test
	public final void testCollisionsNone() 
	{
		Event e = Utilities.getStationary();	
		Event c1 = Cushion.getNext(e, Double.MAX_VALUE);	
		assertNull("No collision parallel to cushion",c1);	
	}

	
	@Test
	public final void testHitsCushion() 
	{
		Event e = Utilities.getRolling(new Vector3D(2,1,0).scalarMultiply(30));		
		Event s = e.next();
		Event c1 = Cushion.getNext(e, s.t);
		assertNotNull("Hits cushion",c1);	
		assertTrue("Collision befor stop",c1.t<s.t);	
	}
	
	
	@Test
	public final void testManyTableCollisions() 
	{
		// generate large number of cushion hits and assert
		// ball always still on table
		
		double v=0;
		while(v<1000)
		{
			Event e = Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(500),Vector3D.PLUS_I);
			Event s = e.next();
			if (Cushion.onTable(s.next()))
				continue;
			
			Event c = Cushion.getNext(e, s.t);
			assertNotNull("Collision before leaving table",c);
			v++;
		}
	}
}
