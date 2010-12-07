package org.java.game.tictactoe;

import org.java.game.Game;
import org.java.game.GamePositionGenerator;
import org.java.game.Player;
import org.junit.Before;
import org.junit.Test;

public class TestTicTacToePositionGenerator {

	private Game game;
	private GamePositionGenerator positionGenerator;
	
	@Before
	public void setUp() {
		game = TicTacToe.newGame(new Player(), new Player());
		positionGenerator = new TicTacToePositionGenerator();
	}
	
	private Game showGames(Iterable<Game> games) {
		Game lastGame = null;
		for (Game g : games) {
			System.out.println(g.getBoard());
			System.out.println("\n");
			lastGame = g;
		}
		return lastGame;
	}
	
	@Test
	public void testPositionGeneration() {
		Game nextGame = showGames(positionGenerator.nextValidPositions(game));
		showGames(positionGenerator.nextValidPositions(nextGame));
	}
	
}
