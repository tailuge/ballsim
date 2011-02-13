package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;


import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.motion.Ball;
import org.motion.ballsim.motion.Collision;
import org.motion.ballsim.motion.Cushion;
import org.motion.ballsim.motion.Event;
import org.motion.ballsim.motion.Pocket;
import org.motion.ballsim.search.Table;
import org.motion.ballsim.util.Logger;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsim.util.UtilVector3D;

public class SequenceTest {

	final Logger logger = new Logger("SequenceTest",true);
	
	@Test
	public final void testGenerateNext() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(100),Vector3D.MINUS_I ));
		assertTrue("Generate next event ok",t.generateNext());
	}

	@Test
	public final void testEmptyTable() 
	{
		Table t = new Table();
		assertEquals("Generate 0 events",0,t.generateSequence());
	}

	@Test
	public final void testTwoStationary() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary());
		t.ball(2).setFirstEvent(Utilities.getStationary(Vector3D.PLUS_I.scalarMultiply(Ball.R*3)));
		assertEquals("Generate 0 events",0,t.generateSequence());
	}

	@Test
	public final void testSingleRoll() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getRolling(Vector3D.MINUS_I ));
		assertEquals("Generate 1 events",1,t.generateSequence());
	}

	@Test
	public final void testTwoRollParallel() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getRolling(Vector3D.MINUS_I ));
		t.ball(2).setFirstEvent(Utilities.getRolling(Vector3D.MINUS_I , Vector3D.PLUS_I.scalarMultiply(Ball.R*4)));
		assertEquals("Generate 2 events",2,t.generateSequence());
	}

	@Test
	//@Ignore
	public final void testStatAndRoll() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary());
		t.ball(2).setFirstEvent(Utilities.getRolling(Vector3D.MINUS_I , Vector3D.PLUS_I.scalarMultiply(Ball.R*4)));
		assertEquals("Generate 1 events",1,t.generateSequence());
	}

	@Test
	public final void testGenerateSequence() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(10),Vector3D.MINUS_I ));
		assertEquals("Generate 2 events slide->(roll,stationary)",2,t.generateSequence());
	}


	@Test
	public final void testStaysOnTable() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getSliding(new Vector3D(1,2,0).scalarMultiply(-100) ,Vector3D.PLUS_I));
		t.generateSequence();
		
		for(Event e:t.getAllEvents())
		{
			assertTrue("All times positive",e.t>=0);
			assertTrue("On table",Cushion.onTable(e));
		}
	}
	
	@Test
	//@Ignore
	public final void testStaysThree() 
	{
		
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary());
		t.ball(2).setFirstEvent(Utilities.getRolling(
				Vector3D.MINUS_J.scalarMultiply(150), new Vector3D(Ball.R * 2
						* (2.0 / 7.0), Ball.R * 4, 0)));
		t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R * 0,
				-Ball.R * 4, 0)));

		t.generateSequence();
		for(Event e:t.getAllEvents())
		{
			assertTrue("All times positive",e.t>=0);
			assertTrue("On table",Cushion.onTable(e));
		}
	}
	
	@Test
	public final void testManyRandom() {

		Collection<Integer> result = new ArrayList<Integer>();

		int v = 0;
		while (v < 20) {
			Table t = new Table();
			t.ball(1).setFirstEvent(Utilities.getStationary(UtilVector3D.rnd()
					.scalarMultiply(20)));
			t.ball(2).setFirstEvent(Utilities.getRolling(UtilVector3D.rnd()
					.scalarMultiply(250), UtilVector3D.rnd().scalarMultiply(20)));
			t.ball(3).setFirstEvent(Utilities.getSliding(UtilVector3D.rnd()
					.scalarMultiply(150), UtilVector3D.rnd().scalarMultiply(20)));
			
			logger.info("+");
			
			if (!Cushion.validPosition(t))
				continue;

			if (!Collision.validPosition(t))
				continue;

			logger.info("test starting pos >>>>> {}",v);

			
			result.add(t.generateSequence());
			
			for (Event e : t.getAllEvents()) {
				assertTrue("All times positive", e.t >= 0);
				assertTrue("On table", Cushion.onTable(e));
			}
			v++;
		}
		logger.info("event list {}",result);
	}

	@Test
	public final void alwaysSeperated() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getSliding(new Vector3D(1,2,0).scalarMultiply(-100) ,Vector3D.PLUS_I));
		t.generateSequence();
		
		for(Event e:t.getAllEvents())
		{
			assertTrue("All times positive",e.t>=0);
			assertTrue("On table",Cushion.onTable(e));
		}
	}
	
	
	@Test
	public final void hitSequenceOk() 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.MINUS_J, 100,0.9999999999999999));			
		t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R,-Ball.R * 6, 0)));
		
		
		assertTrue("Generated ok",t.generateSequence() > 0);
	}

	@Test
	public final void testPottedBallGoesOffTable() 
	{
		Table t = new Table(true);		
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = Pocket.p6.add(new Vector3D(-6*Ball.R,+0.2,0));

		t.ball(1).setFirstEvent(e);

		assertEquals("Generate 2 events (roll,potting,potted)",2,t.generateSequence());
		
		System.out.println(t.getAllEvents());
	}

	@Test
	public final void testKnuckleBounce() 
	{
		Table t = new Table(true);		
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(2500));
		e.pos = Pocket.p6k1.add(new Vector3D(-3*Ball.R,+0.2,0));

		t.ball(1).setFirstEvent(e);

		assertEquals("Generate > 2 events (roll,slide,other)",true,t.generateSequence()>2);
		
		System.out.println(t.getAllEvents());
	}

}
