package org.motion.ballsim.gwtsafe;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.io.Serializable;


/**
 * This class implements rotations in a three-dimensional space.
 *
 * <p>Rotations can be represented by several different mathematical
 * entities (matrices, axe and angle, Cardan or Euler angles,
 * quaternions). This class presents an higher level abstraction, more
 * user-oriented and hiding this implementation details. Well, for the
 * curious, we use quaternions for the internal representation. The
 * user can build a rotation from any of these representations, and
 * any of these representations can be retrieved from a
 * <code>Rotation</code> instance (see the various constructors and
 * getters). In addition, a rotation can also be built implicitely
 * from a set of vectors and their image.</p>
 * <p>This implies that this class can be used to convert from one
 * representation to another one. For example, converting a rotation
 * matrix into a set of Cardan angles from can be done using the
 * followong single line of code:</p>
 * <pre>
 * double[] angles = new Rotation(matrix, 1.0e-10).getAngles(RotationOrder.XYZ);
 * </pre>
 * <p>Focus is oriented on what a rotation <em>do</em> rather than on its
 * underlying representation. Once it has been built, and regardless of its
 * internal representation, a rotation is an <em>operator</em> which basically
 * transforms three dimensional {@link Vector3D vectors} into other three
 * dimensional {@link Vector3D vectors}. Depending on the application, the
 * meaning of these vectors may vary and the semantics of the rotation also.</p>
 * <p>For example in an spacecraft attitude simulation tool, users will often
 * consider the vectors are fixed (say the Earth direction for example) and the
 * rotation transforms the coordinates coordinates of this vector in inertial
 * frame into the coordinates of the same vector in satellite frame. In this
 * case, the rotation implicitely defines the relation between the two frames.
 * Another example could be a telescope control application, where the rotation
 * would transform the sighting direction at rest into the desired observing
 * direction when the telescope is pointed towards an object of interest. In this
 * case the rotation transforms the directionf at rest in a topocentric frame
 * into the sighting direction in the same topocentric frame. In many case, both
 * approaches will be combined, in our telescope example, we will probably also
 * need to transform the observing direction in the topocentric frame into the
 * observing direction in inertial frame taking into account the observatory
 * location and the Earth rotation.</p>
 *
 * <p>These examples show that a rotation is what the user wants it to be, so this
 * class does not push the user towards one specific definition and hence does not
 * provide methods like <code>projectVectorIntoDestinationFrame</code> or
 * <code>computeTransformedDirection</code>. It provides simpler and more generic
 * methods: {@link #applyTo(Vector3D) applyTo(Vector3D)} and {@link
 * #applyInverseTo(Vector3D) applyInverseTo(Vector3D)}.</p>
 *
 * <p>Since a rotation is basically a vectorial operator, several rotations can be
 * composed together and the composite operation <code>r = r<sub>1</sub> o
 * r<sub>2</sub></code> (which means that for each vector <code>u</code>,
 * <code>r(u) = r<sub>1</sub>(r<sub>2</sub>(u))</code>) is also a rotation. Hence
 * we can consider that in addition to vectors, a rotation can be applied to other
 * rotations as well (or to itself). With our previous notations, we would say we
 * can apply <code>r<sub>1</sub></code> to <code>r<sub>2</sub></code> and the result
 * we get is <code>r = r<sub>1</sub> o r<sub>2</sub></code>. For this purpose, the
 * class provides the methods: {@link #applyTo(Rotation) applyTo(Rotation)} and
 * {@link #applyInverseTo(Rotation) applyInverseTo(Rotation)}.</p>
 *
 * <p>Rotations are guaranteed to be immutable objects.</p>
 *
 * @version $Revision: 772119 $ $Date: 2009-05-06 05:43:28 -0400 (Wed, 06 May 2009) $
 * @see Vector3D
 * @see RotationOrder
 * @since 1.2
 */

public final class Rotation implements Serializable {

  /** Identity rotation. */
  public static final Rotation IDENTITY = new Rotation(1.0, 0.0, 0.0, 0.0);

