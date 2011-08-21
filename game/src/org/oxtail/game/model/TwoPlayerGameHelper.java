package org.oxtail.game.model;

public class TwoPlayerGameHelper {

	private Game<?> game;

	public TwoPlayerGameHelper(Game<?> game) {
		this.game = game;
	}

	public Player getPlayerOne() {
		return game.getPlayer(0);
	}

	public Player getPlayerTwo() {
		return game.getPlayer(1);
	}

	
}
