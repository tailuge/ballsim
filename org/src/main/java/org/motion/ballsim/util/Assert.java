package org.motion.ballsim.util;

public final class Assert {

	static private boolean ignore = false;
	
	static public void isTrue(boolean test)
	{
		if (!ignore && !test)
			throw new IllegalArgumentException();
		
	}
}
