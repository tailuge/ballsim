package org.oxtail.game.numberguess.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

public class PlayerToGuess extends
		AbstractGameState<NumberGuessGame, NumberGuessMove, NumberGuessBoard> {

	public PlayerToGuess(
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context) {
		super(context);
	}

	@Action
	public void guess() {
		NumberGuessGame game = getGame();
		if (game.isGuessWin(getMove().getNumberGuessed())) {
			inPlay().notifyOf(win());
			notInPlay().notifyOf(loss());
			gameOver();
		} else {
			inPlay().notifyOf(move());
			notInPlay().notifyOf(move());
			turnOver();
		}
	}

	private GameEvent move() {
		return null;
	}

	private void gameOver() {
		getGame().setGameState(GameOver.class);
	}

	private void turnOver() {
		getGame().turnOver();
		getGame().setGameState(PlayerToGuess.class);
	}

	private GameEvent loss() {
		return null;
	}

	private GameEvent win() {
		return null;
	}
}
