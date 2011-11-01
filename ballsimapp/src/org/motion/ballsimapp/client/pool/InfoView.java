package org.motion.ballsimapp.client.pool;

import org.motion.ballsimapp.client.comms.GWTGameEventHandler;

public interface InfoView {

	void setEventHandler(GWTGameEventHandler eventHandler);

	void setVisibility(boolean visible);

	void setChatEnable(boolean enable);
	void appendMessage(String message);
	void clearMessage();
		
	String getPlayerId();
	String getPassword();
}
