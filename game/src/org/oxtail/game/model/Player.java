package org.oxtail.game.model;

import org.oxtail.game.event.EventCallback;
import org.oxtail.game.event.GameEvent;

/**
 * An entity that participates in a Game
 * 
 * @author liam knox
 */
public class Player implements EventCallback {

	private String alias;

	private StateId stateId;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public StateId getStateId() {
		return stateId;
	}

	public void setStateId(StateId stateId) {
		this.stateId = stateId;
	}

	@Override
	public void notify(GameEvent event) {
	}

}
