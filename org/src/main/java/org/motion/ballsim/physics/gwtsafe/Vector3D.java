package org.motion.ballsim.physics.gwtsafe;


/**
 * This class implements vectors in a three-dimensional space.
 * <p>
 * Instance of this class are guaranteed to be immutable.
 * </p>
 * 
 * @version $Revision: 922713 $ $Date: 2010-03-13 20:26:13 -0500 (Sat, 13 Mar
 *          2010) $
 * @since 1.2
 */

public final class Vector3D {

	public static long instanceCount = 0;
	
	/** Null vector (coordinates: 0, 0, 0). */
	public static final Vector3D ZERO = new Vector3D(0, 0, 0);

	/** First canonical vector (coordinates: 1, 0, 0). */
	public static final Vector3D PLUS_I = new Vector3D(1, 0, 0);

	/** Opposite of the first canonical vector (coordinates: -1, 0, 0). */
	public static final Vector3D MINUS_I = new Vector3D(-1, 0, 0);

	/** Second canonical vector (coordinates: 0, 1, 0). */
	public static final Vector3D PLUS_J = new Vector3D(0, 1, 0);

	/** Opposite of the second canonical vector (coordinates: 0, -1, 0). */
	public static final Vector3D MINUS_J = new Vector3D(0, -1, 0);

	/** Third canonical vector (coordinates: 0, 0, 1). */
	public static final Vector3D PLUS_K = new Vector3D(0, 0, 1);

	/** Opposite of the third canonical vector (coordinates: 0, 0, -1). */
	public static final Vector3D MINUS_K = new Vector3D(0, 0, -1);

	// CHECKSTYLE: stop ConstantName
	/** A vector with all coordinates set to NaN. */
	public static final Vector3D NaN = new Vector3D(Double.NaN, Double.NaN,
			Double.NaN);
	// CHECKSTYLE: resume ConstantName

	/** A vector with all coordinates set to positive infinity. */
	public static final Vector3D POSITIVE_INFINITY = new Vector3D(
			Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
			Double.POSITIVE_INFINITY);

	/** A vector with all coordinates set to negative infinity. */
	public static final Vector3D NEGATIVE_INFINITY = new Vector3D(
			Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
			Double.NEGATIVE_INFINITY);

	/** Default format. */
	// private static final Vector3DFormat DEFAULT_FORMAT =
	// Vector3DFormat.getInstance();

	/** Abscissa. */
	private final double x;

	/** Ordinate. */
	private final double y;

	/** Height. */
	private final double z;

