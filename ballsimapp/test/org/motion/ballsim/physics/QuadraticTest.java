package org.motion.ballsim.physics;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuadraticTest {

	public final double solve(double a, double b, double c)
	{
		return org.motion.ballsim.physics.gwtsafe.Quadratic.leastPositiveRoot(a,b,c);
	}
	@Test
	public final void badInputs() 
	{
		assertEquals("Zero inputs handled ok",0,solve(0,0,0),0);
		assertEquals("Zero inputs handled ok",0,solve(0,0,1),0);

	}

	@Test
	public final void leastPosRoot() 
	{
		// (x-1)(x-2) = x^2 -3x + 2		
		assertEquals("Expect root at 1",1,solve(1,-3,2),0);		
	}

	@Test
	public final void onlyPosRoot() 
	{
		// (x+1)(x-2) = x^2 -x -2
		assertEquals("Expect root at 2",2,solve(1,-1,-2),0);	
	}

	@Test
	public final void noPosRoot() 
	{
		// (x+1)(x+2) = x^2 +3x +2
		assertEquals("Expect root at 2",0,solve(1,3,2),0);	
	}

	@Test
	public final void whenAzero() 
	{
		// (x+1)(x+2) = x^2 +3x +2
		assertEquals("Expect root at 1",1,solve(0,1,-1),0);	
	}

	@Test
	public final void whenAccuracyOnRoot() 
	{
		// -1.4260557597450674E-13t^2 69.74504537775142t -0.03120864927270972
		assertEquals("Expect root at 0.00047",0.000447,solve(-1.4260557597450674E-13, 69.74504537775142,-0.03120864927270972),0.0001);	
	}
	
}
