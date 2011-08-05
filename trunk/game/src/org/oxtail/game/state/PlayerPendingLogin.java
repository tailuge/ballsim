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
	public void actionLogin() {
	}

	/**
	 * invoked to register the player with the system
	 */
	public void actionRegister() {
	}

	@Override
	protected void afterStateExecution() {
		
	}
}
