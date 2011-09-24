package org.oxtail.game.state.reflect;

import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.StateActionExecutor;

/**
 * {@link StateActionExecutor} based on reflection
 * 
 * @author liam knox
 */
public class ReflectStateActionExecutor implements StateActionExecutor {

	@Override
	public void execute(AbstractGameState<?, ?, ?> state, String action) {
//		Method method = ReflectionUtils.findMethod(state.getClass(), action);
//		ReflectionUtils.invokeMethod(method, state);
	}
}
