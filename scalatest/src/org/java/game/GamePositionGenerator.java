package org.java.game;

/**
 * From the current game return the valid continuations
 */
public interface GamePositionGenerator {

	Iterable<Game> nextValidPositions(Game game);
	
}
