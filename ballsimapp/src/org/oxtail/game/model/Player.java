package org.oxtail.game.model;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.motion.ballsimapp.shared.GameEventCallback;

/**
 * An entity that participates in a Game
 */
public class Player implements GameEventCallback {

	private GameEventCallback callbackDelegate;

	private String alias;

	private StateId stateId;

	/**
	 * Used to store player data
	 */
	private final GameEvent playerAttributes = new GameEvent();

	public Player(String alias) {
		setAlias(alias);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		// Assert.notNull(alias);
		this.alias = alias;
	}

	public String getState() {
		return stateId.getId();
	}

	public void setState(String state) {
		this.stateId = new StateId(state);
	}

	@Override
	public void onEvent(GameEvent event) {
		event.addAttribute(new GameEventAttribute("target", getAlias()));
		if (callbackDelegate != null) {
			callbackDelegate.onEvent(event);
		}
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

	public GameEvent getPlayerAttributes() {
		return playerAttributes;
	}

}