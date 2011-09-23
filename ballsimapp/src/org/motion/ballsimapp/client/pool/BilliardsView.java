package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.pool.handlers.AimNotify;
import org.motion.ballsimapp.client.pool.handlers.ViewNotify;


public interface BilliardsView {

	void setPlayer(String playerId);
	void setAimHandler(AimNotify aimHandler);
	void setAnimationCompleteHandler(ViewNotify animationComplete);

	void appendMessage(String message);
	void showTable(Table table);
	void aim(int timeout);
	void animate(Table shotSequence);
	void setAim(Aim aim);
	void setPlacer(Vector3D pos);
	
}
