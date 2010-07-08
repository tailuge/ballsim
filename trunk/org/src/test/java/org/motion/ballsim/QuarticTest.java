package org.motion.ballsim;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Ignore;
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

	@Test
	@Ignore
	public final void testCapturedValueWrong() 
	{
		
       double[] coeffs = { 93.39184270920273, -821.051492918217, 2660.9051442368936, -3920.4796876958917, 2219.5056473290897 };
       double[] roots = Quartic.solveQuartic( coeffs );

       Arrays.sort(roots);
       Quartic.print(coeffs);
       System.out.println("roots:"+Arrays.toString(roots));
       assertEquals("0.27 is 1st root",0.27,roots[0],0.1);
       
       /*
quartic coeffs[93.39184270920273, -821.051492918217, 2660.9051442368936, -3920.4796876958917, 2219.5056473290897]
2219.5056473290897t*t*t*t -3920.4796876958917t*t*t 2660.9051442368936t*t -821.051492918217t 93.39184270920273 
quartic eval at root:-3.3085650856914697
>zero eval:8.636525649394512
candidate:-0.8828508859220485
100000last:0.39025862027330754 eval:-0.8828508858963608    
        */
	}

	
}
