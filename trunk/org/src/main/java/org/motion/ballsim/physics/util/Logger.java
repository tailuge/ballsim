package org.motion.ballsim.physics.util;

public final class Logger {

	private final String name;
	private final boolean enabled;

	public Logger(String name, boolean enabled) {
		this.name = name;
		this.enabled = enabled;
	}

	public void info(String format) {
	}

	public void info(String format, Object arg) {
		if (enabled) {
			System.out.println(name); // todo
		}
	}

	public void info(String format, Object arg1, Object arg2) {
	}

	public void info(String format, Object[] argArray) {

	}

	public boolean isEnabled() {
		return enabled;
	}
}
