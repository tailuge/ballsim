package org.oxtail.game.numberguess.model;

import junit.framework.Assert;

import org.junit.Test;
import org.oxtail.game.model.Player;

/**
 * Tests {@link NumberGuessBoard}
 * 
 * @author liam knox
 */
public class TestNumberGuessBoard {

	private Player bob = new Player("bob");

	@Test
	public void testBoardCreation() {
		NumberGuessBoard board = new NumberGuessBoard(0, 10);
		Assert.assertNotNull(board);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoardCreationErrorWrongOrder() {
		new NumberGuessBoard(11, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoardCreationErrorStartNumberNotePositive() {
		new NumberGuessBoard(-1, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoardCreationErrorEndNumberNotePositive() {
		new NumberGuessBoard(1, -1);
	}

	@Test
	public void testGuess() {
		NumberGuessBoard board = new NumberGuessBoard(0, 9);
		board.guessNumber(bob, 1);
		NumberGuessMove guess = board.getGuessFor(1);
		Assert.assertEquals(new NumberGuessMove(bob, 1), guess);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGuessNumberOutOfRangeHigh() {
		NumberGuessBoard board = new NumberGuessBoard(0, 9);
		board.guessNumber(bob, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGuessNumberOutOfRangeLow() {
		NumberGuessBoard board = new NumberGuessBoard(1, 9);
		board.guessNumber(bob, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateGuessNumber() {
		NumberGuessBoard board = new NumberGuessBoard(1, 9);
		board.guessNumber(bob, 1);
		board.guessNumber(bob, 1);
	}

}
