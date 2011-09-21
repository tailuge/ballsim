package org.motion.ballsimapp.shared;

/**
 * Interface for client to talk to server
 * 
 * @author kinko
 * 
 */
public interface GameServer {

	void notify(GameEvent event);
}
