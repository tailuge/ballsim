package org.motion.ballsimapp.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	protected static void readLine() throws IOException {
		new BufferedReader(new InputStreamReader(System.in)).readLine();
	}
}
