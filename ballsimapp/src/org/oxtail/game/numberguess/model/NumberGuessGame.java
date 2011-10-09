package org.oxtail.game.numberguess.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.GameVersion;
import org.oxtail.game.model.Player;

public class NumberGuessGame extends Game<NumberGuessBoard> {

	private static final AtomicInteger INSTANCE_COUNT = new AtomicInteger();
	private int winningNumber;

	public NumberGuessGame(int winningNumber, NumberGuessBoard board,
			Player player1, Player player2) {
		super(board, player1, player2);
		this.winningNumber = winningNumber;
		setInPlay(player1);
		newGame();
	}

	private void newGame() {
		setVersion(new GameVersion(String.valueOf(INSTANCE_COUNT
				.incrementAndGet()), new Date()));

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

	public String toSummaryString() {
		return "["+getId()+":"+winningNumber+"] "+player1().getAlias()+" vs. "+player2().getAlias();
	}

	@Override
	public String getGameType() {
		return "NumberGuess";
	}

	
	
}