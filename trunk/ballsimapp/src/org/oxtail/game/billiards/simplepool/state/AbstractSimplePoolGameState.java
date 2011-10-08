package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

/**
 * Top level state for SimplePool
 */
public abstract class AbstractSimplePoolGameState extends
		AbstractGameState<SimplePoolGame, SimplePoolMove, SimplePoolTable> {

	public AbstractSimplePoolGameState(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	protected Player findOpponent() {
		Iterable<Player> others = getGameHome().findPlayers(
				new Predicate<Player>() {
					@Override
					public boolean apply(Player other) {
						return !other.equals(getInPlay())
								&& (PlayerState.valueOf(other.getState()) == PlayerState.AwaitingGame);
					}
				});
		// no one to play
		if (Iterables.isEmpty(others)) {
			return null;
		}
		return others.iterator().next();
	}

	private Iterable<Player> findAllLoggedIn() {
		return getGameHome().findPlayers(new Predicate<Player>() {
			@Override
			public boolean apply(Player other) {
				return !other.equals(getInPlay())
						&& (PlayerState.valueOf(other.getState()) != PlayerState.LoggedOut);
			}
		});
	}

	@Action
	public void chat() {
		GameEventHelper helper = new GameEventHelper(getGameEvent());
		String chatTo = helper.getString("chat.to");

		helper.setValue("state", "chatting");
		if (chatTo.equals("*")) {
			for (Player p : findAllLoggedIn())
				chatTo(p, helper);
		} else {
			Player to = getGameHome().findPlayer(chatTo);
			chatTo(to, helper);
		}
	}

	private void chatTo(Player player, GameEventHelper helper) {
		helper.setValue("chat.to", player.getAlias());
		player.onEvent(helper.getEvent());
	}

	protected final GameEvent newGameEvent() {
		return new GameEvent();
	}

	private GameEvent createEvent(String nameValues) {
		final GameEvent gameEvent = newGameEvent();
		for (String token : nameValues.split(",")) {
			final String[] tuple = token.split("=");
			gameEvent.addAttribute(new GameEventAttribute(tuple[0], tuple[1]));
		}
		return gameEvent;
	}

	protected GameEvent newStateEvent(String state) {
		return createEvent("state=" + state);
	}

	protected GameEvent newGameEvent(String state, boolean ballInHand) {
		SimplePoolGame game = getGame();
		GameEvent event = newStateEvent(state);

		event.addAttribute(new GameEventAttribute("player.inplay", game
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay", game
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion()
				.getId()));
		event.addAttribute(new GameEventAttribute("ballinhand", String
				.valueOf(ballInHand)));
		//
		if (getGameEvent().hasAttribute("game.table.state")) {
			event.addAttribute(getGameEvent().getAttribute("game.table.state"));
		}
		return event;
	}

	protected GameEvent newGameEvent(String state) {
		return newGameEvent(state, false);
	}

	protected GameEvent newGameFoulEvent(String state) {
		return newGameEvent(state, true);
	}

	protected void forceToLogin(Player player) {
		if (player != null) {
			PlayerState.LoggedIn.set(player);
			player.onEvent(newStateEvent("loggedin"));
		}
	}

	@Action
	public void requestWatchGames() {
		Player player = getInPlay();
		PlayerState.RequestedWatchGames.set(player);
		player.onEvent(newRequestWatchingGamesEvent());
	}

	protected GameEvent newRequestWatchingGamesEvent() {
		GameEvent event = newStateEvent("requestedwatchgames");
		GameEventHelper helper = new GameEventHelper(event);
		Predicate<Game<?>> all = Predicates.alwaysTrue();
		Iterable<Game<?>> allGames = getGameHome().findGames(all);

		helper.setValue("games.ids", allGames, Game.toId);
		helper.setValue("games.descriptions", allGames,
				SimplePoolGame.toDescription);

		return event;
	}

	/**
	 * Notifies all people requesting watching game of the current games in progress
	 */
	protected void notifyGamesInProgressUpdate() {
		for (Player player : playersRequestingToWatch()) {
			player.onEvent(newRequestWatchingGamesEvent());
		}
	}
	
	private Iterable<Player> playersRequestingToWatch() {
		return getGameHome().findPlayers(requestWatching());
	}
	
	private Predicate<Player> requestWatching() {
		
		return new Predicate<Player>() {
			@Override
			public boolean apply(Player player) {
				return PlayerState.RequestedWatchGames == PlayerState
						.safeValueOf(player.getState());
			}
		};
	}
	
}
