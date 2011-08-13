package org.oxtail.game.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.StateId;

/**
 * Represents a player with actions
 */
public class PlayerProxy {
	
	private Player player;
	
	public PlayerProxy(Player player) {
		this.player = player;
	}

	protected void setState(Class<?> stateClass) {
		player.setStateId(new StateId(stateClass));
	}

	public void notifySelfPendingGame() {
		setState(PlayerPendingGame.class);
		player.notify(pendingGameEvent());
	}

	public void notifyOfPlayerPendingGame(PlayerProxy playerPendingGame) {
		player.notify(pendingGameEvent(playerPendingGame.getPlayer()));
	}

	private GameEvent pendingGameEvent() {
		return GameEvent.toSelf(player.getAlias(), "pendingGame");
	}

	private GameEvent pendingGameEvent(Player other) {
		return GameEvent.toOther(other.getAlias(), player.getAlias(),
				"pendingGame");
	}

	Player getPlayer() {
		return player;
	}
}
