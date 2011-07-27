package org.oxtail.game.state;

/**
 * State where the player is yet to enter the system
 * 
 * @author liam knox
 */
public class PlayerPendingLogin {

	/**
	 * invoked to login into the game system
	 */
	public void actionLogin() {
		// TODO look up player and validate password
	}

	/**
	 * invoked to register the player with the system
	 */
	public void actionRegister() {
		// TODO validate new player and store player
	}
}
