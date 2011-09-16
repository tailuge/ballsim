package org.oxtail.game.numberguess.state;

import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.state.GameEventContext;

public class GameOver extends AbstractNumberGuessGameState {

	public GameOver(
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context) {
		super(context);
	}
	
	
}
