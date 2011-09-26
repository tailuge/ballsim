package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
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
			context.setGame(game);
			game.setInPlay(player);
			game.setGameState(InPlay.class);
			getGameHome().store(game);
			notifyGameStarted();
		} else {
			// no one is available
			PlayerState.AwaitingGame.set(player);
			notifyAwaitingGame(player);
		}
	}

	private void notifyAwaitingGame(Player player) {
		player.onEvent(event("state=awaitinggame"));
	}

	private void notifyLoggedOut(Player player) {
		player.onEvent(event("state=loggedout"));
	}

	private void notifyGameStarted() {
		getGame().inPlay().onEvent(newGameEvent("aiming"));
		getGame().notInPlay().onEvent(newGameEvent("viewing"));
	}

}
