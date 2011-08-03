package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.PlayingSpace;

/**
 * Binds the core components of the Game and current Move for access via the
 * Statemachine
 * @author liam knox
 */
public class GameEventContext<G extends Game<S>, M extends Move, S extends PlayingSpace> {

	private G game;
	private M move;

	public G getGame() {
		return game;
	}

	public void setGame(G game) {
		this.game = game;
	}

	public M getMove() {
		return move;
	}

	public void setMove(M move) {
		this.move = move;
	}

}
