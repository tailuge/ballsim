package org.motion.ballsim;

import static org.junit.Assert.*;

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
		
       double[] coeffs = { 2, -3, 1 };
       double[] roots = Quartic.solveQuadratic( coeffs );

       for ( int i = 0; i < roots.length; i++ )
       {
           System.out.println( i + ":" + roots[i] );
       }
	}

}
