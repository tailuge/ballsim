package org.oxtail.game.model;

import java.util.List;

import org.motion.ballsimapp.shared.GameEvent;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * Top level Game, A unique instance of game
 */
public abstract class Game<T extends PlayingSpace> {

	public static Function<Game<?>, String> toId = new Function<Game<?>, String>() {
		@Override
		public String apply(Game<?> game) {
			return game.getId();
		}

	};

	private final List<Player> players = Lists.newArrayList();
	private GameVersion version;
	private T currentPlayingSpace;
	private StateId stateId;
	private Player inPlay;
	private GameEvent gameEvent;

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

	public final Player getPlayer(int index) {
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

	public Player[] playersAsArray() {
		return players.toArray(new Player[0]);
	}

	public GameEvent getGameEvent() {
		return gameEvent;
	}

	public void setGameEvent(GameEvent gameEvent) {
		this.gameEvent = gameEvent;
	}

}