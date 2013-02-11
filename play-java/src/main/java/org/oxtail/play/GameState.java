package org.oxtail.play;

public enum GameState {

	IN_PLAY, DRAW, PLAYER1_WIN, PLAYER2_WIN;

	public boolean isGameOver() {
		return this != IN_PLAY;
	}
}
