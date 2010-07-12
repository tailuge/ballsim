package org.motion.ballsim;

import com.google.common.base.Function;

public class Quadratic 
{

	static double evaluateAt(double a, double b, double c,double t)
	{
		return a*t*t + b*t + c;
	}
	
	static double leastPositiveRoot(double a, double b, double c)
	{
		System.out.println(a+"t^2 "+b+"t "+c);
		double discr = Math.sqrt((b * b) - (4 * a * c));

		// if discriminant > 0 equation has 2 real roots ignore all other cases

		if (discr > 0)
		{
			double r1 = (-b + discr)/(2 * a);
			double r2 = (-b - discr)/(2 * a);
	                  
			if (r1 > r2)
			{
				double temp = r1;
				r1 = r2;
				r2 = temp;
	        }
	         
			// lesser root is in r1, return if its +ve
	         
			if (r1>0)
				return r1;
	         
			if (r2>0)
				return r2;
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
		
		System.out.println("zero eval:"+func.apply(0.0));
		System.out.println("candidate:"+func.apply(last));
		
		double delta = Quadratic.nextSmallestDelta(last);
		
		int count = 0;
		
		while(
				(last>0) && (last<=rootCandidate) && 
				(Math.signum(func.apply(last)) != sign)
			 )
		{
			last -=delta;
			delta *= 2;
			if (++count%100000 == 0)
				System.out.println(count+"last:"+last+" eval:"+func.apply(last));
		}

		return last;
	}
	
	
	
	public static double latestTrueTime(Function<Double,Boolean> onTable, double rootCandidate)
	{

		// Condition must be good at t = 0
		assert(onTable.apply(0.0) == true);

		double last = rootCandidate;
		System.out.println("--");
		System.out.println("candidate:"+last+" onTable:"+onTable.apply(last));
		double delta = Quadratic.nextSmallestDelta(last);
		
		int count = 0;
		
		while(
				(last>0) && (last<=rootCandidate) && 
				(onTable.apply(last) != true)
			 )
		{
			last -=delta;
			delta *= 2;
			System.out.println("candidate:"+last+" onTable:"+onTable.apply(last));
			if (++count%100000 == 0)
				System.out.println(count+"last:"+last+" eval:"+onTable.apply(last));
		}

		return last;
	}

}
