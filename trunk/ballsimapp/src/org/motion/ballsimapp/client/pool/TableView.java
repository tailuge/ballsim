package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

public interface TableView {

	void setEventHandler(GWTGameEventHandler eventHandler);

	void aim(int timeout);
	void place(int timeout);
	void watch();
	void showTable(Table table);
	void animate(Table shotSequence);
	void plotAtTime(Table table, double t);
	void setAim(Aim aim);
	void showAim();
	void showPlacer();
	void setVisibility(boolean visible);
	
}
