package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

import com.google.common.base.Predicate;
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

	@Action
	public void chat() {
		// do nothing by default
	}

	protected GameEvent event() {
		return new GameEvent();
	}

	protected GameEvent event(String nameValues) {
		final GameEvent gameEvent = event();
		for (String token : nameValues.split(",")) {
			final String[] tuple = token.split("=");
			gameEvent.addAttribute(new GameEventAttribute(tuple[0], tuple[1]));
		}
		return gameEvent;
	}

	protected GameEvent newGameEvent(String state, boolean ballInHand) {
		SimplePoolGame game = getGame();
		GameEvent event = event("state=" + state);
		event.addAttribute(new GameEventAttribute("player.inplay", game
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay", game
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion()
				.getId()));
		event.addAttribute(new GameEventAttribute("game.table", game
				.getCurrentPlayingSpace().toString()));
		event.addAttribute(new GameEventAttribute("ballinhand", String
				.valueOf(ballInHand)));
		return event;
	}

	protected GameEvent newGameEvent(String state) {
		return newGameEvent(state, false);
	}

	protected GameEvent newGameFoulEvent(String state) {
		return newGameEvent(state, true);
	}

}
