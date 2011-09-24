package org.oxtail.game.model;

import java.util.Date;

/**
 * Unique identification of a Game
 * @author liam knox
 */
public class GameVersion {

	private final String id;
	private final Date startDate;
	
	public GameVersion(String id, Date startDate) {
		this.id = id;
		this.startDate = startDate;
	}

	public String getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	

}
