package org.oxtail.game.state;

import java.util.List;

public class PlayersProxy {

	private List<PlayerProxy> players;

	public PlayersProxy(List<PlayerProxy> players) {
		this.players = players;
	}

	public void notifyOfPlayerPendingGame(PlayerProxy playerPendingGame) {
		for (PlayerProxy p : players)
			p.notifyOfPlayerPendingGame(playerPendingGame);
	}

}
