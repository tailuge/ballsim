package org.java.game.tictactoe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.java.game.tictactoe.TicTacToePositions.*;

public class TestTicTacToePositions {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testGet() {
		assertEquals(TOP_LEFT,TicTacToePositions.getPosition(0));
		assertEquals(TOP_MIDDLE,TicTacToePositions.getPosition(1));
		assertEquals(TOP_RIGHT,TicTacToePositions.getPosition(2));

		assertEquals(MIDDLE_LEFT,TicTacToePositions.getPosition(3));
		assertEquals(MIDDLE_MIDDLE,TicTacToePositions.getPosition(4));
		assertEquals(MIDDLE_RIGHT,TicTacToePositions.getPosition(5));
	
		assertEquals(BOTTOM_LEFT,TicTacToePositions.getPosition(6));
		assertEquals(BOTTOM_MIDDLE,TicTacToePositions.getPosition(7));
		assertEquals(BOTTOM_RIGHT,TicTacToePositions.getPosition(8));
	
	}

}
