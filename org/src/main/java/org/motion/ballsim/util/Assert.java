package org.motion.ballsim.util;

public class Assert {

	static private boolean ignore = true;
	
	static public void isTrue(boolean test)
	{
		if (!ignore && !test)
			throw new IllegalArgumentException();
		
	}
}
