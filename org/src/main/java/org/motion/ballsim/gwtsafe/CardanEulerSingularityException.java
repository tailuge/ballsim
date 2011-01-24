package org.motion.ballsim.gwtsafe;

import org.apache.commons.math.MathException;

/** This class represents exceptions thrown while extractiong Cardan
 * or Euler angles from a rotation.

 * @version $Revision: 811827 $ $Date: 2009-09-06 11:32:50 -0400 (Sun, 06 Sep 2009) $
 * @since 1.2
 */
public class CardanEulerSingularityException
  extends MathException {

    /** Serializable version identifier */
    private static final long serialVersionUID = -1360952845582206770L;

    /**
     * Simple constructor.
     * build an exception with a default message.
     * @param isCardan if true, the rotation is related to Cardan angles,
     * if false it is related to EulerAngles
     */
    public CardanEulerSingularityException(boolean isCardan) {
        super(isCardan ? "Cardan angles singularity" : "Euler angles singularity");
    }

}
