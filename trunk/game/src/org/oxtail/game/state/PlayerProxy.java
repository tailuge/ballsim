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
		notifyOf(pendingGameEvent());
	}

	public void notifyOfPlayerPendingGame(PlayerProxy playerPendingGame) {
		notifyOf(pendingGameEvent(playerPendingGame.getPlayer()));
	}

	public void notifyPendingChallengeAccept() {
		setState(PlayerPendingGame.class);
		notifyOf(pendingChallengeAccept());
	}

	private GameEvent pendingGameEvent() {
		return toSelf("pendingGame");
	}

	private GameEvent pendingGameEvent(Player other) {
		return toOther("pendingGame", other);
	}

	private GameEvent pendingChallengeAccept() {
		return toSelf("pendingChallengeAccept");
	}

	private GameEvent toSelf(String action) {
		return null;
		//
		//return GameEvent.toSelf(player.getAlias(), action);
	}

	private GameEvent toOther(String action, Player other) {
		return null;
		//return GameEvent.toOther(other.getAlias(), player.getAlias(), action);
	}

	public void notifyOf(GameEvent event) {
		player.onEvent(event);
	}

	Player getPlayer() {
		return player;
	}

	public void notifyOfUnavailibility(PlayerProxy other) {
		toOther("playerUnavailable", other.getPlayer());
	}

	public void notifyChallengeOffered(PlayerProxy other) {
		toOther("challengeOffered", other.getPlayer());
	}

}
