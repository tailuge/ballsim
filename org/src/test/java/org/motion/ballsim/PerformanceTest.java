package org.motion.ballsim;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsim.util.UtilEvent;

/**
 * 
 * Tests for shot simulation performance.
 * <p>
 * 100 iterations of break off<br>
 * 1. No System.out: 167156<br>
 * 2. Vector3D final: 167468<br>
 * 3. Other finalizations and log conditionals: 156703<br>
 * 4. Full finalization and assert replace, assertions enabled: 44437<br>
 * 5. Full finalization and assert replace, assertions disabled: 41907<br>
 * 
 * Notes for single iteration:
 * Vector3D instanceCount = 1215056
 * events: 115
 * 
 * with simple cache on Event.accelerate
 * events: 115
 * vectors: 531793
 * 
 * remove guard for arithmetic exception on normalise. 
 * events: 115
 * vectors: 525515
 * 
 * use scale and add, only calc position when finding collision (not rotation)
 * events: 115
 * vectors: 122042
 * 
 * further position only calcs
 * events: 115
 * vectors: 117205
 * 
 * Use addScaledPair
 * events: 115
 * vectors: 81335
 * 
 * Use scaledCrossProduct
 * events: 115
 * vectors: 78837
 * 
 * More chained vector combinations.
 * events: 115
 * vectors: 75645
 * 
 * Dont collide with inpocket balls
 * events: 115
 * vectors: 66205
 */
public class PerformanceTest {

	private int numberOfIterations = 1;

	@Before
	public void before() {

	}

	private long now() {
		return System.currentTimeMillis();
	}

	public final void testCorrectness() {
		int numberOfEvents = doTest();
		Assert.assertTrue("expected > 150 events got " + numberOfEvents,
				numberOfEvents > 150);
	}

	@Test
	public final void testPeformance() {
		doTest();
		long start = now();
		for (int i = 0; i < numberOfIterations; ++i)
			doTest();
		System.out.println(now() - start);
	}

	private int doTest() {
		Table t = new Table(true);
		Rack.rack(t, "9Ball", "");
		t.ball(1).setFirstEvent(
				UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 2.6, 0.1));
		t.generateSequence();
		return t.getAllEvents().size();
	}

	/*
	 * Unoptimised
	 * 
	 * vectors: 7
     * vectors: 14
     * 
     * caching
     * 
	 * vectors: 7
	 * vectors: 7
	 * 
	 * guarded normalise
	 * 
	 * vectors: 6
	 * vectors: 6
	 * 
	 * combine some vector operations like scale and add
	 * 
	 * vectors: 3
	 * vectors: 3
	 * 
	 */
	@Test
	public final void testCaching()
	{
		Event e = UtilEvent.hit(Vector3D.ZERO, new Vector3D(0.5,0.5,0), 1, 0.1);
		Vector3D.instanceCount = 0;
		e.acceleration().getX();		
		System.err.println("vectors: " + Vector3D.instanceCount);
		e.acceleration().getX();
		System.err.println("vectors: " + Vector3D.instanceCount);
		Assert.assertTrue(Vector3D.instanceCount > 0);
	}
	
}
