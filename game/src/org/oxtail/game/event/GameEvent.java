package org.oxtail.game.event;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Communication packet between client and server
 */
public class GameEvent implements Serializable {

	
	private String action;
	private String eventFrom;
	private String eventTo;
	private String gameId;
	private List<Attribute> attributes;

	public GameEvent(String action, String eventFrom, String eventTo) {
		this.action = action;
		this.eventFrom = eventFrom;
		this.eventTo = eventTo;
	}

	public static GameEvent toSelf(String player, String action) {
		return new GameEvent(action, player, player);
	}

	public static GameEvent toOther(String player, String other, String action) {
		return new GameEvent(action, player, other);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEventFrom() {
		return eventFrom;
	}

	public void setEventFrom(String eventFrom) {
		this.eventFrom = eventFrom;
	}

	public String getEventTo() {
		return eventTo;
	}

	public void setEventTo(String eventTo) {
		this.eventTo = eventTo;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public boolean hasGameId() {
		return gameId != null;
	}
}
