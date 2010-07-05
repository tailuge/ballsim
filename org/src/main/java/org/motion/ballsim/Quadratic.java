package org.motion.ballsim;

public class Quadratic 
{

	static double evaluateAt(double a, double b, double c,double t)
	{
		return a*t*t + b*t + c;
	}
	
	static double getLeastPositiveRoot(double a, double b, double c)
	{
		System.out.println("a:"+a);
		System.out.println("b:"+b);
		System.out.println("c:"+c);
		
	     double discr = Math.sqrt((b * b) - (4 * a * c));
	     // Determine number of roots

	     // if discr > 0 equation has 2 real roots ignor all other cases

	     if (discr > 0)
	     {
	    	 double r1 = (-b + discr)/(2 * a);
	         double r2 = (-b - discr)/(2 * a);
	         
	         System.out.println("r1:"+r1);
	         System.out.println("r2:"+r2);
	         System.out.println("eval at r1:"+evaluateAt(a,b,c,r1));
	         System.out.println("eval at r2:"+evaluateAt(a,b,c,r2));
	         
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
}
