package ballsim.org;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Test;

public class CushionTest {


	@Test
	public final void testXCollisionsPos() 
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(100));
		
		Event c1 = Cushion.xCollisionsWith(e, 2.0*Ball.R, Double.MAX_VALUE);
		Event c2 = Cushion.xCollisionsWith(e, 3.0*Ball.R, Double.MAX_VALUE);
		
		assertTrue("Collides",c1.t > 0);
		assertTrue("Further collides later",c2.t > c1.t);	
		
		System.out.println(e);
		System.out.println(c1);
		System.out.println(e.stationaryEventFromRolling());

		assertTrue("Collision before ball stops",c1.t < e.stationaryEventFromRolling().t);

		assertEquals("Collision should be at cushion position",2.0*Ball.R,c1.pos.getX(),0.001);
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

}
