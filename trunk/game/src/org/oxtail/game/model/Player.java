package org.oxtail.game.model;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventCallback;
import org.springframework.util.Assert;

/**
 * An entity that participates in a Game
 * 
 * @author liam knox
 */
public class Player implements GameEventCallback {

	private GameEventCallback callbackDelegate;

	private String alias;

	private String stateId;

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

	public String getState() {
		return stateId;
	}

	public void setState(String state) {
		this.stateId = state;
	}

	@Override
	public void onEvent(GameEvent event) {
		if (callbackDelegate != null)
			callbackDelegate.onEvent(event);
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

	public void setCallbackDelegate(GameEventCallback callbackDelegate) {
		this.callbackDelegate = callbackDelegate;
	}

	@Override
	public String toString() {
		return "Player [alias=" + alias + ", stateId=" + stateId + "]";
	}

	
}
