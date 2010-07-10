package org.motion.ballsim;

import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolver;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory;


public class Quartic
{
 

    public static double evalAt(double[] coeff,double t)
    {
    	return t*t*t*t*coeff[4] + t*t*t*coeff[3] + t*t*coeff[2] + t*coeff[1] + coeff[0];
    }

    public static void print(double[] coeff)
    {
    	System.out.print(coeff[4]+"t*t*t*t ");
    	System.out.print(coeff[3]+"t*t*t ");
    	System.out.print(coeff[2]+"t*t ");
    	System.out.print(coeff[1]+"t ");
    	System.out.println(coeff[0]+" ");
    }
    
	/**
	 * Idea is to use Newton solver to find any root, then look for smaller roots
	 * until none remain.
	 * 
	 * @param coeffs
	 * @param max
	 * @return
	 */
	public static double smallestRoot(double[] coeffs, double max)
	{
		PolynomialFunction p = new PolynomialFunction(coeffs);
		
		UnivariateRealFunction function = p;
		UnivariateRealSolverFactory factory = UnivariateRealSolverFactory.newInstance();
		UnivariateRealSolver solver = factory.newNewtonSolver();

		double c = max + 1;
		
		while (c != max)
		{
			max = c;
			try 
			{
				c = solver.solve(function, 0.0, max);
				if (c>max)
					return max;
				System.out.println("c:"+c+" max:"+max);
			} 
			catch (Exception e) 
			{
				// no root in region
				return 0;
			} 
		}
		
		return c;
	}

}