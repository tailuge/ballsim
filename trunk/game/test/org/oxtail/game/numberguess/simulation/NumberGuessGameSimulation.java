package org.oxtail.game.numberguess.simulation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.oxtail.game.event.GameEvent;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.state.NumberGuessGameStatemachine;
import org.oxtail.game.numberguess.state.PlayerState;
import org.oxtail.game.state.GameStatemachine;
import org.oxtail.game.state.StateActionExecutor;
import org.oxtail.game.state.StateFactory;
import org.oxtail.game.state.reflect.ReflectStateActionExecutor;
import org.oxtail.game.state.reflect.ReflectStateFactory;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

public class NumberGuessGameSimulation {

	private GameStatemachine statemachine;
	private GameHome gameHome = new InMemoryGameHome();
	private StateFactory stateFactory = new ReflectStateFactory();
	private StateActionExecutor stateActionExecutor = new ReflectStateActionExecutor();

	/** main executor for the simulation */
	private ExecutorService simulationExecutor = Executors
			.newFixedThreadPool(10);
	/** executor for the state machine to accept {@link GameEvent}'s on */
	private ExecutorService statemachineExecutor = Executors
			.newFixedThreadPool(10);

	@Before
	public void before() {
		statemachine = new TaskBasedStatemachine(
				new NumberGuessGameStatemachine(gameHome, stateFactory,
						stateActionExecutor), statemachineExecutor);

	}

	@Ignore
	@Test
	public void simulateTwoPlayers() throws InterruptedException {
		simulateWith("bob", "jim");
		terminateSimulation();
	}

	@Ignore
	@Test
	public void simulateFourPlayers() throws InterruptedException {
		simulateWith("gadaffi", "obama", "hitler", "merkel");
		terminateSimulation();
	}

	@Test
	public void simulateReligousPlayers() throws InterruptedException {
		simulateWith("jesus", "buddha", "matthew", "mark", "luke", "john",
				"muhammed", "allah", "The Pope", "GOD");
		terminateSimulation();
	}

	private NumberGuessPlayer playerTask(Player player) {
		return new NumberGuessPlayer(thinker(), player, statemachine);
	}

	private void simulateWith(String... players) {
		statemachineExecutor.submit(new SimulationStatusThread());
		for (String alias : players) {
			Player player = new Player(alias);
			PlayerState.LoggedOut.set(player);
			gameHome.storePlayer(player);
			simulateWith(player);
		}
	}

	private void simulateWith(Player player) {
		simulationExecutor.submit(playerTask(player));
	}

	/**
	 * terminate the simulation, waits for it to complete in a reasonable, time
	 * i.e. all games should complete
	 */
	private void terminateSimulation() {
		System.err.println("simulation running");
		simulationExecutor.shutdown();
		try {
			simulationExecutor.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			System.err.println("simulation timeout");
			simulationExecutor.shutdownNow();
		}
		System.err.println("simulation complete");
	}

	/**
	 * Just returns how we think
	 */
	private static Supplier<Long> thinker() {
		return new Supplier<Long>() {
			@Override
			public Long get() {
				return 1000l + (long) (Math.random() * 1000);
			}
		};
	}

	/**
	 * Prints out periodic games statuses,
	 */
	private class SimulationStatusThread implements Runnable {

		public void run() {
			List<Game<?>> snapshot;
			try {
				System.err.println(">>> START");
				do {
					Thread.sleep(10000);
					Predicate<Game<?>> allGames = Predicates.alwaysTrue();
					snapshot = Lists.newArrayList(gameHome.findGames(allGames));
					StringBuilder sb = new StringBuilder();
					for (Game<?> g : snapshot) {
						NumberGuessGame game = (NumberGuessGame) g;
						sb.append(game.toSummaryString()).append(" : ");
					}
					if (snapshot.size() > 0)
						System.err.println("\n>>> " + snapshot.size()
								+ " game(s) in progess: " + sb.toString()
								+ "\n");
				} while (!snapshot.isEmpty());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			System.err.println(">>> TERMINATED");
		}
	}

}
