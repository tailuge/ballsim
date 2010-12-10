package org.java.game.demo;

import org.java.game.Game;
import org.java.game.GamePositionEvaluator;
import org.java.game.GameScore;
import org.java.game.GameStatus;

public class DemoTreePositionEvaluator implements GamePositionEvaluator {

	private int callCount;
	
	@Override
	public GameScore evaluate(Game game) {
		
		int score = 0;
		switch (callCount) {
		case 2 : score= 10; break;
		case 3 : score=-10; break;
		case 5 : score= 5; break;
		case 6 : score=-7; break;
		}
		++callCount;
		return new GameScore(score, GameStatus.Inplay);
	}

}
