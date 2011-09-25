package org.motion.ballsimapp.shared;

public class Events {

	// connectivity

	public final static String LOGIN = "login";
	public final static String ALIAS = "player.alias";
	public final static String STATE = "state";
	public final static String TARGET = "target";
	public final static String CLIENT_ERROR = "error";
	public final static String CHANNEL_CONNECTED = "connected";
	public final static String LOGIN_SUCCESS = "loggedin";
	public final static String AWAITING_GAME = "awaitinggame";
	
	// billiards specific (move to another class?)

	public final static String AIM_UPDATE = "aimUpdate";
	public final static String AIM_COMPLETE = "aimComplete";
	public final static String PLACEBALL_UPDATE = "placeUpdate";
	public final static String PLACEBALL_COMPLETE = "placeComplete";
	public final static String ANIMATION_COMPLETE = "animationComplete";

	// static factories for event creation

	public static GameEvent event(String name, String value) {
		GameEvent event = new GameEvent();
		event.addAttribute(attr(name, value));
		return event;
	}

	private static GameEventAttribute attr(String name, String value) {
		return new GameEventAttribute(name, value);
	}

	public static GameEvent login(String playerId, String password) {
		GameEvent loginRequest = event("action", "login");
		loginRequest.addAttribute(attr("player.alias", playerId));
		loginRequest.addAttribute(attr("password", password));
		return loginRequest;
	}

	public static GameEvent requestGame(String playerId) {
		GameEvent loginRequest = event("action", "requestGame");
		loginRequest.addAttribute(attr("player.alias", playerId));
		return loginRequest;
	}

	public static GameEvent channelConnected() {
		return event(CHANNEL_CONNECTED, "");
	}

	// predicates
	
	public static boolean isState(GameEvent event,String value) {
		return event.hasAttribute(STATE)
				&& event.getAttribute(STATE).getValue().equals(value);
	}
	
}
