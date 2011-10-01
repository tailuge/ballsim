package org.motion.ballsim.util;

public final class Guard {

	static public boolean active = false;
	
	static public void isTrue(boolean value)
	{
		if (active && !value)
			throw new IllegalArgumentException();
	}
}
