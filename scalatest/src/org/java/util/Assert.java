package org.java.util;

public class Assert {

	public static final void assertGreaterThanZero(int i, String message) {
		if (i <= 0)
			throw new IllegalArgumentException(i + "<=0,"+message);
	}

	public static final void assertGreaterThanOrEqualToZero(int i, String message) {
		if (i < 0)
			throw new IllegalArgumentException(i + "<0, "+message);
	}

}
