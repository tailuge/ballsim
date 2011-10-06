package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the player logged in
 */
public class LoggedIn extends AbstractSimplePoolGameState {

	public LoggedIn(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	@Action
	public void logout() {
		Player player = getInPlay();
		PlayerState.LoggedOut.set(player);
		notifyLoggedOut(player);
	}

	@Action
	public void requestGame() {
		Player player = getInPlay();
		PlayerState.AwaitingGame.set(player);
		getStatemachine().execute(getGameEvent());
	}

	private void notifyLoggedOut(Player player) {
		player.onEvent(newStateEvent("loggedout"));
	}

}
