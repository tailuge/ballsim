package org.oxtail.game.state;

public abstract class AbstractGameState<T> {

	protected final GameEventContext context;

	public AbstractGameState(GameEventContext context) {
		super();
		this.context = context;
	}

	protected GameEventContext getContext() {
		return context;
	}
	
}
