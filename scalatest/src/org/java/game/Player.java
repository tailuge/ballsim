package org.java.game;

public class Player {

	private boolean playerOne;
	
	public boolean isPlayerOne() {
		return playerOne;
	}
	
	/**
	 * No one should set this apart from the Game initialization
	 */
	void setPlayerOne() {
		playerOne = true;
	}

	public String toString() {
		return playerOne ? "player1" : "player2";
	}
}
