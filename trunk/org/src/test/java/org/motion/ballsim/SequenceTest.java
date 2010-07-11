package org.motion.ballsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Ignore;
import org.junit.Test;

public class SequenceTest {

	@Test
	public final void testGenerateNext() 
	{
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(100),Vector3D.MINUS_I ));
		t.balls.add(b1);
		Sequence s = new Sequence(t);		
		assertTrue("Generate next event ok",s.generateNext());
	}

	@Test
	public final void testEmptyTable() 
	{
		Table t = new Table();
		Sequence s = new Sequence(t);
		
		assertEquals("Generate 0 events",0,s.generateSequence());
	}

	@Test
	public final void tesTwoStationary() 
	{
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getStationary());
		Ball b2 = new Ball(Utilities.getStationary(Vector3D.PLUS_I.scalarMultiply(Ball.R*3)));
		t.balls.add(b1);
		t.balls.add(b2);		
		Sequence s = new Sequence(t);
		assertEquals("Generate 0 events",0,s.generateSequence());
	}

	@Test
	public final void testSingleRoll() 
	{
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getRolling(Vector3D.MINUS_I ));
		t.balls.add(b1);
		Sequence s = new Sequence(t);		
		assertEquals("Generate 1 events",1,s.generateSequence());
	}

	
	@Test
	public final void testGenerateSequence() 
	{
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(10),Vector3D.MINUS_I ));
		t.balls.add(b1);
		Sequence s = new Sequence(t);		
		assertEquals("Generate 2 events slide->(roll,stationary)",2,s.generateSequence());
	}


	@Test
	@Ignore
	public final void testStaysOnTable() 
	{
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getSliding(new Vector3D(2,2,0).scalarMultiply(-100) ,Vector3D.PLUS_I));
//		Ball b1 = new Ball(Utilities.getSliding(new Vector3D(1,2,0).scalarMultiply(-100) ,Vector3D.PLUS_I));
		t.balls.add(b1);
		Sequence s = new Sequence(t);
		s.generateSequence();
		for(Event e:t.getAllEvents())
		{
			assertTrue("All times positive",e.t>=0);
			assertTrue("On table",Cushion.onTable(e));
		}
	}
}
