package org.motion.ballsim;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.junit.Test;

public class QuarticTest {

	@Test
	public final void testSimpleRoot() throws ConvergenceException, FunctionEvaluationException 
	{
        double[] coeffs = { 24, -50, 35, -10, 1 };
        assertEquals("1.0 is 1st root",1.0,Quartic.smallestRoot(coeffs,100),0.000001);
	}

	@Test
	public final void testNoRoots() throws ConvergenceException, FunctionEvaluationException 
	{
        double[] coeffs = { 24, -50, 35, -10, 3 };
        assertEquals("no roots",0.0,Quartic.smallestRoot(coeffs,100),0);
	}

	@Test
	public final void testTypical() throws ConvergenceException, FunctionEvaluationException 
	{
		// compare with http://www.wolframalpha.com/input/?i=2219.5056473290897t*t*t*t+-3920.4796876958917t*t*t+%2B2660.9051442368936t*t+-821.051492918217t+%2B93.39184270920273			
        double[] coeffs = { 93.39184270920273, -821.051492918217, 2660.9051442368936, -3920.4796876958917, 2219.5056473290897 };
        assertEquals("0.27 is 1st root",0.27,Quartic.smallestRoot(coeffs,100),0.1);
	}
	
	@Test
	public final void testSkipNegativeRoot() throws ConvergenceException, FunctionEvaluationException 
	{
        double[] coeffs = { 12, 8, -7, -2, 1 };
        assertEquals("2.0 is 1st root",2.0,Quartic.smallestRoot(coeffs,100),0.000001);
	}
	
}
