package org.oxtail.game.numberguess.state;

import java.util.Random;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the Game starting, a player comes in and is paired off if
 * another is available
 */
public class GameStart extends AbstractNumberGuessGameState {

	private static final Random RANDOM = new Random();

	public GameStart(
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context) {
		super(context);
	}

	@Action
	public void start() {
		Player player = getInPlay();
		Player opponent = findOpponent();

		if (opponent != null) {
			// someone can play us
			opponent.setState(PlayerState.InPlay.name());
			player.setState(PlayerState.InPlay.name());
			NumberGuessGame game = new NumberGuessGame(createWinningNumber(),
					new NumberGuessBoard(1, 9), player, opponent);
			getGameHome().store(game);
			game.setGameState(PlayerToGuess.class);
			notifyGameStarted(game);
		} else {
			// no one is available
			notifyAwaitingGame(player);
		}
	}
	
	/**
	 * create a random winning number if none is set
	 */
	private int createWinningNumber() {
		GameEventHelper helper = new GameEventHelper(context.getGameEvent());
		if (helper.hasValue("game.winningnumber"))
			return helper.getInt("game.winningnumber");
		return 1 + RANDOM.nextInt(9);
	}

	@Action
	public void logout() {
		Player player = getInPlay();
		player.setState(PlayerState.LoggedOut.name());
		notifyLoggedOut(player);
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

	private void notifyGameStarted(NumberGuessGame game) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "gamestarted"));
		event.addAttribute(new GameEventAttribute("player.inplay", game
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay", game
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion()
				.getId()));
		event.addAttribute(new GameEventAttribute("game.board", game
				.getCurrentPlayingSpace().toString()));
		game.notify(event);
	}
}
