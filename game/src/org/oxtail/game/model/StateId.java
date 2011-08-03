package org.oxtail.game.model;

/**
 * A soft state identifier which can get mapped to a behavioral state class
 * @author liam knox
 *
 */
public class StateId {

	private final Class<?> stateClass;

	public StateId(Class<?> stateClass) {
		this.stateClass = stateClass;
	}

	public Class<?> getStateClass() {
		return stateClass;
	}

}
