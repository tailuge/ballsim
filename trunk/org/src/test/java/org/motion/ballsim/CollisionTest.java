package org.motion.ballsim;

import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;

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
		public Event apply(Double arg) {
			return Utilities.getRolling(UtilVector3D.rnd().scalarMultiply(Ball.R*arg*Math.random()));
		}
	};

	Function<Double,Event> sliding = new Function<Double, Event>() 
	{	
		public Event apply(Double arg) {
			return Utilities.getSliding(
					UtilVector3D.rnd().scalarMultiply(Ball.R*arg*Math.random()),
					UtilVector3D.rnd().scalarMultiply(Ball.R*arg*Math.random()));
		}
	};

	Function<Double,Event> stationary = new Function<Double, Event>() 
	{	
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

//	2010-07-20 09:30:28,162 INFO [main] o.m.b.Table [Table.java:103] Ball java.awt.Color[r=255,g=255,b=255] : Stationary t:0.0 InitialHit p:{-13.48296365031531800; -14.77192239365871500; 0.00000000000000000} v:{0.00000000000000000; 0.00000000000000000; 0.00000000000000000} av:{0.00000000000000000; 0.00000000000000000; 0.00000000000000000}
//	2010-07-20 09:30:28,162 INFO [main] o.m.b.Table [Table.java:103] Ball java.awt.Color[r=255,g=0,b=0] : Sliding t:0.567154439135523 Collision p:{-7.46186466568119040; 13.58212010728338400; 0.00000000000000000} v:{-0.17115815906368680; -6.18992731042295200; 0.00000000000000000} av:{59.38269619059247600; 6.96035116039190900; 0.00000000000000000}
//	2010-07-20 09:30:28,166 INFO [main] o.m.b.Table [Table.java:103] Ball java.awt.Color[r=255,g=255,b=0] : Sliding t:0.567154439135523 Collision p:{-6.07824220980257750; 15.02632144823587300; 0.00000000000000000} v:{42.04694426339171000; 79.12242811508793000; 0.00000000000000000} av:{13.54980461407249400; 20.77515110112390700; 0.00000000000000000}

	@Test
	public final void testCaseKnownIssue() 
	{
		Event e2 = new Event(
				new Vector3D(-7.46186466568119040, 13.58212010728338400,0),
				new Vector3D(-0.17115815906368680, -6.18992731042295200,0),
				Vector3D.PLUS_J,
				Vector3D.PLUS_K,
				new Vector3D(59.38269619059247600, 6.96035116039190900,0),
				new Vector3D(0,0,0),
				State.Sliding,
				0.567154439135523,
				EventType.Interpolated,0,0);
		Event e3 = new Event(
				new Vector3D(-6.07824220980257750, 15.02632144823587300,0),
				new Vector3D(42.04694426339171000, 79.12242811508793000,0),
				Vector3D.PLUS_J,
				Vector3D.PLUS_K,
				new Vector3D(13.54980461407249400, 20.77515110112390700,0),
				new Vector3D(0,0,0),
				State.Sliding,
				0.567154439135523,
				EventType.Interpolated,0,0);

		Table t= new Table();

		t.ball(1).setFirstEvent(e2);
		t.ball(2).setFirstEvent(e3);
				
		assertTrue(Collision.validPosition(t));
		t.generateNext();
		assertTrue(Collision.validPosition(t));
	}
	
}
