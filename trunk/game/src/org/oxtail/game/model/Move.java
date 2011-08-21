package org.oxtail.game.model;

import org.springframework.util.Assert;

/**
 * Some move made in the Game
 * 
 * @author liam knox
 */
public abstract class Move {

	protected final Player player;

	public Move(Player player) {
		Assert.notNull(player);

		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
