package org.motion.ballsim.gwtsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public final class Quartic
{


	/**
	 * Differentiate once to find min/max points using cubic equation.
	 * Use Newton solver to inspect each of these spans to see which
	 * contain roots. Return the least positive one.
	 * 
	 * @param coeffs
	 * @param max
	 * @return
	 */
	public static double smallestRoot(double[] coeffs, double max)
	{
		PolynomialFunction p = new PolynomialFunction(coeffs);
		
		
		// solve derivative cubic to get min/max points
		
		double res[] = new double[3];
		
		double cubicCoeffs[] = p.polynomialDerivative().getCoefficients(); 
		
		//PolynomialFunction dpdt = new PolynomialFunction(cubicCoeffs);
		//System.out.println("derivative cubic:"+dpdt);		
		
		int cubicRoots = 0;
		if (cubicCoeffs.length > 2)
		{
			// swap a and d
			//double d=cubicCoeffs[0];
			//cubicCoeffs[0] = cubicCoeffs[3];
			//cubicCoeffs[3] = d;
			cubicRoots = Quadratic.solveCubic(cubicCoeffs, res);
		}
		// create set of spans that root must be between if present

		List<Double> spans = new ArrayList<Double>();
		List<Double> roots = new ArrayList<Double>();
		spans.add(0.0);
		spans.add(max);
		for(int i=0;i<cubicRoots;i++)
			spans.add(res[i]);
		
		Collections.sort(spans);
		
		//System.out.println("spans:"+spans);

		// look for root in each span.
		
		double last = 0.0;
		for(double s : spans)
		{
			if (last != s)
			{
				
				double bi=0;
				try 
				{
					bi = solve(p, last, s);
					roots.add(bi);
				} 
				catch (Exception e) 
				{
					
					// no root
				}
				
				
				
			}
			last = s;
		}
		
		// use least +ve root.
		Collections.sort(roots);

		
		for(double root:roots)
			if (root>0)
				return confirmNoSmallerRoot(coeffs,root);			

		return 0;
		
	}

	private static double confirmNoSmallerRoot(double coeffs[],double candidate)
	{
		double smaller = Quadratic.nextSmallest(candidate);
		PolynomialFunction p = new PolynomialFunction(coeffs);
				
		try {
			double smallerRoot = solve(p, 0, smaller);
			return smallerRoot;

		} catch (MaxIterationsExceededException e) {
			return candidate;
		}
		
	}
	
    public static double evalAt(double[] coeff,double t)
    {
    	return new PolynomialFunction(coeff).value(t);
    }

    public static String print(double[] coeff)
    {
    	return new PolynomialFunction(coeff).toString();
    }
    

    /*
     *  Bisection method imported from apache-commons-math
     */
 
    private static final int maximalIterationCount = 128;
    private static final double absoluteAccuracy = 0.00000000000001;
      
    public static double solve(final PolynomialFunction f, double min, double max)
    throws MaxIterationsExceededException {

    double m;
    double fm;
    double fmin;
    
    if (f.value(min)*f.value(max) > 0)
    	throw new MaxIterationsExceededException();
    
    int i = 0;
    while (i < maximalIterationCount) {
        m = midpoint(min, max);
       fmin = f.value(min);
       fm = f.value(m);

        if (fm * fmin > 0.0) {
            // max and m bracket the root.
            min = m;
        } else {
            // min and m bracket the root.
            max = m;
        }

        if (Math.abs(max - min) <= absoluteAccuracy) {
            return midpoint(min, max);
        }
        ++i;
    }

    throw new MaxIterationsExceededException();
    }

    public static double midpoint(double a, double b) {
        return (a + b) * .5;
    }
    
  

}