package org.oxtail.game.event;

/**
 * Call back interface for the server to push to the client
 * 
 * @author liam knox
 * 
 */
public interface GameEventCallback {

	void onEvent(GameEvent event);
}
