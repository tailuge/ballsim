package org.oxtail.game.server.event;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

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
		GameEventAttribute attribute = event.getAttribute(name);
		if (attribute == null)
			throw new IllegalArgumentException("no such attribute "+name);
		return attribute.getValue();
	}

	public Integer getInt(String name) {
		return Integer.parseInt(getString(name));
	}

	public boolean hasValue(String name) {
		return event.hasAttribute(name);
	}
}
