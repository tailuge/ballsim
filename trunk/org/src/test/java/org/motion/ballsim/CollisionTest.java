package org.motion.ballsim;

import static org.junit.Assert.assertTrue;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Test;

import com.google.common.base.Function;

public class CollisionTest {

	@Test
	public final void testCollisionTime() 
	{
		Event e1 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(130));
		Event e2 = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(-130));
		e2.pos = Vector3D.PLUS_I.scalarMultiply(Ball.R*4);
		double t = Collision.collisionTime(e1, e2,Double.MAX_VALUE);
		EventPair cols = Collision.collisionEvents(e1,e2, t);
		assertTrue("Rolling towards each other collide",t>0);
		System.out.println(Collision.startingSeperation(cols.first, cols.second));
		assertTrue("Balls seperated",Collision.startingSeperation(cols.first, cols.second) > 0);
	}



	Function<Double,Event> rolling = new Function<Double, Event>() 
	{	
		@Override
		public Event apply(Double arg) {
			return Utilities.getRolling(UtilVector3D.rnd().scalarMultiply(Ball.R*arg*Math.random()));
		}
	};

	Function<Double,Event> sliding = new Function<Double, Event>() 
	{	
		@Override
		public Event apply(Double arg) {
			return Utilities.getSliding(
					UtilVector3D.rnd().scalarMultiply(Ball.R*arg*Math.random()),
					UtilVector3D.rnd().scalarMultiply(Ball.R*arg*Math.random()));
		}
	};

	Function<Double,Event> stationary = new Function<Double, Event>() 
	{	
		@Override
		public Event apply(Double arg) {
			return Utilities.getStationary();
		}
	};

	@Test
	public final void testManySlidingStat() 
	{
		testMany(sliding,stationary);
	}
	
	@Test
	public final void testManyRollStat() 
	{
		testMany(rolling,stationary);
	}

	@Test
	public final void testManyRollRoll() 
	{
		testMany(rolling,rolling);
	}

	@Test
	public final void testManySlideSlide() 
	{
		testMany(sliding,sliding);
	}

	@Test
	public final void testManySlideRoll() 
	{
		testMany(sliding,rolling);
	}

	public final void testMany(Function<Double,Event> ballA, Function<Double,Event> ballB) 
	{
		int i = 0;
		while(i < 100)
		{
			Event e1 = ballA.apply(100.0);		
			Event e2 = ballB.apply(100.0);
			e2.pos = e1.next().pos.add(UtilVector3D.rnd());
						
			if (Collision.startingSeperation(e1, e2) <= 0)
				continue;
			
			double t = Collision.collisionTime(e1, e2,Double.MAX_VALUE);

			if (t<=0 || t>e1.next().t)
				continue;
			
			EventPair cols = Collision.collisionEvents(e1,e2, t);
			//System.out.println(Collision.startingSeperation(cols.getFirst(), cols.getSecond()));
			//System.out.println(e1);
			//System.out.println(e2);
			assertTrue("Balls seperated",Collision.startingSeperation(cols.first, cols.second) > 0);							
			assertTrue("Balls seperated but close",Collision.startingSeperation(cols.first, cols.second) < 0.1);							
			i++;
		}	
	}


}
