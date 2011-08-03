package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.PlayingSpace;
import org.oxtail.game.model.StateId;

public abstract class AbstractGameState<G extends Game<S>, M extends Move, S extends PlayingSpace> {

	protected final GameEventContext<G, M, S> context;

	public AbstractGameState(GameEventContext<G, M, S> context) {
		this.context = context;
	}

	protected StateId getStateId() {
		return new StateId(getClass());
	}

	/** Called by the statemachine after the state execution is performed */
	protected abstract void afterStateExecution();
}
