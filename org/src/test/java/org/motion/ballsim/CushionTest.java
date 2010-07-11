package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.math.geometry.Vector3D;
import org.apache.commons.math.util.MathUtils;
import org.junit.Test;
import org.motion.ballsim.Ball;
import org.motion.ballsim.Cushion;
import org.motion.ballsim.Event;

public class CushionTest {


	@Test
	public final void testXCollisionsPos() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		Event stat = e.next();
		
		Event c1 = Cushion.xCollisionsWith(e, stat.pos.getX() / 3.0, stat.t);
		
		System.out.println("stat.t  :"+stat.t);
		System.out.println("stat.x  :"+stat.pos.getX());
		System.out.println("stat.x/3:"+(stat.pos.getX()/3.0));
		
		assertNotNull("Collides",c1);

		Event c2 = Cushion.xCollisionsWith(e, stat.pos.getX() / 2.0, stat.t);

		assertTrue("Further collides later",c2.t > c1.t);	
		
		System.out.println(e);
		System.out.println(c1);
		System.out.println(e.next());

		assertTrue("Collision before ball stops",c1.t < e.next().t);
		assertEquals("Collision should be at cushion position",stat.pos.getX() / 3.0,c1.pos.getX(),0.001);
		assertTrue("Velocity reflected",c1.vel.getX() < 0);
		assertTrue("Velocity reflected",c2.vel.getX() < 0);
	}

	@Test
	public final void testXCollisionsNeg() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(-100));
		
		Event c1 = Cushion.xCollisionsWith(e, -2*Ball.R, Double.MAX_VALUE);
		Event c2 = Cushion.xCollisionsWith(e, -3*Ball.R, Double.MAX_VALUE);
		
		assertTrue("Collides",c1.t > 0);
		assertTrue("Further collides later",c2.t > c1.t);		
	}

	@Test
	public final void testYCollisionsPos() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_J.scalarMultiply(100));
		
		Event c1 = Cushion.yCollisionsWith(e, 2*Ball.R, Double.MAX_VALUE);
		Event c2 = Cushion.yCollisionsWith(e, 3*Ball.R, Double.MAX_VALUE);
		
		assertTrue("Collides",c1.t > 0);
		assertTrue("Further collides later",c2.t > c1.t);		
	}

	@Test
	public final void testYCollisionsNeg() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_J.scalarMultiply(-100));
		
		Event c1 = Cushion.yCollisionsWith(e, -2*Ball.R, Double.MAX_VALUE);
		Event c2 = Cushion.yCollisionsWith(e, -3*Ball.R, Double.MAX_VALUE);
		
		assertTrue("Collides",c1.t > 0);
		assertTrue("Further collides later",c2.t > c1.t);		
	}

	@Test
	public final void testXCollisionsNone() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_J.scalarMultiply(100));		
		Event c1 = Cushion.xCollisionsWith(e, 2*Ball.R, Double.MAX_VALUE);	
		assertNull("No collision parallel to cushion",c1);	
	}

	@Test
	public final void testYCollisionsNone() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(100));		
		Event c1 = Cushion.yCollisionsWith(e, 2*Ball.R, Double.MAX_VALUE);	
		assertNull("No collision parallel to cushion",c1);	
	}

	@Test
	public final void testManyCollisions() 
	{
		// generate large number of cushion hits and assert
		// ball always still on table
		
		int far=0,close=0;
		double v=0;
		while(v<1000)
		{
			Event e = Utilities.getRolling(new Vector3D(v,10,0));
			Event s = e.next();

			// choose distance very close to halting point
			double farx = s.pos.getX()-MathUtils.SAFE_MIN;

			// choose distance very close to start
			double closex = e.pos.getX()+0.00001;
			
			Event c1 = Cushion.xCollisionsWith(e, farx, s.t);
			Event c2 = Cushion.xCollisionsWith(e, closex, s.t);
			if(c1 != null)
			{
				String state="v:"+v+" c1.x:"+c1.pos.getX()+" farx:"+farx;
				assertTrue("At collision, must be on table:"+state,farx-c1.pos.getX() > 0);
				far++;
			}
			if(c2 != null)
			{
				String state="v:"+v+" c2.x:"+c2.pos.getX()+" closex:"+closex;
				assertTrue("At collision, must be on table:"+state,closex-c2.pos.getX() > 0);
				close++;
			}
			
			v++;
		}
		System.out.println("far:"+far+"close:"+close);
	}
	
	@Test
	public final void testHitsCushion() 
	{
		Event e = Utilities.getRolling(new Vector3D(2,1,0).scalarMultiply(30));		
		Event s = e.next();
		Event c1 = Cushion.getNext(e, s.t);
		
		System.out.println(e);
		System.out.println(s);
		System.out.println(c1);
		
		assertNotNull("No collision parallel to cushion",c1);	
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
