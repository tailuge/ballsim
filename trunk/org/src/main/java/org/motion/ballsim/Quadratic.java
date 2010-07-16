package org.motion.ballsim;

import java.awt.geom.CubicCurve2D;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

public class Quadratic 
{
	
	private final static Logger logger = LoggerFactory.getLogger(Quadratic.class);


	static double evaluateAt(double a, double b, double c,double t)
	{
		return a*t*t + b*t + c;
	}
	
	static double leastPositiveRoot(double a, double b, double c)
	{
		logger.info(a+"t^2 "+b+"t "+c);

		double res[] = new double[3];
		int roots = CubicCurve2D.solveCubic(new double[]{c,b,a,0}, res);
		Arrays.sort(res);
		
		logger.info(roots+" roots -> " +Arrays.toString(res));
		
		for(double r:res)
		{
			if (r>0)
				return r;
		}
		
	    // zero implies no roots
	     
	    return 0;
	}
	
	public static double nextSmallest(double d)
	{
		double dec = Double.MIN_VALUE;
		while((d - dec) >= d)
		{
			dec *= 2;
		}
		return d-dec;
	}

	public static double nextSmallestDelta(double d)
	{
		double dec = Double.MIN_VALUE;
		while((d - dec) >= d)
		{
			dec *= 2;
		}
		return dec;
	}

	/**
	 * 
	 * Find input to function that returns a value as close to rootCandidate as possible 
	 * and have same sign as f(0)
	 * 
	 * @param func
	 * @param rootCandidate
	 * @return double 
	 */
	public static double optimise(Function<Double,Double> func, double rootCandidate)
	{
		
		double sign = Math.signum(func.apply(0.0));
		
		assert(sign != 0);

		double last = rootCandidate;
		
		logger.info("zero eval:"+func.apply(0.0));
		logger.info("zero eval:"+func.apply(0.0));
		logger.info("candidate f({}) = {}",last,func.apply(last));
		
		double delta = Quadratic.nextSmallestDelta(last);
		
		int count = 0;
		
		while(
				(last>0) && (last<=rootCandidate) && 
				(Math.signum(func.apply(last)) != sign)
			 )
		{
			last -=delta;
			delta *= 2;
			if (++count%1 == 0)
				logger.info("f({}) = {}",last,func.apply(last));
		}

		return last;
	}
	
	
	
	public static double latestTrueTime(Function<Double,Boolean> onTable, double rootCandidate)
	{

		// Condition must be good at t = 0
		assert(onTable.apply(0.0) == true);

		double last = rootCandidate;
		logger.info("onTable({}) = {}",last,onTable.apply(last));
		double delta = Quadratic.nextSmallestDelta(last);
		
		while(
				(last>0) && (last<=rootCandidate) && 
				(onTable.apply(last) != true)
			 )
		{
			last -=delta;
			delta *= 2;
			logger.info("onTable({}) = {}",last,onTable.apply(last));
		}

		return last;
	}

}
