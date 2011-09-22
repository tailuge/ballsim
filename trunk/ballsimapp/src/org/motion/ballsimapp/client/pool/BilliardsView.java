package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.Table;


public interface BilliardsView {

	void setPlayer(String playerId);
	void setAimUpdateHandler(AimNotify aimHandler);
	void setAimCompleteHandler(AimNotify aimHandler);
	void setAnimationCompleteHandler(ViewNotify animationComplete);

	void appendMessage(String message);
	void aim(Table table, int timeout);
	void animate(Table shotSequence);
	
}
