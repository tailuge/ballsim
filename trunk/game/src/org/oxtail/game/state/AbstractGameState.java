package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.PlayingSpace;
import org.oxtail.game.model.StateId;

public abstract class AbstractGameState<T extends PlayingSpace, S extends Game<T>> {

	protected final GameEventContext<T,S> context;

	public AbstractGameState(GameEventContext<T,S> context) {
		this.context = context;
	}

	protected GameEventContext<T,S> getContext() {
		return context;
	}
	
	protected StateId getStateId() {
		return new StateId(getClass());
	}
}
