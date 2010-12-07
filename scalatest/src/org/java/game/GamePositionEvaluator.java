package org.java.game;

/**
 * Calculates the score of this game
 *
 */
public interface GamePositionEvaluator {
	
	GameScore evaluate(Game game);

}
