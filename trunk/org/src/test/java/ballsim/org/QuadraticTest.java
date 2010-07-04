package ballsim.org;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuadraticTest {

	@Test
	public final void badInputs() 
	{
		assertEquals("Zero inputs handled ok",0,Quadratic.getLeastPositiveRoot(0,0,0),0);
		assertEquals("Zero inputs handled ok",0,Quadratic.getLeastPositiveRoot(0,0,1),0);
		assertEquals("Zero inputs handled ok",0,Quadratic.getLeastPositiveRoot(0,1,1),0);
	}

	@Test
	public final void leastPosRoot() 
	{
		// (x-1)(x-2) = x^2 -3x + 2		
		assertEquals("Expect root at 1",1,Quadratic.getLeastPositiveRoot(1,-3,2),0);		
	}

	@Test
	public final void onlyPosRoot() 
	{
		// (x+1)(x-2) = x^2 -x -2
		assertEquals("Expect root at 2",2,Quadratic.getLeastPositiveRoot(1,-1,-2),0);	
	}

	@Test
	public final void noPosRoot() 
	{
		// (x+1)(x+2) = x^2 +3x +2
		assertEquals("Expect root at 2",0,Quadratic.getLeastPositiveRoot(1,3,2),0);	
	}

}
