package org.oxtail.game.state;

import org.oxtail.game.model.PlayingSpace;

public abstract class AbstractGameState<T extends PlayingSpace> {

	protected final GameEventContext<T> context;

	public AbstractGameState(GameEventContext<T> context) {
		super();
		this.context = context;
	}

	protected GameEventContext<T> getContext() {
		return context;
	}
	
}
