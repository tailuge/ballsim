package org.java.game.tictactoe;

import junit.framework.Assert;

import org.java.game.Board;
import org.java.game.Game;
import org.java.game.GamePositionEvaluator;
import org.java.game.GameScore;
import org.java.game.GameStatus;
import org.java.game.Piece;
import org.java.game.Player;
import org.junit.Before;
import org.junit.Test;

public class TestTicTacToePositionEvaluator {

	private Game game = TicTacToe.newGame(new Player(), new Player());

	private Board board = game.getBoard();

	private Piece piece = TicTacToe.NOUGHT;

	private GamePositionEvaluator evaluator = new TicTacToePositionEvaluator();

	@Before
	public void setUp() {

	}

	@Test
	public void testEvaluateWinBottomRow() {
		board.move(piece, TicTacToePositions.BOTTOM_LEFT);
		board.move(piece, TicTacToePositions.BOTTOM_MIDDLE);
		board.move(piece, TicTacToePositions.BOTTOM_RIGHT);
		GameScore gameScore = evaluator.evaluate(game);
		System.out.println(board);
		Assert.assertEquals(GameStatus.Win, gameScore.getStatus());
	}

	@Test
	public void testEvaluateInCompleteBottomRow() {
		board.move(piece, TicTacToePositions.BOTTOM_LEFT);
		board.move(piece, TicTacToePositions.BOTTOM_MIDDLE);
		GameScore gameScore = evaluator.evaluate(game);
		Assert.assertEquals(GameStatus.Inplay, gameScore.getStatus());
	}

}
