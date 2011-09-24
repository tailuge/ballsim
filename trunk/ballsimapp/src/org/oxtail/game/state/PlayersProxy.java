package org.oxtail.game.state;

import java.util.List;

import org.motion.ballsimapp.shared.GameEvent;

public class PlayersProxy {

	private List<PlayerProxy> players;

	public PlayersProxy(List<PlayerProxy> players) {
		this.players = players;
	}

	public void notifyOfUnavalibility(PlayerProxy... unavailable) {
		for (PlayerProxy p : players) 
			for (PlayerProxy u : unavailable)
				p.notifyOfUnavailibility(u);
	}

	public void notifyOfPlayerPendingGame(PlayerProxy playerPendingGame) {
		for (PlayerProxy p : players)
			p.notifyOfPlayerPendingGame(playerPendingGame);
	}

	public void notifyOf(GameEvent event) {
		for (PlayerProxy p : players)
			p.getPlayer().onEvent(event);
	}

}
