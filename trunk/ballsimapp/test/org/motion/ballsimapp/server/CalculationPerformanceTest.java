package org.motion.ballsimapp.server;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.motion.ballsimapp.shared.CalculationPerformanceTestDelegate;

public class CalculationPerformanceTest extends TestCase {

	private CalculationPerformanceTestDelegate delegate = new CalculationPerformanceTestDelegate(getClass().getName());

	@Ignore
	@Test
	public final void testTableEvaluationCorrectness() {
		delegate.testTableEvaluationCorrectness();
	}

	@Test
	public final void testTableEvaluationPerfomance() {
		delegate.testTableEvaluationPerfomance();
	}

	
}
