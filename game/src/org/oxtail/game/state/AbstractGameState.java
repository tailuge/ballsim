package org.oxtail.game.state;

public abstract class AbstractGameState {

	protected final GameEventContext context;

	public AbstractGameState(GameEventContext context) {
		super();
		this.context = context;
	}

}