	/**
	 * Simple constructor. Build a vector from its coordinates
	 * 
	 * @param x
	 *            abscissa
	 * @param y
	 *            ordinate
	 * @param z
	 *            height
	 * @see #getX()
	 * @see #getY()
	 * @see #getZ()
	 */
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		instanceCount++;
	}

	/**
	 * Simple constructor. Build a vector from its azimuthal coordinates
	 * 
	 * @param alpha
	 *            azimuth (&alpha;) around Z (0 is +X, &pi;/2 is +Y, &pi; is -X
	 *            and 3&pi;/2 is -Y)
	 * @param delta
	 *            elevation (&delta;) above (XY) plane, from -&pi;/2 to +&pi;/2
	 * @see #getAlpha()
	 * @see #getDelta()
	 */
	public Vector3D(double alpha, double delta) {
		double cosDelta = Math.cos(delta);
		this.x = Math.cos(alpha) * cosDelta;
		this.y = Math.sin(alpha) * cosDelta;
		this.z = Math.sin(delta);
		++instanceCount;
	}

	/**
	 * Get the abscissa of the vector.
	 * 
	 * @return abscissa of the vector
	 * @see #Vector3D(double, double, double)
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the ordinate of the vector.
	 * 
	 * @return ordinate of the vector
	 * @see #Vector3D(double, double, double)
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the height of the vector.
	 * 
	 * @return height of the vector
	 * @see #Vector3D(double, double, double)
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Get the L<sub>1</sub> norm for the vector.
	 * 
	 * @return L<sub>1</sub> norm for the vector
	 */
	public double getNorm1() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	/**
	 * Get the L<sub>2</sub> norm for the vector.
	 * 
	 * @return euclidian norm for the vector
	 */
	public double getNorm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Get the square of the norm for the vector.
	 * 
	 * @return square of the euclidian norm for the vector
	 */
	public double getNormSq() {
		return x * x + y * y + z * z;
	}

	/**
	 * Get the L<sub>&infin;</sub> norm for the vector.
	 * 
	 * @return L<sub>&infin;</sub> norm for the vector
	 */
	public double getNormInf() {
		return Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));
	}

	/**
	 * Get the azimuth of the vector.
	 * 
	 * @return azimuth (&alpha;) of the vector, between -&pi; and +&pi;
	 * @see #Vector3D(double, double)
	 */
	public double getAlpha() {
		return Math.atan2(y, x);
	}

	/**
	 * Get the elevation of the vector.
	 * 
	 * @return elevation (&delta;) of the vector, between -&pi;/2 and +&pi;/2
	 * @see #Vector3D(double, double)
	 */
	public double getDelta() {
		return Math.asin(z / getNorm());
	}

	/**
	 * Add a vector to the instance.
	 * 
	 * @param v
	 *            vector to add
	 * @return a new vector
	 */
	public Vector3D add(Vector3D v) {
		return new Vector3D(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * Add a scaled vector to the instance.
	 * 
	 * @param factor
	 *            scale factor to apply to v before adding it
	 * @param v
	 *            vector to add
	 * @return a new vector
	 */
	public Vector3D add(double factor, Vector3D v) {
		return new Vector3D(x + factor * v.x, y + factor * v.y, z + factor
				* v.z);
	}

	public Vector3D addScaledPair(double factor, Vector3D v,double factor2, Vector3D v2) {
		return new Vector3D(x + factor * v.x + factor2 * v2.x, y + factor * v.y + factor2* v2.y, z + factor
				* v.z + factor2 * v2.z);
	}

	/**
	 * Subtract a vector from the instance.
	 * 
	 * @param v
	 *            vector to subtract
	 * @return a new vector
	 */
	public Vector3D subtract(Vector3D v) {
		return new Vector3D(x - v.x, y - v.y, z - v.z);
	}

	/**
	 * Subtract a scaled vector from the instance.
	 * 
	 * @param factor
	 *            scale factor to apply to v before subtracting it
	 * @param v
	 *            vector to subtract
	 * @return a new vector
	 */
	public Vector3D subtract(double factor, Vector3D v) {
		return new Vector3D(x - factor * v.x, y - factor * v.y, z - factor
				* v.z);
	}

	/**
	 * Get a normalized vector aligned with the instance.
	 * 
	 * @return a new normalized vector
	 * @throws MathRuntimeException
	 * @exception ArithmeticException
	 *                if the norm is zero
	 */
	public Vector3D normalize() throws ArithmeticException {
		double s = getNorm();
		if (s == 0) {
			throw new ArithmeticException();
		}
		return scalarMultiply(1 / s);
	}

	public Vector3D unitScale(double scale)
	{
		double s = getNorm();
		if (s == 0) {
			return Vector3D.ZERO;
		}
		return scalarMultiply(scale / s);		
	}
	
	/**
	 * Get a vector orthogonal to the instance.
	 * <p>
	 * There are an infinite number of normalized vectors orthogonal to the
	 * instance. This method picks up one of them almost arbitrarily. It is
	 * useful when one needs to compute a reference frame with one of the axes
	 * in a predefined direction. The following example shows how to build a
	 * frame having the k axis aligned with the known vector u :
	 * 
	 * <pre>
	 * <code>
	 *   Vector3D k = u.normalize();
	 *   Vector3D i = k.orthogonal();
	 *   Vector3D j = Vector3D.crossProduct(k, i);
	 * </code>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @return a new normalized vector orthogonal to the instance
	 * @throws MathRuntimeException
	 * @exception ArithmeticException
	 *                if the norm of the instance is null
	 */
	public Vector3D orthogonal() throws MathRuntimeException {

		double threshold = 0.6 * getNorm();
		if (threshold == 0) {
			throw new MathRuntimeException();
		}

		if ((x >= -threshold) && (x <= threshold)) {
			double inverse = 1 / Math.sqrt(y * y + z * z);
			return new Vector3D(0, inverse * z, -inverse * y);
		} else if ((y >= -threshold) && (y <= threshold)) {
			double inverse = 1 / Math.sqrt(x * x + z * z);
			return new Vector3D(-inverse * z, 0, inverse * x);
		}
		double inverse = 1 / Math.sqrt(x * x + y * y);
		return new Vector3D(inverse * y, -inverse * x, 0);

	}

	/**
	 * Compute the angular separation between two vectors.
	 * <p>
	 * This method computes the angular separation between two vectors using the
	 * dot product for well separated vectors and the cross product for almost
	 * aligned vectors. This allows to have a good accuracy in all cases, even
	 * for vectors very close to each other.
	 * </p>
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return angular separation between v1 and v2
	 * @throws MathRuntimeException
	 * @exception ArithmeticException
	 *                if either vector has a null norm
	 */
	public static double angle(Vector3D v1, Vector3D v2)
			throws MathRuntimeException {

		double normProduct = v1.getNorm() * v2.getNorm();
		if (normProduct == 0) {
			throw new MathRuntimeException();
		}

		double dot = dotProduct(v1, v2);
		double threshold = normProduct * 0.9999;
		if ((dot < -threshold) || (dot > threshold)) {
			// the vectors are almost aligned, compute using the sine
			Vector3D v3 = crossProduct(v1, v2);
			if (dot >= 0) {
				return Math.asin(v3.getNorm() / normProduct);
			}
			return Math.PI - Math.asin(v3.getNorm() / normProduct);
		}

		// the vectors are sufficiently separated to use the cosine
		return Math.acos(dot / normProduct);

	}

	/**
	 * Get the opposite of the instance.
	 * 
	 * @return a new vector which is opposite to the instance
	 */
	public Vector3D negate() {
		return new Vector3D(-x, -y, -z);
	}

	/**
	 * Multiply the instance by a scalar
	 * 
	 * @param a
	 *            scalar
	 * @return a new vector
	 */
	public Vector3D scalarMultiply(double a) {
		return new Vector3D(a * x, a * y, a * z);
	}

	/**
	 * Returns true if any coordinate of this vector is NaN; false otherwise
	 * 
	 * @return true if any coordinate of this vector is NaN; false otherwise
	 */
	public boolean isNaN() {
		return Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z);
	}

	/**
	 * Returns true if any coordinate of this vector is infinite and none are
	 * NaN; false otherwise
	 * 
	 * @return true if any coordinate of this vector is infinite and none are
	 *         NaN; false otherwise
	 */
	public boolean isInfinite() {
		return !isNaN()
				&& (Double.isInfinite(x) || Double.isInfinite(y) || Double
						.isInfinite(z));
	}

	/**
	 * Test for the equality of two 3D vectors.
	 * <p>
	 * If all coordinates of two 3D vectors are exactly the same, and none are
	 * <code>Double.NaN</code>, the two 3D vectors are considered to be equal.
	 * </p>
	 * <p>
	 * <code>NaN</code> coordinates are considered to affect globally the vector
	 * and be equals to each other - i.e, if either (or all) coordinates of the
	 * 3D vector are equal to <code>Double.NaN</code>, the 3D vector is equal to
	 * {@link #NaN}.
	 * </p>
	 * 
	 * @param other
	 *            Object to test for equality to this
	 * @return true if two 3D vector objects are equal, false if object is null,
	 *         not an instance of Vector3D, or not equal to this Vector3D
	 *         instance
	 * 
	 */
	@Override
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		}

		if (other instanceof Vector3D) {
			final Vector3D rhs = (Vector3D) other;
			if (rhs.isNaN()) {
				return this.isNaN();
			}

			return (x == rhs.x) && (y == rhs.y) && (z == rhs.z);
		}
		return false;
	}

	/**
	 * Get a hashCode for the 3D vector.
	 * <p>
	 * All NaN values have the same hash code.
	 * </p>
	 * 
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		if (isNaN()) {
			return 8;
		}
		return (int) (31 * (23 * x + 19 * y + z));
	}

	/**
	 * Compute the dot-product of two vectors.
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return the dot product v1.v2
	 */
	public static double dotProduct(Vector3D v1, Vector3D v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	/**
	 * Compute the cross-product of two vectors.
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return the cross product v1 ^ v2 as a new Vector
	 */
	public static Vector3D crossProduct(Vector3D v1, Vector3D v2) {
		return new Vector3D(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x
				* v2.z, v1.x * v2.y - v1.y * v2.x);
	}

	public static Vector3D crossProduct(double factor,Vector3D v1, Vector3D v2) {
		return new Vector3D(factor*(v1.y * v2.z - v1.z * v2.y), factor*(v1.z * v2.x - v1.x
				* v2.z), factor*(v1.x * v2.y - v1.y * v2.x));
	}

	/**
	 * Compute the distance between two vectors according to the L<sub>1</sub>
	 * norm.
	 * <p>
	 * Calling this method is equivalent to calling:
	 * <code>v1.subtract(v2).getNorm1()</code> except that no intermediate
	 * vector is built
	 * </p>
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return the distance between v1 and v2 according to the L<sub>1</sub>
	 *         norm
	 */
	public static double distance1(Vector3D v1, Vector3D v2) {
		final double dx = Math.abs(v2.x - v1.x);
		final double dy = Math.abs(v2.y - v1.y);
		final double dz = Math.abs(v2.z - v1.z);
		return dx + dy + dz;
	}

	/**
	 * Compute the distance between two vectors according to the L<sub>2</sub>
	 * norm.
	 * <p>
	 * Calling this method is equivalent to calling:
	 * <code>v1.subtract(v2).getNorm()</code> except that no intermediate vector
	 * is built
	 * </p>
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return the distance between v1 and v2 according to the L<sub>2</sub>
	 *         norm
	 */
	public static double distance(Vector3D v1, Vector3D v2) {
		final double dx = v2.x - v1.x;
		final double dy = v2.y - v1.y;
		final double dz = v2.z - v1.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * Compute the distance between two vectors according to the
	 * L<sub>&infin;</sub> norm.
	 * <p>
	 * Calling this method is equivalent to calling:
	 * <code>v1.subtract(v2).getNormInf()</code> except that no intermediate
	 * vector is built
	 * </p>
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return the distance between v1 and v2 according to the
	 *         L<sub>&infin;</sub> norm
	 */
	public static double distanceInf(Vector3D v1, Vector3D v2) {
		final double dx = Math.abs(v2.x - v1.x);
		final double dy = Math.abs(v2.y - v1.y);
		final double dz = Math.abs(v2.z - v1.z);
		return Math.max(Math.max(dx, dy), dz);
	}

	/**
	 * Compute the square of the distance between two vectors.
	 * <p>
	 * Calling this method is equivalent to calling:
	 * <code>v1.subtract(v2).getNormSq()</code> except that no intermediate
	 * vector is built
	 * </p>
	 * 
	 * @param v1
	 *            first vector
	 * @param v2
	 *            second vector
	 * @return the square of the distance between v1 and v2
	 */
	public static double distanceSq(Vector3D v1, Vector3D v2) {
		final double dx = v2.x - v1.x;
		final double dy = v2.y - v1.y;
		final double dz = v2.z - v1.z;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * Get a string representation of this vector.
	 * 
	 * @return a string representation of this vector
	 */
	@Override
	public String toString() {
		return "[" + x + "," + y + "," + z + "]";
	}

}
