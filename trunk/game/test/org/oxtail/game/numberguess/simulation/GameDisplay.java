package org.oxtail.game.numberguess.simulation;

public interface GameDisplay {

	void notifyGameStart(String gameDecription, String gameId);
	
	void notifyGuess(String player, int guess, String gameId);
	
	void notifyGameOver(String gameId);
	
}
