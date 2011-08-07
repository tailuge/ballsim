package org.oxtail.game.event;

/**
 * Communication packet between client and server
 */
public class GameEvent {

	private String action;
	private String playerAlias;
	private String gameId;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPlayerAlias() {
		return playerAlias;
	}
	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}


}
