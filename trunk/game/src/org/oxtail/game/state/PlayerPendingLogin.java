package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.PlayingSpace;

/**
 * State where the player is yet to enter the system
 * 
 * @author liam knox
 */
public class PlayerPendingLogin<G extends Game<S>, M extends Move, S extends PlayingSpace>
		extends PlayerState<G, M, S> {

	public PlayerPendingLogin(GameEventContext<G, M, S> context) {
		super(context);
	}

	/**
	 * invoked to login into the game system
	 */
	@Action
	public void login() {
		others().notifyOfPlayerPendingGame(player());
		player().notifySelfPendingGame();
	}

	@Override
	protected void afterStateExecution() {

	}
}
