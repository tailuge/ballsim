package org.motion.ballsimapp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Table implements IsSerializable {

	public String getId() {
		return id;
	}
	public String getPlayer() {
		return player;
	}
	public Table(String id, String player) {
		this.id = id;
		this.player = player;
	}
	
	String id;
	String player;
	
}
