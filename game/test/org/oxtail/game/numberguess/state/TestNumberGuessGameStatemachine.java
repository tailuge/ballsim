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
		bob.setState(PlayerState.LoggedOut.name());
		jim.setState(PlayerState.LoggedOut.name());
		gameHome.storePlayer(bob);
		gameHome.storePlayer(jim);
	}

	@Test
	public void testPlayerOneJoins() {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias", bob
				.getAlias()));
		statemachine.execute(gameEvent);
		assertEquals("awaitinggame", bobCallback.lastState());
	}

	@Test
	public void testGameStartWith2Players() {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias", bob
				.getAlias()));
		statemachine.execute(gameEvent);
		gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias", jim
				.getAlias()));
		statemachine.execute(gameEvent);
		assertEquals("gamestarted", bobCallback.lastState());
		assertEquals("gamestarted", jimCallback.lastState());
		assertEquals(1, jimCallback.numberOfEvents());
		assertEquals(2, bobCallback.numberOfEvents());
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
			return lastEvent().getAttribute("state").getValue();
		}

		public int numberOfEvents() {
			return events.size();
		}
	}
}
