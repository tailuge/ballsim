package org.motion.ballsimapp.shared;

public class Events {

	// connectivity

	public final static String ACTION = "action";
	public final static String STATE = "state";
	public final static String TARGET = "target";
	public final static String LOGIN = "login";
	public final static String REQUEST_GAME = "requestGame";
	public final static String PLAYER_ALIAS = "player.alias";
	public final static String GAME_ID = "game.id";
	public final static String PASSWORD = "password";
	public final static String CLIENT_ERROR = "error";
	public final static String CHANNEL_CONNECTED = "connected";
	public final static String LOGIN_SUCCESS = "loggedin";
	public final static String AWAITING_GAME = "awaitinggame";
	public final static String WINNER = "winner";
	public final static String LOSER = "loser";

	// billiards specific (move to another class?)

	public final static String BEGIN_AIMING = "aiming";
	public final static String BEGIN_VIEWING = "viewing";
	public final static String AIM_UPDATE = "aimUpdate";
	public final static String AIM_COMPLETE = "aimComplete";
	public final static String PLACEBALL_UPDATE = "placeUpdate";
	public final static String PLACEBALL_COMPLETE = "placeComplete";
	public final static String ANIMATION_COMPLETE = "animationComplete";
	public final static String CURSOR_INPUT = "cursorInput";
	public final static String CURSOR_INPUT_COMPLETE = "cursorInputComplete";
	public final static String BALL_IN_HAND = "ballinhand";

	public static final String GAME_SHOT_ANYBALLHIT = "game.shot.anyballhit";
	public static final String GAME_SHOT_CUSHION_BEFORE_SECOND_BALL = "game.shot.cushionBeforeSecondBall";
	public static final String GAME_SHOT_TOTAL_BALLS_HITTING_CUSHION = "game.shot.totalBallsHittingCushion";
	public static final String GAME_SHOT_FIRST_BALL_HIT = "game.shot.firstBallHit";
	public static final String GAME_SHOT_BALLSPOTTED = "game.shot.ballspotted";

	public static final String GAME_RACK_TYPE = "game.rack.type";
	public static final String GAME_RACK_SEED = "game.rack.seed";
	
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
		GameEvent loginRequest = event(ACTION, LOGIN);
		loginRequest.addAttribute(attr(PASSWORD, password));
		return loginRequest;
	}

	public static GameEvent requestGame(String playerId) {
		GameEvent loginRequest = event(ACTION, REQUEST_GAME);
		loginRequest.addAttribute(attr(PLAYER_ALIAS, playerId));
		return loginRequest;
	}

	public static GameEvent channelConnected() {
		return event(CHANNEL_CONNECTED, "");
	}

	// predicates

	public static boolean isState(GameEvent event, String value) {
		return event.hasAttribute(STATE)
				&& event.getAttribute(STATE).getValue().equals(value);
	}

}
