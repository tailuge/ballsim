package org.motion.ballsim.physics.gwtsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Quartic {

	/**
	 * Differentiate once to find min/max points using cubic equation. Use
	 * Newton solver to inspect each of these spans to see which contain roots.
	 * Return the least positive one.
	 * 
	 * @param coeffs
	 * @param max
	 * @return
	 */
	public static double smallestRoot(double[] coeffs, double max) {
		PolynomialFunction p = new PolynomialFunction(coeffs);

		// solve derivative cubic to get min/max points

		double cubicCoeffs[] = p.polynomialDerivative().getCoefficients();

		double res[] = new double[3];
		int cubicRoots = 0;
		if (cubicCoeffs.length > 2) {
			cubicRoots = Quadratic.solveCubic(cubicCoeffs, res);
		}
		// create set of spans that root must be between if present

		List<Double> spans = new ArrayList<Double>();

		spans.add(0.0);
		spans.add(max);
		for (int i = 0; i < cubicRoots; i++)
			if ((res[i] < max) && (res[i] > 0))
				spans.add(res[i]);

		Collections.sort(spans);

		// System.out.println("spans:"+spans);

		// look for root in each span.
		// stop when found smallest

		double last = 0.0;
		for (double s : spans) {
			if (last != s) {
				double root = solveOrInf(p, last, s);
				if ((root > last) && (root < s)) {
					return confirmNoSmallerRoot(p, root);
				}
			}
			last = s;
		}

		return 0;

	}

	private static double confirmNoSmallerRoot(PolynomialFunction p,
			double candidate) {
		double smaller = Quadratic.nextSmallest(candidate);

		double smallerRoot = solveOrInf(p, 0, smaller);
		if (smallerRoot == Double.POSITIVE_INFINITY)
			return candidate;
		return smallerRoot;
	}

	public static double evalAt(double[] coeff, double t) {
		return new PolynomialFunction(coeff).value(t);
	}

	public static String print(double[] coeff) {
		return new PolynomialFunction(coeff).toString();
	}

	/*
	 * Bisection method imported from apache-commons-math WITH NO EXCEPTIONS -
	 * returns Double.POSITIVY_INFINITY if no root
	 */

	public static double solveOrInf(final PolynomialFunction f, double min,
			double max) {

		double m;
		double fm;
		double fmin;

		if (f.value(min) * f.value(max) > 0)
			return Double.POSITIVE_INFINITY;

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

		return Double.POSITIVE_INFINITY;

	}

	/*
	 * Bisection method imported from apache-commons-math
	 */

	private static final int maximalIterationCount = 48;
	private static final double absoluteAccuracy = 0.00000001;

	public static double solve(final PolynomialFunction f, double min,
			double max) throws MaxIterationsExceededException {

		double m;
		double fm;
		double fmin;

		if (f.value(min) * f.value(max) > 0)
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