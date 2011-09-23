package org.motion.ballsimapp.client.pool.handlers;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;

public interface AimNotify {
	void handleAimUpdate(Aim aim);
	void handleAimComplete(Aim aim);
	void handlePlaceBallUpdate(Vector3D pos);
	void handlePlaceBallComplete(Vector3D pos);
}
