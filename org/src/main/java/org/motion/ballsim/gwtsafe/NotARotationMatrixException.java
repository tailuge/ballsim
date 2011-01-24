package org.motion.ballsim.gwtsafe;

import org.apache.commons.math.MathException;

/**
 * This class represents exceptions thrown while building rotations
 * from matrices.
 *
 * @version $Revision: 811827 $ $Date: 2009-09-06 11:32:50 -0400 (Sun, 06 Sep 2009) $
 * @since 1.2
 */

public class NotARotationMatrixException
  extends MathException {

    /** Serializable version identifier */
    private static final long serialVersionUID = 5647178478658937642L;

    /**
     * Simple constructor.
     * Build an exception by translating and formating a message
     * @param specifier format specifier (to be translated)
     * @param parts to insert in the format (no translation)
     */
    public NotARotationMatrixException(String specifier, Object ... parts) {
        super(specifier, parts);
    }

}
