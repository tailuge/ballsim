package org.motion.ballsim;

import static org.junit.Assert.*;

import org.junit.Test;
import org.motion.ballsim.Quadratic;

public class QuadraticTest {

	@Test
	public final void badInputs() 
	{
		assertEquals("Zero inputs handled ok",0,Quadratic.leastPositiveRoot(0,0,0),0);
		assertEquals("Zero inputs handled ok",0,Quadratic.leastPositiveRoot(0,0,1),0);

	}

	@Test
	public final void leastPosRoot() 
	{
		// (x-1)(x-2) = x^2 -3x + 2		
		assertEquals("Expect root at 1",1,Quadratic.leastPositiveRoot(1,-3,2),0);		
	}

	@Test
	public final void onlyPosRoot() 
	{
		// (x+1)(x-2) = x^2 -x -2
		assertEquals("Expect root at 2",2,Quadratic.leastPositiveRoot(1,-1,-2),0);	
	}

	@Test
	public final void noPosRoot() 
	{
		// (x+1)(x+2) = x^2 +3x +2
		assertEquals("Expect root at 2",0,Quadratic.leastPositiveRoot(1,3,2),0);	
	}

	@Test
	public final void whenAzero() 
	{
		// (x+1)(x+2) = x^2 +3x +2
		assertEquals("Expect root at 1",1,Quadratic.leastPositiveRoot(0,1,-1),0);	
	}

	@Test
	public final void whenAccuracyOnRoot() 
	{
		// -1.4260557597450674E-13t^2 69.74504537775142t -0.03120864927270972
		assertEquals("Expect root at 0.00047",0.000447,Quadratic.leastPositiveRoot(-1.4260557597450674E-13, 69.74504537775142,-0.03120864927270972),0.0001);	
	}
	
}
