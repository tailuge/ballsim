package org.motion.ballsimapp.proxy;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventCallback;
import org.motion.ballsimapp.shared.GameServer;
import org.oxtail.game.billiards.simplepool.home.CreatePlayerInMemoryHome;
import org.oxtail.game.billiards.simplepool.state.PlayerState;
import org.oxtail.game.billiards.simplepool.state.SimplePoolStatemachine;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.event.CallbackEnrichedGameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.GameStatemachine;
import org.oxtail.game.state.reflect.ReflectStateActionExecutor;
import org.oxtail.game.state.reflect.ReflectStateFactory;

/**
 * Interface from GWT World to State machine world
 */
public class GameServerProxy implements GameServer {

	private final GameStatemachine statemachine;
	private final GameHome gameHome;

	public GameServerProxy(GameEventCallback callback) {
		this.gameHome = newGameHome(callback);
		this.statemachine = newStatemachine();
	}

	private SimplePoolStatemachine newStatemachine() {
		return new SimplePoolStatemachine(gameHome, new ReflectStateFactory(),
				new ReflectStateActionExecutor());
	}

	private GameHome newGameHome(GameEventCallback callback) {
		return new CallbackEnrichedGameHome(new CreatePlayerInMemoryHome(), callback);
	}

	@Override
	public void notify(GameEvent event) {
		statemachine.execute(event);
	}

	@SuppressWarnings("unused")
	private void setUpPlayerInState(String playerAlias, PlayerState state) {
		Player player = new Player(playerAlias);
		state.set(player);
		gameHome.storePlayer(player);
	}

}