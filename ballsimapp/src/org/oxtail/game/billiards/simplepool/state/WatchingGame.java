package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

public class WatchingGame extends AbstractSimplePoolGameState {

	public WatchingGame(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	/**
	 * Invalid login event, move back to be logged in
	 */
	@Action
	public void login() {
		log.warning("Invalid action login received moving back to loggedin");
		forceToLogin(getInPlay());
	}

}
