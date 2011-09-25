package org.motion.ballsimapp.proxy;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventCallback;
import org.motion.ballsimapp.shared.GameServer;
import org.oxtail.game.billiards.simplepool.state.PlayerState;
import org.oxtail.game.billiards.simplepool.state.SimplePoolStatemachine;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.event.CallbackEnrichedGameHome;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
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
		gameHome = new CallbackEnrichedGameHome(new InMemoryGameHome(),
				callback);
		statemachine = new SimplePoolStatemachine(gameHome,
				new ReflectStateFactory(), new ReflectStateActionExecutor());
		setUpPlayers("jim", "bob");
	}

	@Override
	public void notify(GameEvent event) {
		statemachine.execute(event);
	}

	/**
	 * Temporary to hard code some players,
	 */
	private void setUpPlayers(String... playerAliases) {
		for (String playerAlias : playerAliases)
			setUpPlayer(playerAlias);
	}

	private void setUpPlayer(String playerAlias) {
		Player player = new Player(playerAlias);
		PlayerState.LoggedOut.set(player);
		gameHome.storePlayer(player);
	}

}
