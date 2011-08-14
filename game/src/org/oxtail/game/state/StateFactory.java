package org.oxtail.game.state;

import org.oxtail.game.model.StateId;

/**
 * Creates State based on id
 * 
 * @author liam knox
 */
public interface StateFactory {

	AbstractGameState<?, ?, ?> createState(StateId stateId, GameEventContext<?, ?, ?> context);

}
