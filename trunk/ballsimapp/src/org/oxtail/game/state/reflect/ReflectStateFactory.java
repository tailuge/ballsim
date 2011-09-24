package org.oxtail.game.state.reflect;

import java.lang.reflect.InvocationTargetException;

import org.oxtail.exception.OxtailRuntimeException;
import org.oxtail.game.model.StateId;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;
import org.oxtail.game.state.StateFactory;

/**
 * {@link StateFactory} based in reflection creation
 * 
 * @author liam knox
 */
public class ReflectStateFactory implements StateFactory {

	@Override
	public AbstractGameState<?, ?, ?> createState(StateId stateId,
			GameEventContext<?, ?, ?> context) {
		Class<?> c;
		try {
			c = Class.forName(stateId.getId());
			return (AbstractGameState<?, ?, ?>) c.getConstructor(
					GameEventContext.class).newInstance(context);
		} catch (ClassNotFoundException e) {
			throw new OxtailRuntimeException(
					"Can't create state for,  no class found " + stateId, e);
		} catch (InvocationTargetException e) {
			throw new OxtailRuntimeException(
					"Can't create state for, constructor mismatch " + stateId,
					e.getTargetException());
		} catch (Exception e) {
			throw new OxtailRuntimeException("Can't create state for "
					+ stateId, e);
		}
	}

}
