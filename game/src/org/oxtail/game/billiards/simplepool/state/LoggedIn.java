package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.state.PlayerState;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the player logged in
 */
public class LoggedIn extends AbstractSimplePoolGameState {

	public LoggedIn(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	@Action
	public void logout() {
		Player player = getInPlay();
		PlayerState.LoggedOut.set(player);
		notifyLoggedOut(player);
	}

	@Action
	public void requestGame() {
		Player player = getInPlay();
		Player opponent = findOpponent();

		if (opponent != null) {
			// someone can play us
			PlayerState.InPlay.set(opponent, player);
			SimplePoolGame game = new SimplePoolGame(player, opponent);
			game.setGameState(InPlay.class);
			notifyGameStarted(game);
		} else {
			// no one is available
			notifyAwaitingGame(player);
		}
	}

	private void notifyAwaitingGame(Player player) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "awaitinggame"));
		player.onEvent(event);
	}

	private void notifyLoggedOut(Player player) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "loggedout"));
		player.onEvent(event);
	}

	private void notifyGameStarted(SimplePoolGame game) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "gamestarted"));
		event.addAttribute(new GameEventAttribute("player.inplay", game
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay", game
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion()
				.getId()));
		event.addAttribute(new GameEventAttribute("game.table", game
				.getCurrentPlayingSpace().toString()));
		game.notify(event);
	}
}
