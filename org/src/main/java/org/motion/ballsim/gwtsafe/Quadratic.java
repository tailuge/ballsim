package org.motion.ballsim.gwtsafe;

import java.util.Arrays;


public class Quadratic 
{
	


	static double evaluateAt(double a, double b, double c,double t)
	{
		return a*t*t + b*t + c;
	}
	
	public static double leastPositiveRoot(double a, double b, double c)
	{

		double res[] = new double[3];
		solveCubic(new double[]{c,b,a,0}, res);
		Arrays.sort(res);
		
		
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
		
		
		double delta = Quadratic.nextSmallestDelta(last);

		int iter = 0;
		
		while(
				(last>0) && (last<=rootCandidate) && 
				(Math.signum(func.apply(last)) != sign)
			 )
		{
			last -=delta;
			delta *= 2;
			iter++;
		}

		if (iter>8) System.out.println("Quadratic optimise.Iterations " + iter);
		return last;
	}
	
	
	
	public static double latestTrueTime(Function<Double,Boolean> onTable, double rootCandidate)
	{

		// Condition must be good at t = 0
		//assert(onTable.apply(0.0) == true);
		if (onTable.apply(0.0)==false)
		{
			System.out.println("Problem: not on table");
		}
		
		double last = rootCandidate;
		double delta = Quadratic.nextSmallestDelta(last);

		int iter = 0;
		
		while(
				(last>0) && (last<=rootCandidate) && 
				(onTable.apply(last) != true)
			 )
		{
			last -=delta;
			delta *= 2;
			iter++;
		}

		if (iter>8) System.out.println("Quadratic optimise.latestTrueTime " + iter);

		return last;
	}

	/*
	 * GWT safe version imported from external library apache-commons-math
	 */

   /**
     * Solves the cubic whose coefficients are in the <code>eqn</code>
     * array and places the non-complex roots back into the same array,
     * returning the number of roots.  The solved cubic is represented
     * by the equation:
     * <pre>
     *     eqn = {c, b, a, d}
     *     dx^3 + ax^2 + bx + c = 0
     * </pre>
     * A return value of -1 is used to distinguish a constant equation
     * that might be always 0 or never 0 from an equation that has no
     * zeroes.
     * @param eqn an array containing coefficients for a cubic
     * @return the number of roots, or -1 if the equation is a constant.
     * @since 1.2
     */
    public static int solveCubic(double eqn[]) {
        return solveCubic(eqn, eqn);
    }

    /**
     * Solve the cubic whose coefficients are in the <code>eqn</code>
     * array and place the non-complex roots into the <code>res</code>
     * array, returning the number of roots.
     * The cubic solved is represented by the equation:
     *     eqn = {c, b, a, d}
     *     dx^3 + ax^2 + bx + c = 0
     * A return value of -1 is used to distinguish a constant equation,
     * which may be always 0 or never 0, from an equation which has no
     * zeroes.
     * @param eqn the specified array of coefficients to use to solve
     *        the cubic equation
     * @param res the array that contains the non-complex roots
     *        resulting from the solution of the cubic equation
     * @return the number of roots, or -1 if the equation is a constant
     * @since 1.3
     */
    public static int solveCubic(double eqn[], double res[]) {
        // From Numerical Recipes, 5.6, Quadratic and Cubic Equations
        double d = eqn[3];
        if (d == 0.0) {
            // The cubic has degenerated to quadratic (or line or ...).
            return solveQuadratic(eqn, res);
        }
        double a = eqn[2] / d;
        double b = eqn[1] / d;
        double c = eqn[0] / d;
        int roots = 0;
        double Q = (a * a - 3.0 * b) / 9.0;
        double R = (2.0 * a * a * a - 9.0 * a * b + 27.0 * c) / 54.0;
        double R2 = R * R;
        double Q3 = Q * Q * Q;
        a = a / 3.0;
        if (R2 < Q3) {
            double theta = Math.acos(R / Math.sqrt(Q3));
            Q = -2.0 * Math.sqrt(Q);
            if (res == eqn) {
                // Copy the eqn so that we don't clobber it with the
                // roots.  This is needed so that fixRoots can do its
                // work with the original equation.
                eqn = new double[4];
                System.arraycopy(res, 0, eqn, 0, 4);
            }
            res[roots++] = Q * Math.cos(theta / 3.0) - a;
            res[roots++] = Q * Math.cos((theta + Math.PI * 2.0)/ 3.0) - a;
            res[roots++] = Q * Math.cos((theta - Math.PI * 2.0)/ 3.0) - a;
            fixRoots(res, eqn);
        } else {
            boolean neg = (R < 0.0);
            double S = Math.sqrt(R2 - Q3);
            if (neg) {
                R = -R;
            }
            double A = Math.pow(R + S, 1.0 / 3.0);
            if (!neg) {
                A = -A;
            }
            double B = (A == 0.0) ? 0.0 : (Q / A);
            res[roots++] = (A + B) - a;
        }
        return roots;
    }

