package org.oxtail.game.billiards.simplepool.state;

import java.util.logging.Logger;

import org.motion.ballsimapp.shared.GameEvent;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.StateId;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.GameEventContext;
import org.oxtail.game.state.GameStatemachine;
import org.oxtail.game.state.StateActionExecutor;
import org.oxtail.game.state.StateFactory;

/**
 * <b>Simple Pool Statemachine</b>
 * <p>
 * Protocol
 * <p>
 * 
 * Event Attribute definitions
 * <p>
 * game.id // string id of the game<br>
 * game.shot.ballspotted // balls potted, empty indicates none, comma separated
 * indicates which<br>
 * 
 */
public class SimplePoolStatemachine implements GameStatemachine {

	private static final Logger log = Logger
			.getLogger(SimplePoolStatemachine.class.getName());

	private final GameHome gameHome;
	private final StateFactory stateFactory;
	private final StateActionExecutor executor;

	public SimplePoolStatemachine(GameHome gameHome, StateFactory stateFactory,
			StateActionExecutor executor) {
		this.gameHome = gameHome;
		this.stateFactory = stateFactory;
		this.executor = executor;
	}

	@Override
	public void execute(GameEvent gameEvent) {
		log.info("Statemachine: Received Event " + gameEvent);
		doExecute(gameEvent);
	}

	private void doExecute(GameEvent gameEvent) {
		final GameEventHelper event = new GameEventHelper(gameEvent);
		Player player = gameHome.findPlayer(event.getString("player.alias"));
		String action = event.getString("action");
		GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context = newContext(
				gameEvent, player);
		if (event.hasValue("game.id"))
			executeForGame(event.getString("game.id"), context, action);
		else
			executeForPlayer(player, context, action);
	}

	private void executeForPlayer(
			Player player,
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context,
			String action) {
		executor.execute(
				stateFactory.createState(getStateIdForPlayer(player), context),
				action);
	}

	private void executeForGame(
			String gameId,
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context,
			String action) {
		SimplePoolGame game = (SimplePoolGame) gameHome.findGame(gameId);
		context.setGame(game);
		executor.execute(stateFactory.createState(game.getStateId(), context),
				action);

	}

	private StateId getStateIdForPlayer(Player player) {
		return new StateId(getClass().getPackage().getName() + "."
				+ player.getState());
	}

	private GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> newContext(
			GameEvent gameEvent, Player player) {
		GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context = new GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable>();
		context.setGameHome(gameHome);
		context.setGameEvent(gameEvent);
		context.setInPlay(player);
		context.setStatemachine(this);
		return context;
	}
}