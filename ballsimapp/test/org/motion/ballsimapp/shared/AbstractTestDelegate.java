package org.motion.ballsimapp.shared;

import java.util.logging.Logger;

public abstract class AbstractTestDelegate {

	protected final Logger log;

	protected final Object owner;

	protected AbstractTestDelegate(String name, Object owner) {
		log = Logger.getLogger(name);
		this.owner = owner;
	}

	protected AbstractTestDelegate(String name) {
		this(name, null);
	}

	protected final long now() {
		return System.currentTimeMillis();
	}

}
