package org.java.game.inarow;

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
	
	private Game bestGame;
	
	@Before
	public void setUp() {
		minMaxEvaluator = new MinMaxEvaluator(new InARowPositionEvaluator(3), new InARowPositionGenerator(), new BestGameListener() {
			
			
			@Override
			public void notifyBestGame(GameEvaluationResult result) {
				bestGame = result.getGame();
			}
		});
	}

	
	String stupidDecision = "...."+
	                        "X..."+
	                        "0..."+
	                        "0.X.";  
	
	
	@Test
	public void testPlayerOneWins() {
		Game game = InARow.newGame(stupidDecision,4, 4);
		System.out.println(minMaxEvaluator.minimax(game,20));
		System.out.println(bestGame);
	}
}


