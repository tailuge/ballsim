package org.motion.ballsim;

import static org.junit.Assert.*;

import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;

public class PerformanceTest {

	@Test
	public final void hitSequenceOk() 
	{
		Table t = new Table();
		t.rack("9Ball", "");
		t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 2.6,0.1));
		t.generateSequence();
		System.out.println(t.getAllEvents().size() + "events");
		assertTrue(t.getAllEvents().size() > 150);
	}
}
