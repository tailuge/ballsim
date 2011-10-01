package org.motion.ballsim;

import org.junit.Before;
import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.Assert;
import org.motion.ballsim.util.UtilEvent;

/**
 * Tests for shot simulation performance.
 * <p>
 * 100 iterations of break off<br>
 * 1. No System.out: 167156<br> 
 * 2. Vector3D final: 167468<br>
 * 3. Other finalizations and log conditionals: 156703<br>
 * 4. Full finalization and assert replace, assertions enabled: 44437<br>
 * 5. Full finalization and assert replace, assertions disabled: 41907<br>
 *
 */
public class PerformanceTest {

	private int numberOfIterations = 1;
	
	@Before
	public void before() {
		
	}
	
	private long now() { return System.currentTimeMillis(); }
	
	@Test
	public final void hitSequenceOk() 
	{
		int numberOfEvents = doTest();
		Assert.isTrue(numberOfEvents>150);
		long start = now();
		for (int i=0;i<numberOfIterations;++i)
			doTest();
		System.out.println(now() - start);
	}
	
	private int doTest() {
		Table t = new Table(true);
		Rack.rack(t,"9Ball", "");
		t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 2.6,0.1));
		t.generateSequence();
		return t.getAllEvents().size();
	}

}
