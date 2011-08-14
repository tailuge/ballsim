package org.oxtail.game.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.model.StateId;

public abstract class AbstractStatemachine {

	public abstract void execute(GameEvent gameEvent);

	private StateFactory stateFactory;

	private StateActionExecutor stateActionExecutor;

	protected void executeForState(StateId stateId, String action,
			GameEventContext context) {
		AbstractGameState state = stateFactory.createState(stateId, context);
		stateActionExecutor.execute(state, action);
	}
}
