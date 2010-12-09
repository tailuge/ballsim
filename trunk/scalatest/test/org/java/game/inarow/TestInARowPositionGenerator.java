package org.java.game.inarow;

import org.java.game.Game;
import org.java.game.GamePositionGenerator;
import org.junit.Before;
import org.junit.Test;

public class TestInARowPositionGenerator {

	private GamePositionGenerator generator;
	
	@Before
	public void setUp() {
		generator = new InARowPositionGenerator();
	}
	
	@Test
	public void test() {
		Game game = InARow.newGame(4, 3);
		for (Game g : generator.nextValidPositions(game)) {
			System.out.println(g.getBoard());
			game = g;
		}
		for (Game g : generator.nextValidPositions(game)) {
			System.out.println(g.getBoard());
			game = g;
		}
	}
	
}
