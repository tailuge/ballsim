package org.communications.layer.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Communication packet between client and server
 */
public class GameEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<GameEventAttribute> attributes;

	public List<GameEventAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<GameEventAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public static GameEvent simpleEvent(String name,String value)
	{
		GameEventAttribute attribute = new GameEventAttribute();
		attribute.setName(name);
		attribute.setValue(value);
		
		ArrayList<GameEventAttribute> attributes = new ArrayList<GameEventAttribute>();
		attributes.add(attribute);

		GameEvent event = new GameEvent();
		event.setAttributes(attributes);

		return event;
	}

}
