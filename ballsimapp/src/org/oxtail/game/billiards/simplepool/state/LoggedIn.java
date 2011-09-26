package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
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
			game.setInPlay(player);
			game.setGameState(InPlay.class);
			getGameHome().store(game);
			notifyGameStarted2(game);
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

	private GameEvent newGameStarted(String state, SimplePoolGame game) {
		GameEvent event = event("state=" + state);
		event.addAttribute(new GameEventAttribute("player.inplay", game
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay", game
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion()
				.getId()));
		event.addAttribute(new GameEventAttribute("game.table", game
				.getCurrentPlayingSpace().toString()));
		return event;
	}

	private void notifyGameStarted(SimplePoolGame game) {
		GameEvent event = newGameStarted("gamestarted", game);
		game.notify(event);
	}

	private void notifyGameStarted2(SimplePoolGame game) {
		game.inPlay().onEvent(newGameStarted("aiming", game));
		game.notInPlay().onEvent(newGameStarted("viewing", game));

	}

}
