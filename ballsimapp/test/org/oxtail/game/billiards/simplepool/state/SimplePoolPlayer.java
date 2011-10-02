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

	public SimplePoolPlayer loginDuringPlay() {
		notify(newGameEvent("login", lastGameId()));
		return this;
	}

	public SimplePoolPlayer requestGame() {
		notify(newGameEvent("requestGame"));
		return this;
	}

	private GameEvent newGameEvent(String action, String gameId) {
		GameEvent gameEvent = newGameEvent(action);
		gameEvent.addAttribute(new GameEventAttribute("game.id", gameId));
		return gameEvent;
	}

	private GameEvent newShotEvent() {
		String gameId = lastGameId();
		Assert.assertNotNull("no game id found !", gameId);
		return newGameEvent("shot", gameId);
	}

	public SimplePoolPlayer pot(Integer ball, Integer... rest) {
		GameEvent gameEvent = newShotEvent();
		GameEventHelper helper = new GameEventHelper(gameEvent);
		helper.setValue("game.shot.ballspotted", ball, rest);
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
		return lastEvent().getAttribute(name).getValue();
	}

	public String lastState() {
		return lastAttributeValue("state");
	}

	public String lastGameId() {
		return lastAttributeValue("game.id");
	}

	public GameEvent lastEvent() {
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

	public SimplePoolPlayer assertWinner() {
		return assertLastStateWas("winner");
	}

	public SimplePoolPlayer assertLoser() {
		return assertLastStateWas("loser");
	}

}
