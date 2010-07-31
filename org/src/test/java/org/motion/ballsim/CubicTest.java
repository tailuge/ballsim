package org.motion.ballsim;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.motion.ballsim.gwtsafe.PolynomialFunction;
import org.motion.ballsim.gwtsafe.Quadratic;


public class CubicTest {

	@Test
	public final void testSimpleRoot()  
	{

		double[] coeffs = { 24, -50, 35, -10, 1 };
		PolynomialFunction p = new PolynomialFunction(coeffs);

		// solve derivative cubic to get min/max points

		double res[] = new double[3];

		double cubicCoeffs[] = p.polynomialDerivative().getCoefficients();

		PolynomialFunction dpdt = new PolynomialFunction(cubicCoeffs);
		System.out.println("derivative cubic:" + dpdt);

		if (cubicCoeffs.length > 2) 
		{
			// swap a and d
			//double d = cubicCoeffs[0];
			//cubicCoeffs[0] = cubicCoeffs[3];
			//cubicCoeffs[3] = d;
			Quadratic.solveCubic(cubicCoeffs, res);
		}

		System.out.println("Cubic results : "+Arrays.toString(res));
		assertEquals("3 roots",3,res.length);
	}
	
}
