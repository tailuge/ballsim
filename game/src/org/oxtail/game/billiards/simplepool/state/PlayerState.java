package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.model.Player;

public enum PlayerState {

	InPlay, LoggedIn, LoggedOut, AwaitingGame;

	public void set(Player... players) {
		for (Player p : players)
			p.setState(name());
	}
}
