package org.motion.ballsim;

public class Quadratic 
{

	static double evaluateAt(double a, double b, double c,double t)
	{
		return a*t*t + b*t + c;
	}
	
	static double getLeastPositiveRoot(double a, double b, double c)
	{
		
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
}