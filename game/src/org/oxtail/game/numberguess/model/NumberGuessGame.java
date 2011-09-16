package org.oxtail.game.numberguess.model;

import java.util.Date;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.GameVersion;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.StateId;

public class NumberGuessGame extends Game<NumberGuessBoard> {

	private int winningNumber;

	public NumberGuessGame(int winningNumber, NumberGuessBoard board,
			Player player1, Player player2) {
		super(board,player1, player2);
		this.winningNumber = winningNumber;
		setInPlay(player1);
		newGame();
	}

	private void newGame() {
		setVersion(new GameVersion(String.valueOf(System.currentTimeMillis()),
				new Date()));

	}

	/**
	 * Called when the current players turn is over
	 */
	public final void turnOver() {
		if (inPlay() == player1())
			setInPlay(player2());
		else if (inPlay() == player2())
			setInPlay(player1());
		else
			throw new IllegalStateException("Can`t swap players for game! "
					+ this);
	}

	public Player player1() {
		return getPlayer(0);
	}

	public Player player2() {
		return getPlayer(1);
	}

	public Player notInPlay() {
		return player1() == inPlay() ? player2() : player1();
	}

	public boolean isGuessWin(int number) {
		getCurrentPlayingSpace().guessNumber(inPlay(), number);
		return (number == winningNumber);
	}

	public void setGameState(Class<?> k) {
		setStateId(new StateId(k));
	}

	public void notify(GameEvent event) {
		player1().onEvent(event);
		player2().onEvent(event);
	}

}
