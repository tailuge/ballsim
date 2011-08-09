package org.oxtail.game.model;

/**
 * An entity that participates in a Game
 * 
 * @author liam knox
 */
public class Player {

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

}
