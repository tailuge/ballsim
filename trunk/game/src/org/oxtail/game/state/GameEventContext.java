package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.PlayerMove;
import org.oxtail.game.model.PlayingSpace;

public class GameEventContext<T extends PlayingSpace, S extends Game<T>> {

	private PlayerMove<T> inplay;
	private S game;

	public PlayerMove<T> getInplay() {
		return inplay;
	}

	public void setInplay(PlayerMove<T> inplay) {
		this.inplay = inplay;
	}

	public S getGame() {
		return game;
	}

	public void setGame(S game) {
		this.game = game;
	}

}
