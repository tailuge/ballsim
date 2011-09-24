package org.motion.ballsimapp.proxy;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventCallback;
import org.motion.ballsimapp.shared.GameServer;
import org.oxtail.game.billiards.simplepool.state.SimplePoolStatemachine;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.event.CallbackEnrichedGameHome;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
import org.oxtail.game.state.GameStatemachine;
import org.oxtail.game.state.reflect.ReflectStateActionExecutor;
import org.oxtail.game.state.reflect.ReflectStateFactory;

public class GameServerProxy implements GameServer {

	private GameStatemachine statemachine;

	public GameServerProxy(GameEventCallback callback) {
		GameHome home = new CallbackEnrichedGameHome(new InMemoryGameHome(),
				callback);
		statemachine = new SimplePoolStatemachine(home,
				new ReflectStateFactory(), new ReflectStateActionExecutor());
	}

	@Override
	public void notify(GameEvent event) {
		statemachine.execute(event);
	}

}
