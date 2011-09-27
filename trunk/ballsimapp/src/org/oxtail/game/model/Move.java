package org.oxtail.game.model;

/**
 * Some move made in the Game
 * 
 * @author liam knox
 */
public abstract class Move {

	protected Player player;

	public Move(Player player) {
		this.player = player;
	}

	public Move() {
	}

	public Player getPlayer() {
		return player;
	}

}
