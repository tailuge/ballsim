package org.java.game.tictactoe;

import junit.framework.Assert;

import org.java.game.BestGameListener;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.minmax.MinMaxEvaluator;
import org.junit.Before;
import org.junit.Test;

/**
 * Test minmax evaluation using TicTacToe
 */
public class TestMinMaxEvaluator  {

	private MinMaxEvaluator minMaxEvaluator;
	
	@Before
	public void setUp() {
		minMaxEvaluator = new MinMaxEvaluator(new TicTacToePositionEvaluator(), new TicTacToePositionGenerator(), new BestGameListener() {
			
			@Override
			public void notifyBestGame(GameEvaluationResult result) {
				System.out.println(result);
			}
		});
	}

	String drawInOneBoard = 
		"0X0"+
		"0X0"+
		"X.X";

	String winInOneBoard = 
		"X00"+
		"0X0"+
		"XX.";

	String forceWinInThreeBoard = 
		".0."+
		".X0"+
		"X..";

	String forceWinInFiveBoard = 
		"..0"+
		"..X"+
		"...";

	
	private void assertResult(String board, double scoreExpected) {
		Game game = TicTacToe.newGame(board);
		Assert.assertEquals(scoreExpected, minMaxEvaluator.minimax(game,8));
	}
	
	private void assertPlayerOneWins(String board) {
		assertResult(board, 1.0);
	}
	
	@Test
	public void testPlayerOneWins() {
//		assertPlayerOneWins(forceWinInThreeBoard);
//		assertPlayerOneWins(winInOneBoard);
		assertPlayerOneWins(forceWinInFiveBoard);
	}
}


