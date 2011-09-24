package org.oxtail.game.state.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
		try {
			Method method = state.getClass().getMethod(action);
			method.invoke(state);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("failed to invoke " + action,
					e.getTargetException());

		} catch (Exception e) {
			throw new RuntimeException("failed to invoke " + action, e);
		}
	}
}
