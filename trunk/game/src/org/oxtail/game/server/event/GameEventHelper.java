package org.oxtail.game.server.event;

import org.oxtail.game.event.GameEvent;

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
		return event.getAttribute(name).getValue();

	}
}
