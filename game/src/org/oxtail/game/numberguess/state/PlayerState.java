package org.oxtail.game.numberguess.state;

import org.oxtail.game.model.Player;

public enum PlayerState {

	InPlay, LoggedIn, LoggedOut;

	public void set(Player... players) {
		for (Player p : players)
			p.setState(name());
	}
}