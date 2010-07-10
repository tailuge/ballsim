package org.motion.ballsim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuarticTest {

	@Test
	public final void testSimpleRoot()  
	{
        double[] coeffs = { 24, -50, 35, -10, 1 };
        assertEquals("1.0 is 1st root",1.0,Quartic.smallestRoot(coeffs,100),0.000001);
        Quartic.print(coeffs);
	}

	@Test
	public final void testNoRoots()  
	{
        double[] coeffs = { 24, -50, 35, -10, 3 };
        assertEquals("no roots",0.0,Quartic.smallestRoot(coeffs,100),0);
	}

	@Test
	public final void testTypical()  
	{
		// compare with http://www.wolframalpha.com/input/?i=2219.5056473290897t*t*t*t+-3920.4796876958917t*t*t+%2B2660.9051442368936t*t+-821.051492918217t+%2B93.39184270920273			
        double[] coeffs = { 93.39184270920273, -821.051492918217, 2660.9051442368936, -3920.4796876958917, 2219.5056473290897 };
        assertEquals("0.27 is 1st root",0.27,Quartic.smallestRoot(coeffs,100),0.1);
	}
	
	@Test
	public final void testSkipNegativeRoot()  
	{
        double[] coeffs = { 12, 8, -7, -2, 1 };
        assertEquals("2.0 is 1st root",2.0,Quartic.smallestRoot(coeffs,100),0.000001);
	}
	
	
	@Test
	public final void testFindRootWithNegMaxima()  
	{
        double[] coeffs = { 12.407390198237287, -247.5287260222008, 1356.6017708251973, -3092.5777341883927, 2500.0 };
        assertEquals("0.077 is 1st root",0.077,Quartic.smallestRoot(coeffs,100),0.1);
	}

	@Test
	public final void testGradientPosAtZero()  
	{
        double[] coeffs = { 0.6, 4.9, -10.4, 5.9, -1 };
        assertEquals("1 is 1st root",1.0,Quartic.smallestRoot(coeffs,100),0.01);
	}
}
