package org.motion.ballsimapp.client;

import org.junit.Ignore;
import org.junit.Test;
import org.motion.ballsimapp.shared.CalculationPerformanceTestDelegate;

import com.google.gwt.junit.client.GWTTestCase;

public class CalculationPerformanceTest extends GWTTestCase {

	private CalculationPerformanceTestDelegate delegate = new CalculationPerformanceTestDelegate();

	public String getModuleName() {
		return "org.motion.ballsimapp.Ballsimapp";
	}

	@Test
	public final void testTableEvaluationCorrectness() {
		delegate.testTableEvaluationCorrectness();
	}

	@Ignore
	@Test
	public final void testTableEvaluationPerfomance() {
		delegate.testTableEvaluationPerfomance();
	}

}
