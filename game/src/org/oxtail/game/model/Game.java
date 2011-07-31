package org.oxtail.game.model;

import java.util.List;

import org.oxtail.game.state.AbstractGameState;

/**
 * Top level Game, A unique instance of a games
 * 
 */
public abstract class Game<T extends PlayingSpace> {

	private List<Player> players;
	private GameVersion version;
	private T currentPlayingSpace;
	private AbstractGameState<T> state;

	public T getCurrentPlayingSpace() {
		return currentPlayingSpace;
	}

	public AbstractGameState<T> getState() {
		return state;
	}

	public void setState(AbstractGameState<T> state) {
		this.state = state;
	}

}