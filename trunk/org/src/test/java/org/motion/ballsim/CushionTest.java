package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.motion.ballsim.gwtsafe.Quadratic;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Cushion;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.State;
import org.motion.ballsim.util.UtilVector3D;

public class CushionTest {


	@Test
	public final void testXCollisionsPos() 
	{
		// place near cushion
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = Vector3D.PLUS_I.scalarMultiply(Cushion.xp-Ball.R);
		Event c1 = Cushion.hit(e, e.next().t);
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
		Event c1 = Cushion.hit(e, e.next().t);
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
		Event c1 = Cushion.hit(e, e.next().t);
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
		Event c1 = Cushion.hit(e, e.next().t);
		assertNotNull("Collides",c1);
		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",Cushion.yn,c1.pos.getY(),0.001);
		assertTrue("Velocity reflected",c1.vel.getY() > 0);

	}

	@Test
	public final void testCollisionsNone() 
	{
		Event e = Utilities.getStationary();	
		Event c1 = Cushion.hit(e, Double.MAX_VALUE);	
		assertNull("No collision parallel to cushion",c1);	
	}

	
	@Test
	public final void testHitsCushion() 
	{
		Event e = Utilities.getRolling(new Vector3D(2,1,0).scalarMultiply(30));		
		Event s = e.next();
		Event c1 = Cushion.hit(e, s.t);
		assertNotNull("Hits cushion",c1);	
		assertTrue("Collision befor stop",c1.t<s.t);	
	}

	@Test
	public final void testHitsCushionKnownIssue() 
	{
		//full format:Sliding t:0.503344196955985 Cushion p:{9.99999999999999800; 19.96879135072729000; 0.00000000000000000} v:{-34.87801312348303000; 69.74504537775142000; 0.00000000000000000} av:{69.74504537775140000; -34.87801312348303000; 0.00000000000000000}
		Event e = Utilities.getStationary();
		e.pos = new Vector3D(Cushion.xp*0.9999, Cushion.yp*0.9999, 0);
		e.vel = new Vector3D(-34.87801312348303000, 69.74504537775142000,0);
		e.angularVel = new Vector3D(69.74504537775140000, -34.87801312348303000,0);
		e.state = State.Sliding;
		Event s = e.next();
		Event c1 = Cushion.hit(e, s.t);
		assertNotNull("Hits cushion",c1);	
		assertTrue("Collision befor stop",c1.t<s.t);	
	}

	@Test
	public final void testInCorner() 
	{
		Event e = Utilities.getRolling(
				new Vector3D(1,1,0).scalarMultiply(30),
				new Vector3D(Quadratic.nextSmallest(Cushion.xp),Quadratic.nextSmallest(Cushion.yp),0));		
		Event s = e.next();
		Event c1 = Cushion.hit(e, s.t);
		assertNotNull("Hits cushion",c1);	
		assertTrue("On table",Cushion.onTable(c1));	
		c1 = Cushion.hit(c1, Double.MAX_VALUE);
		assertNotNull("Hits cushion",c1);	
		assertTrue("On table",Cushion.onTable(c1));	
		assertTrue("Collision befor stop",c1.t<s.t);	
	}

	@Test
	public final void testAwayFromCorner() 
	{
		Event e = Utilities.getRolling(
				new Vector3D(1,1,0).scalarMultiply(30),
				new Vector3D(Quadratic.nextSmallest(Cushion.xp),Quadratic.nextSmallest(Cushion.yp),0));		
		//e.vel = new Vector3D(-1,1,0).scalarMultiply(30);
		//e.t = 1;
		Event s = e.next();
		Event c1 = Cushion.hit(e, s.t);
		assertNotNull("Hits cushion",c1);	
		assertTrue("On table",Cushion.onTable(c1));	
		System.out.println("------------------------------------");
		c1 = Cushion.hit(c1, Double.MAX_VALUE);
		assertNotNull("Hits cushion",c1);	
		assertTrue("On table",Cushion.onTable(c1));	
		assertTrue("Collision befor stop",c1.t<s.t);	
	}

	@Test
	public final void testMoveAwayEdgeX() 
	{
		Event e = Utilities.getRolling(
				new Vector3D(-1,0,0).scalarMultiply(130),
				new Vector3D(Quadratic.nextSmallest(Cushion.xp),0,0));		
	
		Event c1 = Cushion.hit(e, Double.MAX_VALUE);
		assertNotNull("Hits cushion",c1);	
		assertTrue("On table",Cushion.onTable(c1));	
	}

	@Test
	public final void testMoveAwayEdgeY() 
	{
		Event e = Utilities.getRolling(
				new Vector3D(0,-1,0).scalarMultiply(130),
				new Vector3D(0,Quadratic.nextSmallest(Cushion.yp),0));		
	
		Event c1 = Cushion.hit(e, Double.MAX_VALUE);
		assertNotNull("Hits cushion",c1);	
		assertTrue("On table",Cushion.onTable(c1));	
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
			
			Event c = Cushion.hit(e, s.t);
			assertNotNull("Collision before leaving table",c);
			v++;
		}
	}
	
	@Test
	public final void testOnTable() 
	{
		assertTrue(Cushion.onTable(Utilities.getStationary()));
		assertTrue(!Cushion.onTable(Utilities.getStationary(new Vector3D(Cushion.xp,0,0))));
		assertTrue(!Cushion.onTable(Utilities.getStationary(new Vector3D(Cushion.xn,0,0))));
		assertTrue(!Cushion.onTable(Utilities.getStationary(new Vector3D(0,Cushion.yp,0))));
		assertTrue(!Cushion.onTable(Utilities.getStationary(new Vector3D(0,Cushion.yn,0))));
		assertTrue(Cushion.onTable(Utilities.getStationary(new Vector3D(Cushion.xp-Ball.R,0,0))));
	}	
}
