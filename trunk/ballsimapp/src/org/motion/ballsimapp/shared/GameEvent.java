package org.motion.ballsimapp.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Communication packet between client and server
 */
public class GameEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<GameEventAttribute> attributes = new ArrayList<GameEventAttribute>();

	public List<GameEventAttribute> getAttributes() {
		return attributes;
	}

	public void addAttribute(GameEventAttribute attribute) {
		this.attributes.add(attribute);
	}

	public boolean hasAttribute(String attributeName) {
		return getAttribute(attributeName) != null;
	}

	public GameEventAttribute getAttribute(String attributeName) {
		for (GameEventAttribute a : attributes)
			if (a.getName().equals(attributeName))
				return a;
		return null;
	}

	public boolean removeAttribute(String attributeName) {
		int index = 0;
		for (GameEventAttribute a : attributes) {
			if (a.getName().equals(attributeName))
				attributes.remove(index);
			index++;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "GameEvent [attributes=" + attributes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
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
		GameEvent other = (GameEvent) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	public GameEvent copy() {
		GameEvent event = new GameEvent();
		for (GameEventAttribute attribute : getAttributes())
			event.addAttribute(new GameEventAttribute(attribute.getName(),
					attribute.getValue()));
		return event;
	}

}
