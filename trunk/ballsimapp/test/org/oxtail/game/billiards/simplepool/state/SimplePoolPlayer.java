package org.oxtail.game.billiards.simplepool.state;

import java.util.List;

import junit.framework.Assert;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;

import com.google.common.collect.Lists;

public class SimplePoolPlayer extends Player {

	private final SimplePoolStatemachine statemachine;
	private List<GameEvent> events = Lists.newArrayList();

	private static int shotCount = 0;

	public SimplePoolPlayer(String alias, SimplePoolStatemachine statemachine) {
		super(alias);
		this.statemachine = statemachine;

	}

	private void notify(GameEvent event) {
		statemachine.execute(event);
	}

	private GameEvent newGameEvent(String action) {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias",
				getAlias()));
		gameEvent.addAttribute(new GameEventAttribute("action", action));
		return gameEvent;
	}

	public SimplePoolPlayer login() {
		notify(newGameEvent("login"));
		return this;
	}

	public SimplePoolPlayer watchGame(String id) {
		GameEvent event = newGameEvent("watchGame");
		event.addAttribute(new GameEventAttribute("game.watch.id", id));
		notify(event);
		return this;
	}

	public SimplePoolPlayer loginDuringPlay() {
		notify(newGameEvent("login", lastGameId()));
		return this;
	}

	public SimplePoolPlayer requestGame() {
		notify(newGameEvent("requestGame"));
		return this;
	}

	public SimplePoolPlayer requestWatchGames() {
		notify(newGameEvent("requestWatchGames"));
		return this;
	}

	private GameEvent newGameEvent(String action, String gameId) {
		GameEvent gameEvent = newGameEvent(action);
		gameEvent.addAttribute(new GameEventAttribute("game.id", gameId));
		gameEvent.addAttribute(new GameEventAttribute("game.table.state",
				String.valueOf(shotCount)));
		return gameEvent;
	}

	private GameEvent newShotEvent() {
		++shotCount;
		String gameId = lastGameId();
		Assert.assertNotNull("no game id found !", gameId);
		GameEvent gameEvent = newGameEvent("shot", gameId);
		return gameEvent;
	}

	public SimplePoolPlayer potAllBallsFromBreak() {
		return pot(1, 2, 3, 4, 5, 6, 7, 8, 9);
	}

	public SimplePoolPlayer pot(Integer ball, Integer... rest) {
		GameEvent gameEvent = newShotEvent();
		GameEventHelper helper = new GameEventHelper(gameEvent);
		helper.setValues("game.shot.ballspotted", ball, rest);
		notify(gameEvent);
		return this;
	}

	public SimplePoolPlayer miss() {
		GameEvent gameEvent = newShotEvent();
		GameEventHelper helper = new GameEventHelper(gameEvent);
		helper.setValue("game.shot.ballspotted", "");
		notify(gameEvent);
		return this;
	}

	@Override
	public void onEvent(GameEvent event) {
		events.add(event);
	}

	public SimplePoolPlayer assertLastStateWas(String stateExpected) {
		Assert.assertEquals(stateExpected, lastState());
		return this;
	}

	public String lastAttributeValue(String name) {
		GameEventAttribute attribute = lastEvent().getAttribute(name);
		if (attribute == null) {
			throw new IllegalArgumentException(name+" not found int event, "+lastEvent());
		}
		return attribute.getValue();
	}

	public String tableState() {
		return lastAttributeValue("game.table.state");
	}

	public String lastState() {
		return lastAttributeValue("state");
	}

	public String lastGameId() {
		return lastAttributeValue("game.id");
	}

	public GameEvent lastEvent() {
		Assert.assertTrue("No event received by " + getAlias(),
				!events.isEmpty());
		return events.get(events.size() - 1);
	}

	public SimplePoolPlayer assertViewing() {
		return assertLastStateWas("viewing");
	}

	public SimplePoolPlayer assertAiming() {
		return assertLastStateWas("aiming");
	}

	public SimplePoolPlayer assertLoggedIn() {
		return assertLastStateWas("loggedin");
	}

	public SimplePoolPlayer assertAwaitingGame() {
		return assertLastStateWas("awaitinggame");
	}

	public SimplePoolPlayer assertRequestWatchGames() {
		return assertLastStateWas("requestedwatchgames");
	}

	public SimplePoolPlayer assertWatchingGame() {
		return assertLastStateWas("watchinggame");
	}

	public SimplePoolPlayer assertWinner() {
		return assertLastStateWas("winner");
	}

	public SimplePoolPlayer assertLoser() {
		return assertLastStateWas("loser");
	}

	public SimplePoolPlayer assertChatting(String expectedMessage) {
		assertLastStateWas("chatting");
		Assert.assertEquals(expectedMessage, lastAttributeValue("chat.message"));
		return this;
	}

	public SimplePoolPlayer chatTo(String to, String message) {
		GameEvent event = newGameEvent("chat");
		event.addAttribute(new GameEventAttribute("chat.from", getAlias()));
		event.addAttribute(new GameEventAttribute("chat.to", to));
		event.addAttribute(new GameEventAttribute("chat.message", message));
		notify(event);
		return this;
	}

	public SimplePoolPlayer chatToAll(String message) {
		return chatTo("*", message);
	}

	public SimplePoolPlayer chat(Player to, String message) {
		return chatTo(to.getAlias(), message);
	}

	public void assertTableState(String state) {
		assertAttribute("game.table.state", state);
	}

	public void assertAvaliableActions(String value) {
		assertAttribute("available.actions", value);
	}

	public void assertAttribute(String name, String value) {
		Assert.assertEquals(value, lastAttributeValue(name));
	}

	public void assertGameType() {
		assertAttribute("game.type", "SimplePool");
	}

	public static void resetShotCount() {
		shotCount = 0;
	}
}
