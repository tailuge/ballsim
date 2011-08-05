package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.PlayingSpace;

public abstract class PlayerState<G extends Game<S>, M extends Move, S extends PlayingSpace> extends AbstractGameState<G,M,S> {

	public PlayerState(GameEventContext<G,M,S> context) {
		super(context);
	}
}
