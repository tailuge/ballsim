package org.oxtail.game.model;

import org.oxtail.game.event.GameEventCallback;
import org.oxtail.game.event.GameEvent;
import org.springframework.util.Assert;

/**
 * An entity that participates in a Game
 * 
 * @author liam knox
 */
public class Player implements GameEventCallback {

	private String alias;

	private StateId stateId;

	public Player(String alias) {
		setAlias(alias);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		Assert.notNull(alias);
		this.alias = alias;
	}

	public StateId getStateId() {
		return stateId;
	}

	public void setStateId(StateId stateId) {
		this.stateId = stateId;
	}

	@Override
	public void onEvent(GameEvent event) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		return true;
	}

}
