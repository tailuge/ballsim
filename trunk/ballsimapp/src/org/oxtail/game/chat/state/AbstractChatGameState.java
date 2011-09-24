package org.oxtail.game.chat.state;

import javax.xml.ws.Action;

import org.oxtail.game.model.Player;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@SuppressWarnings("rawtypes")
public abstract class AbstractChatGameState extends AbstractGameState {

	@SuppressWarnings("unchecked")
	public AbstractChatGameState(
			GameEventContext context) {
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
