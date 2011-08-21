package org.oxtail.game.numberguess.model;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.StateId;
import org.oxtail.game.model.TwoPlayerGameHelper;

public class NumberGuessGame extends Game<NumberGuessBoard> {

	private int winningNumber;

	private NumberGuessBoard board;

	private TwoPlayerGameHelper helper;

	public NumberGuessGame(int winningNumber, NumberGuessBoard board) {
		this.winningNumber = winningNumber;
		this.board = board;
		helper = new TwoPlayerGameHelper(this);
	}

	/**
	 * Called when the current players turn is over
	 */
	public final void turnOver() {
		if (getInPlay() == getPlayerOne())
			setInPlay(getPlayerTwo());
		else if (getInPlay() == getPlayerTwo())
			setInPlay(getPlayerOne());
		else
			throw new IllegalStateException("Can`t swap players for game! "
					+ this);
	}

	private Player getPlayerTwo() {
		return helper.getPlayerTwo();
	}

	private Player getPlayerOne() {
		return helper.getPlayerOne();
	}

	@Override
	protected StateId getInitialState() {
		return null;
	}
	
	public boolean isGuessWin(int number) {
		board.guessNumber(getInPlay(), number);
		return (number == winningNumber);
	}

	public void setGameState(Class<?> k) {
		setStateId(new StateId(k));
	}
}
