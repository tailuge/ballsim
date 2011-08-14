package org.oxtail.game.state;

/**
 * Executes the action of the State
 * 
 * @author liam knox
 */
public interface StateActionExecutor {

	void execute(AbstractGameState<?,?,?> state, String action);
}
