package org.motion.ballsimapp.client;

import org.junit.Ignore;
import org.junit.Test;
import org.motion.ballsimapp.shared.CalculationPerformanceTestDelegate;

import com.google.gwt.junit.client.GWTTestCase;

public class CalculationPerformanceTest extends GWTTestCase {

	private CalculationPerformanceTestDelegate delegate = new CalculationPerformanceTestDelegate(
			getClass().getName());

	public String getModuleName() {
		return "org.motion.ballsimapp.Ballsimapp";
	}

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
