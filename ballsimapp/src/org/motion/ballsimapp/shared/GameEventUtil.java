package org.motion.ballsimapp.shared;

import java.util.ArrayList;

public class GameEventUtil {

	public final static String AIM_UPDATE = "aimUpdate";
	public final static String AIM_COMPLETE = "aimComplete";

	public final static String PLACEBALL_UPDATE = "placeUpdate";
	public final static String PLACEBALL_COMPLETE = "placeComplete";

	public final static String ANIMATION_COMPLETE = "animationComplete";

	public static GameEvent makeEvent(String name, String value) {
		GameEventAttribute attribute = new GameEventAttribute(name, value);

		ArrayList<GameEventAttribute> attributes = new ArrayList<GameEventAttribute>();
		attributes.add(attribute);

		GameEvent event = new GameEvent();
		event.setAttributes(attributes);

		return event;
	}

	public static GameEvent makeEvent(String name) {
		return makeEvent(name, "");
	}
}
