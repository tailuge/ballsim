package org.motion.ballsim;

import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolver;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory;


public class Quartic
{

	static private UnivariateRealSolverFactory factory = UnivariateRealSolverFactory.newInstance();

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
		
		UnivariateRealFunction function = p;
		
		UnivariateRealSolver solver = factory.newNewtonSolver();
		
		// solve derivative cubic to get min/max points
		
		double res[] = new double[3];
		int cubicRoots = CubicCurve2D.solveCubic(p.polynomialDerivative().getCoefficients(), res);

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
				//System.out.println("try["+last+","+s+"]");	
				double bi=0;
				try 
				{
					bi = solver.solve(function, last, s, (last+s)/2);
				} 
				catch (Exception e) 
				{
					// no root
				}
				
				//System.out.println("bi:"+bi);
				roots.add(bi);
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
		
		UnivariateRealFunction function = p;
		
		UnivariateRealSolver solver = factory.newBisectionSolver();
		
		try {
			double smallerRoot = solver.solve(function, 0, smaller);
			return smallerRoot;

		} catch (ConvergenceException e) {
			return candidate;
		} catch (FunctionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return candidate;
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