package org.oxtail.game.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.model.Player;

/**
 * State where the player is waiting to join a Game
 * 
 * @author liam knox
 */
public class PlayerPendingGame extends PlayerState {

	public PlayerPendingGame(GameEventContext context) {
		super(context);
	}

	/**
	 * Challenge another player
	 */
	@Action
	public void challenge() {
		inPlay().notifyPendingChallengeAccept();
		notInPlay().notifyChallengeOffered(inPlay());
		others().notifyOfUnavalibility(inPlay(), notInPlay());
	}

	/**
	 * Challenge is accepted so we should start the game
	 */
	@Action
	public void challengeAccepted() {
		Player player1 = inPlay().getPlayer();
		Player player2 = notInPlay().getPlayer();
		//GameEvent gameStart = GameEvent.toOther(player1.getAlias(), player2.getAlias(), "startGame");
		//getStatemachine().execute(gameStart);
	}
	
	@Override
	protected void afterStateExecution() {
	}

}
