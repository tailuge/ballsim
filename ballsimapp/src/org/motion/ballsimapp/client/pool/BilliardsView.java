package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.pool.handlers.AimNotify;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;


public interface BilliardsView {

	void setPlayer(String playerId);
	void setAimUpdateHandler(AimNotify aimHandler);
	void setAimCompleteHandler(AimNotify aimHandler);
	void setAnimationCompleteHandler(ViewNotify animationComplete);

	void appendMessage(String message);
	void showTable(Table table);
	void aim(int timeout);
	void animate(Table shotSequence);
	void setAim(Aim aim);
	
}
