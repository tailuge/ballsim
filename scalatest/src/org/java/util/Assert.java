package org.java.util;


/**
 * Assertion Utilities
 * @author kinko
 *
 */
public class Assert {

	public static final void assertGreaterThanZero(int i, String message) {
		if (i <= 0)
			throw new IllegalArgumentException(i + "<=0," + message);
	}

	public static final void assertGreaterThanOrEqualToZero(int i,
			String message) {
		if (i < 0)
			throw new IllegalArgumentException(i + "<0, " + message);
	}

	public static final void assertBetweenRangeInclusive(int start, int end,
			int i) {
		assertBetweenRangeInclusive(start, end, i, "");
	}

	public static final void assertBetweenRangeInclusive(int start, int end,
			int i, String message) {
		if (!(i >= start && i <= end))
			throw new IllegalArgumentException(i
					+ " is not in inclusive range " + start + " to " + end
					+ "," + message);
	}

}
