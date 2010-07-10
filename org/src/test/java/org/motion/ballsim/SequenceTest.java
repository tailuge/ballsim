package org.motion.ballsim;

import static org.junit.Assert.*;

import org.apache.commons.math.geometry.Vector3D;
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
	public final void testGenerateSequnce() 
	{
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(100),Vector3D.MINUS_I ));
		t.balls.add(b1);
		Sequence s = new Sequence(t);
		
		assertEquals("Generate 2 events",2,s.generateSequence());
	}

}
