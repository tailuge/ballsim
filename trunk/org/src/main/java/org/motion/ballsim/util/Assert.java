package org.motion.ballsim.util;

public class Assert {

	static public void isTrue(boolean test)
	{
		if (!test)
			throw new IllegalArgumentException();
	}
}
