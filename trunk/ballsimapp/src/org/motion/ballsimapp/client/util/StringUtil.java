package org.motion.ballsimapp.client.util;

/**
 * String Utilities
 */
public final class StringUtil {
	private StringUtil(){};
	
	public final static String toString(Iterable<String> i) {
		StringBuilder sb = new StringBuilder();
		for (String s : i) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(s);
		}
		return sb.toString();
	}

}
