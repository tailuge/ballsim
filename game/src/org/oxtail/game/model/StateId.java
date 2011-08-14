package org.oxtail.game.model;

/**
 * A soft state identifier which can get mapped to a behavioral state class
 * 
 * @author liam knox
 * 
 */
public class StateId {

	private final String id;

	public StateId(Class<?> stateClass) {
		this(stateClass.getName());
	}

	public StateId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "StateId [id=" + id + "]";
	}

}
