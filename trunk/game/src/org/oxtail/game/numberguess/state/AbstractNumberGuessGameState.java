package org.oxtail.game.numberguess.state;

import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public abstract class AbstractNumberGuessGameState extends AbstractGameState<NumberGuessGame, NumberGuessMove, NumberGuessBoard> {

	public AbstractNumberGuessGameState(
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context) {
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
}
