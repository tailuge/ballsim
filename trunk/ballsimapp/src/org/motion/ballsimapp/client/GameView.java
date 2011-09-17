package org.motion.ballsimapp.client;

import org.motion.ballsim.physics.Table;


public interface GameView {

	void setPlayer(String playerId);
	void setAimCompleteHandler(AimNotify aimHandler);
	void setAnimationCompleteHandler(ViewNotify animationComplete);

	void appendMessage(String message);
	void aim(Table table, int timeout);
	void animate(Table shotSequence);
	
}