    /*
     * This pruning step is necessary since solveCubic uses the
     * cosine function to calculate the roots when there are 3
     * of them.  Since the cosine method can have an error of
     * +/- 1E-14 we need to make sure that we don't make any
     * bad decisions due to an error.
     *
     * If the root is not near one of the endpoints, then we will
     * only have a slight inaccuracy in calculating the x intercept
     * which will only cause a slightly wrong answer for some
     * points very close to the curve.  While the results in that
     * case are not as accurate as they could be, they are not
     * disastrously inaccurate either.
     *
     * On the other hand, if the error happens near one end of
     * the curve, then our processing to reject values outside
     * of the t=[0,1] range will fail and the results of that
     * failure will be disastrous since for an entire horizontal
     * range of test points, we will either overcount or undercount
     * the crossings and get a wrong answer for all of them, even
     * when they are clearly and obviously inside or outside the
     * curve.
     *
     * To work around this problem, we try a couple of Newton-Raphson
     * iterations to see if the true root is closer to the endpoint
     * or further away.  If it is further away, then we can stop
     * since we know we are on the right side of the endpoint.  If
     * we change direction, then either we are now being dragged away
     * from the endpoint in which case the first condition will cause
     * us to stop, or we have passed the endpoint and are headed back.
     * In the second case, we simply evaluate the slope at the
     * endpoint itself and place ourselves on the appropriate side
     * of it or on it depending on that result.
     */
    private static void fixRoots(double res[], double eqn[]) {
        final double EPSILON = 1E-5;
        for (int i = 0; i < 3; i++) {
            double t = res[i];
            if (Math.abs(t) < EPSILON) {
                res[i] = findZero(t, 0, eqn);
            } else if (Math.abs(t - 1) < EPSILON) {
                res[i] = findZero(t, 1, eqn);
            }
        }
    }

    private static double solveEqn(double eqn[], int order, double t) {
        double v = eqn[order];
        while (--order >= 0) {
            v = v * t + eqn[order];
        }
        return v;
    }

    private static double findZero(double t, double target, double eqn[]) {
        double slopeqn[] = {eqn[1], 2*eqn[2], 3*eqn[3]};
        double slope;
        double origdelta = 0;
        double origt = t;
        while (true) {
            slope = solveEqn(slopeqn, 2, t);
            if (slope == 0) {
                // At a local minima - must return
                return t;
            }
            double y = solveEqn(eqn, 3, t);
            if (y == 0) {
                // Found it! - return it
                return t;
            }
            // assert(slope != 0 && y != 0);
            double delta = - (y / slope);
            // assert(delta != 0);
            if (origdelta == 0) {
                origdelta = delta;
            }
            if (t < target) {
                if (delta < 0) return t;
            } else if (t > target) {
                if (delta > 0) return t;
            } else { /* t == target */
                return (delta > 0
                        ? (target + java.lang.Double.MIN_VALUE)
                        : (target - java.lang.Double.MIN_VALUE));
            }
            double newt = t + delta;
            if (t == newt) {
                // The deltas are so small that we aren't moving...
                return t;
            }
            if (delta * origdelta < 0) {
                // We have reversed our path.
                int tag = (origt < t
                           ? getTag(target, origt, t)
                           : getTag(target, t, origt));
                if (tag != INSIDE) {
                    // Local minima found away from target - return the middle
                    return (origt + t) / 2;
                }
                // Local minima somewhere near target - move to target
                // and let the slope determine the resulting t.
                t = target;
            } else {
                t = newt;
            }
        }
    }
    
    private static final int BELOW = -2;
    private static final int LOWEDGE = -1;
    private static final int INSIDE = 0;
    private static final int HIGHEDGE = 1;
    private static final int ABOVE = 2;

    /*
     * Determine where coord lies with respect to the range from
     * low to high.  It is assumed that low <= high.  The return
     * value is one of the 5 values BELOW, LOWEDGE, INSIDE, HIGHEDGE,
     * or ABOVE.
     */
    private static int getTag(double coord, double low, double high) {
        if (coord <= low) {
            return (coord < low ? BELOW : LOWEDGE);
        }
        if (coord >= high) {
            return (coord > high ? ABOVE : HIGHEDGE);
        }
        return INSIDE;
    }

    public static int solveQuadratic(double eqn[]) {
        return solveQuadratic(eqn, eqn);
    }

    /**
     * Solves the quadratic whose coefficients are in the <code>eqn</code>
     * array and places the non-complex roots into the <code>res</code>
     * array, returning the number of roots.
     * The quadratic solved is represented by the equation:
     * <pre>
     *     eqn = {C, B, A};
     *     ax^2 + bx + c = 0
     * </pre>
     * A return value of <code>-1</code> is used to distinguish a constant
     * equation, which might be always 0 or never 0, from an equation that
     * has no zeroes.
     * @param eqn the specified array of coefficients to use to solve
     *        the quadratic equation
     * @param res the array that contains the non-complex roots
     *        resulting from the solution of the quadratic equation
     * @return the number of roots, or <code>-1</code> if the equation is
     *  a constant.
     * @since 1.3
     */
    public static int solveQuadratic(double eqn[], double res[]) {
        double a = eqn[2];
        double b = eqn[1];
        double c = eqn[0];
        int roots = 0;
        if (a == 0.0) {
            // The quadratic parabola has degenerated to a line.
            if (b == 0.0) {
                // The line has degenerated to a constant.
                return -1;
            }
            res[roots++] = -c / b;
        } else {
            // From Numerical Recipes, 5.6, Quadratic and Cubic Equations
            double d = b * b - 4.0 * a * c;
            if (d < 0.0) {
                // If d < 0.0, then there are no roots
                return 0;
            }
            d = Math.sqrt(d);
            // For accuracy, calculate one root using:
            //     (-b +/- d) / 2a
            // and the other using:
            //     2c / (-b +/- d)
            // Choose the sign of the +/- so that b+d gets larger in magnitude
            if (b < 0.0) {
                d = -d;
            }
            double q = (b + d) / -2.0;
            // We already tested a for being 0 above
            res[roots++] = q / a;
            if (q != 0.0) {
                res[roots++] = c / q;
            }
        }
        return roots;
    }
}
