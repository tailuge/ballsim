package org.java.game;

import static junit.framework.Assert.*;
import static org.java.game.PositionBean.*;
import org.junit.Test;

/**
 * Tests actions on the {@link Board}
 *
 */
public class TestBoard {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidBoard() {
		Board.newSquareBoard(0);
	}

	@Test
	public void testBoardPieceAreAll() {
		Board board = Board.newSquareBoard(2);
		board.move(new TestPiece('X'), newPosition(0, 0));
		board.move(new TestPiece('0'), newPosition(1, 0));
		board.move(new TestPiece('X'), newPosition(0, 1));
		board.move(new TestPiece('0'), newPosition(1, 1));
		assertTrue(board.hasPieceAt(newPosition(0, 0)));
	}

	@Test
	public void testBoardToString() {
		Board board = Board.newSquareBoard(2);
		board.move(new TestPiece('a'), newPosition(0, 0));
		board.move(new TestPiece('b'), newPosition(1, 0));
		board.move(new TestPiece('c'), newPosition(0, 1));
		board.move(new TestPiece('d'), newPosition(1, 1));
		System.out.println(board);
	}

	
	@Test
	public void testHasPieceAt() {
		Board board = Board.newSquareBoard(2);
		board.move(new TestPiece('a'), newPosition(0, 0));
		assertTrue(board.hasPieceAt(newPosition(0, 0)));
		assertFalse(board.hasPieceAt(newPosition(1, 0)));
	}

	@Test
	public void testGetPieceAt() {
		Board board = Board.newSquareBoard(2);
		TestPiece piece = new TestPiece('a');
		board.move(piece,newPosition(0, 0));
		assertEquals(piece,board.getPieceAt(newPosition(0, 0)));
	}

	private static class TestPiece extends Piece {
		public TestPiece(char c) {
			super(String.valueOf(c));
		}
	}

}
