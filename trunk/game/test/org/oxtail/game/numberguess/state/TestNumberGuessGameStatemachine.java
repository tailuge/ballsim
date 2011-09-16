package org.oxtail.game.numberguess.state;

import static junit.framework.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.event.GameEventCallback;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.StateActionExecutor;
import org.oxtail.game.state.StateFactory;
import org.oxtail.game.state.reflect.ReflectStateActionExecutor;
import org.oxtail.game.state.reflect.ReflectStateFactory;

import com.google.common.collect.Lists;

/**
 * Tests {@link NumberGuessGameStatemachine}
 * 
 */
public class TestNumberGuessGameStatemachine {

	private NumberGuessGameStatemachine statemachine;
	private GameHome gameHome = new InMemoryGameHome();

	private StateFactory stateFactory = new ReflectStateFactory();
	private StateActionExecutor stateActionExecutor = new ReflectStateActionExecutor();

	private Player bob = new Player("bob");
	private Player jim = new Player("jim");

	private AssertingGameEventCallback bobCallback = new AssertingGameEventCallback();
	private AssertingGameEventCallback jimCallback = new AssertingGameEventCallback();

	@Before
	public void before() {
		statemachine = new NumberGuessGameStatemachine(gameHome, stateFactory,
				stateActionExecutor);
		bob.setCallbackDelegate(bobCallback);
		jim.setCallbackDelegate(jimCallback);
		PlayerState.LoggedOut.set(bob,jim);
		gameHome.storePlayer(bob);
		gameHome.storePlayer(jim);
	}

	@Test
	public void testPlayerOneJoins() {
		statemachine.execute(newGameEvent(bob, "start"));
		assertEquals("awaitinggame", bobCallback.lastState());
	}

	@Test
	public void testPlayerOneJoinsAndLogsOut() {
		statemachine.execute(newGameEvent(bob, "start"));
		assertEquals("awaitinggame", bobCallback.lastState());
		statemachine.execute(newGameEvent(bob, "logout"));
		assertEquals("loggedout", bobCallback.lastState());
	}

	@Test
	public void testGameStartWith2Players() {
		statemachine.execute(newGameEvent(bob, "start"));
		statemachine.execute(newGameEvent(jim, "start"));
		assertEquals("gamestarted", bobCallback.lastState());
		assertEquals("gamestarted", jimCallback.lastState());
		assertEquals(1, jimCallback.numberOfEvents());
		assertEquals(2, bobCallback.numberOfEvents());
		assertInPlay("jim", "bob");
	}

	private void assertInPlay(String expectedInPlay, String expectedNotInPlay) {
		assertInPlay(bobCallback, expectedInPlay, expectedNotInPlay);
		assertInPlay(jimCallback, expectedInPlay, expectedNotInPlay);

	}

	private void assertInPlay(AssertingGameEventCallback callback,
			String expectedInPlay, String expectedNotInPlay) {
		assertEquals(expectedInPlay, callback.lastValue("player.inplay"));
		assertEquals(expectedNotInPlay, callback.lastValue("player.notinplay"));

	}

	@Test
	public void testGameStartWith2PlayerGame() {
		statemachine.execute(newGameEvent(bob, "start"));
		statemachine.execute(newGameEvent(jim, "start"));

		String gameId = bobCallback.gameId();
		assertNotNull(gameId);
		// jim guesses 3, which is wrong
		statemachine.execute(newMoveEvent(gameId, jim, "3"));

		assertEquals("inplay", bobCallback.lastState());
		assertEquals("inplay", jimCallback.lastState());

		assertInPlay("bob", "jim");

		// bob guesses 5, which is right
		statemachine.execute(newMoveEvent(gameId, bob, "5"));

		assertEquals("gameover", bobCallback.lastState());
		assertEquals("gameover", jimCallback.lastState());

		assertEquals("bob", bobCallback.winner());
		assertEquals("jim", jimCallback.loser());

	}

	private GameEvent newGameEvent(Player player, String action) {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias", player
				.getAlias()));
		gameEvent.addAttribute(new GameEventAttribute("action", action));
		gameEvent.addAttribute(new GameEventAttribute("game.winningnumber","5"));
		return gameEvent;
	}

	private GameEvent newMoveEvent(String gameId, Player player, String move) {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias", player
				.getAlias()));
		gameEvent.addAttribute(new GameEventAttribute("move", move));
		gameEvent.addAttribute(new GameEventAttribute("action", "move"));
		gameEvent.addAttribute(new GameEventAttribute("game.id", gameId));
		return gameEvent;
	}

	private static class AssertingGameEventCallback implements
			GameEventCallback {

		private List<GameEvent> events = Lists.newArrayList();

		@Override
		public void onEvent(GameEvent event) {
			events.add(event);
		}

		public GameEvent lastEvent() {
			return events.get(events.size() - 1);
		}

		public String lastState() {
			return lastValue("state");
		}

		public int numberOfEvents() {
			return events.size();
		}

		public String lastValue(String name) {
			return lastEvent().getAttribute(name).getValue();
		}

		public String winner() {
			return lastEvent().getAttribute("player.winner").getValue();
		}

		public String loser() {
			return lastEvent().getAttribute("player.loser").getValue();
		}

		public String gameId() {
			return lastValue("game.id");
		}
	}
}
