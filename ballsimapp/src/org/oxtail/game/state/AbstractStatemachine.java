package org.oxtail.game.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.oxtail.game.model.StateId;

public abstract class AbstractStatemachine implements GameStatemachine {

	public abstract void execute(GameEvent gameEvent);

	private StateFactory stateFactory;

	private StateActionExecutor stateActionExecutor;

	protected void executeForState(StateId stateId, String action,
			GameEventContext<?,?,?> context) {
		context.setStatemachine(this);
		AbstractGameState<?,?,?> state = stateFactory.createState(stateId, context);
		stateActionExecutor.execute(state, action);
	}
}
