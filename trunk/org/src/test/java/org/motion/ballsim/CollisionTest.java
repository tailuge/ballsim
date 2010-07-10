package org.motion.ballsim;

import static org.junit.Assert.assertTrue;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Ignore;
import org.junit.Test;

public class CollisionTest {

	@Test
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


	@Test
	@Ignore
	public final void testManyRolling() 
	{
		int i = 0;
		while(i < 100)
		{
			Event e1 = Utilities.getRolling(UtilVector3D.rnd().scalarMultiply(Ball.R*100*Math.random()));		
			Event e2 = Utilities.getStationary();
			e2.pos = e1.stationaryEventFromRolling().pos.add(UtilVector3D.rnd());
						
			if (Collision.startingSeperation(e1, e2) <= 0)
				continue;
			
			double t = Collision.collisionTime(e1, e2);

			if (t<=0 || t>e1.stationaryEventFromRolling().t)
				continue;
			
			EventPair cols = Collision.collisionEvents(e1,e2, t);
			System.out.println(Collision.startingSeperation(cols.getFirst(), cols.getSecond()));
			assertTrue("Balls seperated",Collision.startingSeperation(cols.getFirst(), cols.getSecond()) > 0);							
			assertTrue("Balls seperated but close",Collision.startingSeperation(cols.getFirst(), cols.getSecond()) < 1);							
			i++;
		}	
	}

}
