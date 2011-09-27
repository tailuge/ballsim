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

public class SimplePoolStatemachine implements GameStatemachine {

	private static final Logger log = Logger.getLogger(SimplePoolStatemachine.class.getName());
	
	private GameHome gameHome;

	private StateFactory stateFactory;

	private StateActionExecutor executor;

	public SimplePoolStatemachine(GameHome gameHome, StateFactory stateFactory,
			StateActionExecutor executor) {
		this.gameHome = gameHome;
		this.stateFactory = stateFactory;
		this.executor = executor;
	}

	@Override
	public void execute(GameEvent gameEvent) {
		
		try {
			log.warning("Statemachine: Received Event "+gameEvent);
			doExecute(gameEvent);
		} catch (Exception e) {
			System.err.println("failed to execute : " + gameEvent);
			e.printStackTrace(System.err);
		}
	}

	private void doExecute(GameEvent gameEvent) {
		final GameEventHelper event = new GameEventHelper(gameEvent);
		Player player = gameHome.findPlayer(event.getString("player.alias"));
		String action = event.getString("action");
		GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context = newContext(gameEvent,player);
		//
		if (event.hasValue("game.id")) {

			// assume we are in a game
			SimplePoolGame game = (SimplePoolGame) gameHome.findGame(event
					.getString("game.id"));
			SimplePoolMove move = new SimplePoolMove(player);
			context.setGame(game);
			context.setMove(move);
			// execute for game state
			executor.execute(
					stateFactory.createState(game.getStateId(), context),
					action);
		} else { // execute off the player state
			executor.execute(stateFactory.createState(
					getStateIdForPlayer(player), context), action);

		}
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
		return context;
	}

}
