package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.PlayerMove;
import org.oxtail.game.model.PlayingSpace;

public class GameEventContext<T extends PlayingSpace> {

	private PlayerMove<T> inplay;
	private Game<T> game;

	public PlayerMove<T> getInplay() {
		return inplay;
	}

	public void setInplay(PlayerMove<T> inplay) {
		this.inplay = inplay;
	}

	public Game<T> getGame() {
		return game;
	}

	public void setGame(Game<T> game) {
		this.game = game;
	}

}
