package org.motion.ballsim;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class QuarticTest {

	@Test
	public final void testSolveQuadratic() 
	{
		
       double[] coeffs = { 2, -3, 1 };
       double[] roots = Quartic.solveQuadratic( coeffs );

       for ( int i = 0; i < roots.length; i++ )
       {
           System.out.println( i + ":" + roots[i] );
       }
	}

	@Test
	public final void testSolveQuartic() 
	{
		
       double[] coeffs = { 24, -50, 35, -10, 1 };
       double[] roots = Quartic.solveQuartic( coeffs );

       Arrays.sort(roots);
       assertEquals("1 is 1st root",1.0,roots[0],0.0);
	}

}
