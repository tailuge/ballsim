package org.oxtail.game.model;

import java.util.List;

/**
 * Top level Game, A unique instance of a games
 * 
 */
public abstract class Game<T extends PlayingSpace> {

	private List<Player> players;
	private GameVersion version;
	private T currentPlayingSpace;
	private StateId stateId;
	private Player inPlay;

	public T getCurrentPlayingSpace() {
		return currentPlayingSpace;
	}

	protected final Player getPlayer(int index) {
		return players.get(index);
	}

	public final Player getInPlay() {
		return inPlay;
	}

	public final void setInPlay(Player inPlay) {
		this.inPlay = inPlay;
	}

	public final StateId getStateId() {
		return stateId;
	}

	public final void setStateId(StateId stateId) {
		this.stateId = stateId;
	}

	public final GameVersion getVersion() {
		return version;
	}

	public final void setVersion(GameVersion version) {
		this.version = version;
	}

	
}