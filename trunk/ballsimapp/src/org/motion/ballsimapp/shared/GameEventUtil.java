package org.motion.ballsimapp.shared;

import java.util.ArrayList;

public class GameEventUtil {

	public final static String LOGIN = "login";
	public final static String ALIAS = "player.alias";
	public final static String CHANNEL_CONNECTED = "player.alias";
	public final static String LOGIN_SUCCESS = "loginSuccess";

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
	
	// move to a factory

	public static GameEvent getLoginEvent(String playerId, String password)
	{
		GameEvent loginRequest = makeEvent("action","login");
		loginRequest.addAttribute(new GameEventAttribute("player.alias", playerId));
		loginRequest.addAttribute(new GameEventAttribute("password", password));
		return loginRequest;
	}
	

	public static GameEvent requestGame(String playerId)
	{
		GameEvent loginRequest = makeEvent("action","gameRequest");
		loginRequest.addAttribute(new GameEventAttribute("player.alias", playerId));
		return loginRequest;
	}

}
