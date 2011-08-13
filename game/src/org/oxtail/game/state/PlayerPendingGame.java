package org.oxtail.game.state;

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
		// player().pendingAccept();
		// challenger().challenged();
		
		// others().playersUnavailable(player(),challenger());
	}
	
	/**
	 * Challenge another player
	 */
	@Action
	public void accept() {
		// create Game and join it
		// Game game = createGame();
		
		// player().inGame();
		// challenger().inGame();
		
		// TODO what should we do about starting the game, let the game decide who goes first ?
	}
	
	@Action
	public void decline() {
		// player go back to pending
		// player().pendingGame();
		// challenger().pendingGame();
		// others().playersAvailable(player(),challenger());
	}
	
	@Override
	protected void afterStateExecution() {
	}

	
}
