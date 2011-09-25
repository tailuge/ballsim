package org.oxtail.game.server.event;

import org.motion.ballsimapp.shared.GameEvent;

/**
 * Server side helper for the {@link GameEvent}, to simplify access without
 * complicating the communication classes
 */
public class GameEventHelper {

	private final GameEvent event;

	public GameEventHelper(GameEvent event) {
		super();
		this.event = event;
	}

	public String getString(String name) {
		final String value = event.getAttribute(name).getValue();
		if (value == null)
			throw new IllegalArgumentException("no such value "+name);
		return value;
	}

	public Integer getInt(String name) {
		return Integer.parseInt(getString(name));
	}

	public boolean hasValue(String name) {
		return event.hasAttribute(name);
	}
}
