package org.oxtail.game.event;

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

	public void setAttributes(List<GameEventAttribute> attributes) {
		this.attributes = attributes;
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

	@Override
	public String toString() {
		return "GameEvent [attributes=" + attributes + "]";
	}

	
}