  /** Serializable version identifier */
  private static final long serialVersionUID = -2153622329907944313L;

  /** Scalar coordinate of the quaternion. */
  private final double q0;

  /** First coordinate of the vectorial part of the quaternion. */
  private final double q1;

  /** Second coordinate of the vectorial part of the quaternion. */
  private final double q2;

  /** Third coordinate of the vectorial part of the quaternion. */
  private final double q3;

  /** Build a rotation from the quaternion coordinates.
   * <p>A rotation can be built from a <em>normalized</em> quaternion,
   * i.e. a quaternion for which q<sub>0</sub><sup>2</sup> +
   * q<sub>1</sub><sup>2</sup> + q<sub>2</sub><sup>2</sup> +
   * q<sub>3</sub><sup>2</sup> = 1. If the quaternion is not normalized,
   * the constructor can normalize it in a preprocessing step.</p>
   * @param q0 scalar part of the quaternion
   * @param q1 first coordinate of the vectorial part of the quaternion
   * @param q2 second coordinate of the vectorial part of the quaternion
   * @param q3 third coordinate of the vectorial part of the quaternion
   * @param needsNormalization if true, the coordinates are considered
   * not to be normalized, a normalization preprocessing step is performed
   * before using them
   */
  public Rotation(double q0, double q1, double q2, double q3) {

    this.q0 = q0;
    this.q1 = q1;
    this.q2 = q2;
    this.q3 = q3;

  }

  /** Build a rotation from an axis and an angle.
   * <p>We use the convention that angles are oriented according to
   * the effect of the rotation on vectors around the axis. That means
   * that if (i, j, k) is a direct frame and if we first provide +k as
   * the axis and PI/2 as the angle to this constructor, and then
   * {@link #applyTo(Vector3D) apply} the instance to +i, we will get
   * +j.</p>
   * @param axis axis around which to rotate
   * @param angle rotation angle.
   * @exception ArithmeticException if the axis norm is zero
   */
  public Rotation(Vector3D axis, double angle) {

    double norm = axis.getNorm();
    if (norm == 0) {
      throw new MathRuntimeException();
    }

    double halfAngle = -0.5 * angle;
    double coeff = Math.sin(halfAngle) / norm;

    q0 = Math.cos (halfAngle);
    q1 = coeff * axis.getX();
    q2 = coeff * axis.getY();
    q3 = coeff * axis.getZ();

  }



  /** Apply the rotation to a vector.
   * @param u vector to apply the rotation to
   * @return a new vector which is the image of u by the rotation
   */
  public Vector3D applyTo(Vector3D u) {

    double x = u.getX();
    double y = u.getY();
    double z = u.getZ();

    double s = q1 * x + q2 * y + q3 * z;

    return new Vector3D(2 * (q0 * (x * q0 - (q2 * z - q3 * y)) + s * q1) - x,
                        2 * (q0 * (y * q0 - (q3 * x - q1 * z)) + s * q2) - y,
                        2 * (q0 * (z * q0 - (q1 * y - q2 * x)) + s * q3) - z);

  }


  /** Apply the instance to another rotation.
   * Applying the instance to a rotation is computing the composition
   * in an order compliant with the following rule : let u be any
   * vector and v its image by r (i.e. r.applyTo(u) = v), let w be the image
   * of v by the instance (i.e. applyTo(v) = w), then w = comp.applyTo(u),
   * where comp = applyTo(r).
   * @param r rotation to apply the rotation to
   * @return a new rotation which is the composition of r by the instance
   */
  public Rotation applyTo(Rotation r) {
    return new Rotation(r.q0 * q0 - (r.q1 * q1 + r.q2 * q2 + r.q3 * q3),
                        r.q1 * q0 + r.q0 * q1 + (r.q2 * q3 - r.q3 * q2),
                        r.q2 * q0 + r.q0 * q2 + (r.q3 * q1 - r.q1 * q3),
                        r.q3 * q0 + r.q0 * q3 + (r.q1 * q2 - r.q2 * q1)
                       );
  }

 
 
}
