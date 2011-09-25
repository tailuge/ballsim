package org.oxtail.game.model;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsimapp.shared.GameEvent;

/**
 * Top level Game, A unique instance of game
 */
public abstract class Game<T extends PlayingSpace> {

	private List<Player> players = new ArrayList<Player>();
	private GameVersion version;
	private T currentPlayingSpace;
	private StateId stateId;
	private Player inPlay;

	public Game(Player... ps) {
		for (Player p : ps)
			players.add(p);
	}

	public Game(T currentPlayingSpace, Player... ps) {
		this.currentPlayingSpace = currentPlayingSpace;
		for (Player p : ps)
			players.add(p);
	}

	public T getCurrentPlayingSpace() {
		return currentPlayingSpace;
	}

	protected final Player getPlayer(int index) {
		return players.get(index);
	}

	public final Player inPlay() {
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

	public String getId() {
		return version.getId();
	}

	public void setGameState(Class<?> k) {
		setStateId(new StateId(k));
	}

	/**
	 * Notifies all players in the game of an event
	 */
	public void notify(GameEvent event) {
		for (Player player : players) {
			player.onEvent(event.copy());
		}
	}

}