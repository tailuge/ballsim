package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the player logged out
 */
public class LoggedOut extends AbstractSimplePoolGameState {

	public LoggedOut(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	@Action
	public void login() {
		Player player = getInPlay();
		PlayerState.LoggedIn.set(player);
		notifyLoggedIn(player);
	}

	private void notifyLoggedIn(Player player) {
		player.onEvent(event("state=loggedin"));
	}
}
