package org.oxtail.game.chat.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.GameEventContext;
import org.oxtail.game.state.GameStatemachine;
import org.oxtail.game.state.StateActionExecutor;

/**
 * [gamestart] <-{start}-> [logged in]
 */
@SuppressWarnings("rawtypes")
public class ChatGameStatemachine implements GameStatemachine {

	private GameHome gameHome;

	private StateActionExecutor executor;

	public ChatGameStatemachine(GameHome gameHome, StateActionExecutor executor) {
		this.gameHome = gameHome;
		this.executor = executor;
	}

	@Override
	public void execute(GameEvent gameEvent) {
		doExecute(gameEvent);
	}

	private void doExecute(GameEvent gameEvent) {
		final GameEventHelper event = new GameEventHelper(gameEvent);
		Player player = gameHome.findPlayer(event.getString("player.alias"));
		String action = event.getString("action");
		GameEventContext context = newContext(gameEvent);

		if (event.hasValue("game.id")) {
			throw new UnsupportedOperationException();
		} else { // player not in game we assume
			context.setInPlay(player);
			executor.execute(new PlayerNotLoggedIn(context), action);
		}
	}

	private GameEventContext newContext(GameEvent gameEvent) {
		GameEventContext context = new GameEventContext();
		context.setGameHome(gameHome);
		context.setGameEvent(gameEvent);
		return context;
	}

}
