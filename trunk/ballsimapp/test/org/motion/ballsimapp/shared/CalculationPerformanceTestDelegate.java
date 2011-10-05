package org.motion.ballsimapp.shared;

import junit.framework.Assert;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsim.util.UtilEvent;

/**
 * Delegate for Performances tests which is called by Java and GWT Unit Tests
 * for comparison
 */
public class CalculationPerformanceTestDelegate extends AbstractTestDelegate {

	private int numberOfIterations = 1;

	public CalculationPerformanceTestDelegate(String name) {
		super(name);
	}

	public final void testTableEvaluationCorrectness() {
		int numberOfEvents = doTest();
		Assert.assertTrue("expected > 150 events got " + numberOfEvents,
				numberOfEvents > 150);
	}

	public final void testTableEvaluationPerfomance() {
		int numberOfEvents = 0;
		Vector3D.instanceCount = 0;
		long start = now();
		for (int i = 0; i < numberOfIterations; ++i)
			numberOfEvents = doTest();
		System.out.println("events: " + numberOfEvents);
		System.out.println("vectors: " + Vector3D.instanceCount);
		System.out.println("time taken for " + numberOfIterations
				+ " iterations: " + (now() - start));

	}

	private int doTest() {
		Table t = new Table(true);
		Rack.rack(t, "PerformanceTest", "");
		t.ball(1).setFirstEvent(
				UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 2.6, 0.1));
		t.generateSequence();
		return t.getAllEvents().size();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Hit Return to Start Test");
		readLine();
		new CalculationPerformanceTestDelegate(
				CalculationPerformanceTestDelegate.class.getName())
				.testTableEvaluationPerfomance();
		System.out.println("Test Complete, Hit Return to Exit");
		readLine();
	}
}
