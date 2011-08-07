package org.oxtail.game.event;

/**
 * Call back interface for the server to push to the client
 * 
 * @author liam knox
 * 
 */
public interface EventCallback {

	void notify(GameEvent event);
}
