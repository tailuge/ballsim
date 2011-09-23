package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;


public interface BilliardsView {

	void setPlayer(String playerId);
	void setEventHandler(GWTGameEventHandler eventHandler);

	void aim(int timeout);
	void place(int timeout);
	void appendMessage(String message);

	void showTable(Table table);
	void animate(Table shotSequence);
	void setAim(Aim aim);
	void setPlacer(Vector3D pos);
	
}
