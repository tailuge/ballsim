package org.motion.ballsimapp.client;

import org.motion.ballsim.physics.Table;


public interface GameView {

	void setPlayer(String playerId);
	void appendMessage(String message);
	void addAimCompleteHandler(AimHandler aimHandler);
	void aim(Table table, int timeout);
	void animate(Table shotSequence);
}
