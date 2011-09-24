package org.oxtail.game.billiards.simplepool.state;

import javax.xml.ws.Action;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public abstract class AbstractSimplePoolGameState extends AbstractGameState<SimplePoolGame, SimplePoolMove, SimplePoolTable> {

	public AbstractSimplePoolGameState(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}
	
	protected Player findOpponent() {
		Iterable<Player> others = getGameHome().findPlayers(new Predicate<Player>() {
			@Override
			public boolean apply(Player other) {
				return !other.equals(getInPlay()) && (PlayerState.valueOf(other.getState()) == PlayerState.LoggedIn);
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
}
