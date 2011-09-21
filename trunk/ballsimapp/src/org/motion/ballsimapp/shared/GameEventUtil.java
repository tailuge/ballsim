package org.motion.ballsimapp.shared;

import java.util.ArrayList;

//import org.oxtail.game.event.GameEvent;
//import org.oxtail.game.event.GameEventAttribute;

public class GameEventUtil {

	public static GameEvent simpleEvent(String name,String value)
	{
		GameEventAttribute attribute = new GameEventAttribute(name,value);
		
		ArrayList<GameEventAttribute> attributes = new ArrayList<GameEventAttribute>();
		attributes.add(attribute);

		GameEvent event = new GameEvent();
		event.setAttributes(attributes);

		return event;
	}
	
}
