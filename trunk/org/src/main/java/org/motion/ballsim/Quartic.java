package org.motion.ballsim;

import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolver;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory;


public class Quartic
{
 
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

		double c = max;
		max = 0;
		
		while (c != max)
		{
			max = c;
			try 
			{
				c = solver.solve(function, 0.0, max);
				if (c>max)
					return max;
			} 
			catch (Exception e) 
			{
				// no root in region
				return 0;
			} 
		}
		
		return c;
	}

	
    public static double evalAt(double[] coeff,double t)
    {
    	PolynomialFunction p = new PolynomialFunction(coeff);
    	return p.value(t);
    }

    public static void print(double[] coeff)
    {
    	PolynomialFunction p = new PolynomialFunction(coeff);
    	System.out.println(p.toString());
    }
    

}