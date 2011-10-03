package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;


public interface BilliardsView {

	void setEventHandler(GWTGameEventHandler eventHandler);

	void aim(int timeout);
	void place(int timeout);
	void watch();
	void setChatEnable(boolean enable);
	void appendMessage(String message);

	void showTable(Table table);
	void animate(Table shotSequence);
	void setAim(Aim aim);
	void showAim();
	void showPlacer();
	void setVisibility(boolean visible);
	
	String getPlayerId();
	String getPassword();
}
