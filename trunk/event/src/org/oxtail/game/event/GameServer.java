package org.oxtail.game.event;

/**
 * Interface for client to talk to server
 */
public interface GameServer {

	void notify(GameEvent event);
}
