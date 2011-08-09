package org.oxtail.game.state;

/**
 * State where the player is yet to enter the system
 * 
 * @author liam knox
 */
public class PlayerPendingLogin extends PlayerState {

	
	public PlayerPendingLogin(GameEventContext context) {
		super(context);
	}

	/**
	 * invoked to login into the game system
	 */
	@Action
	public void login() {
		// player().pendingGame();
	}

	/**
	 * invoked to register the player with the system
	 */
	@Action
	public void register() {
		// TODO we will look at registration etc. later
	}

	@Override
	protected void afterStateExecution() {
		
	}
}
