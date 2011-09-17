package org.oxtail.game.numberguess.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.numberguess.model.InvalidGuessException;
import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the game in play, with one player to guess (generically
 * 'move')
 */
public class PlayerToGuess extends
		AbstractGameState<NumberGuessGame, NumberGuessMove, NumberGuessBoard> {

	public PlayerToGuess(
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context) {
		super(context);
	}

	@Action
	public void move() {
		NumberGuessGame game = getGame();
		try {
			if (game.isGuessWin(getMove().getNumberGuessed())) {
				gameOver();
				notifyGameOver(game);
			} else {
				turnOver();
				notifyMove(game);
			}
		} catch (InvalidGuessException e) {
			System.err.println(game.inPlay().getAlias() + ", Fuckwit !!! "
					+ e.getMessage());
			turnOver();
			notifyMove(game);
		}
	}

	@Action
	public void logout() {
		NumberGuessGame game = getGame();
		PlayerState.LoggedOut.set(game.player1(), game.player2());
		notifyLoggedOut(game);
	}

	private void notifyLoggedOut(NumberGuessGame game) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "loggedout"));
		game.notify(event);
	}

	private void notifyMove(NumberGuessGame game) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "inplay"));
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

	private void notifyGameOver(NumberGuessGame game) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "gameover"));
		event.addAttribute(new GameEventAttribute("player.winner", game
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.loser", game
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion()
				.getId()));
		event.addAttribute(new GameEventAttribute("game.board", game
				.getCurrentPlayingSpace().toString()));
		game.inPlay().setState(PlayerState.LoggedIn.name());
		game.notInPlay().setState(PlayerState.LoggedIn.name());
		game.notify(event);
		getGameHome().deleteGame(game.getId());
	}

	private void gameOver() {
		// only symbolic to complete the transitions
		getGame().setGameState(GameOver.class);
	}

	private void turnOver() {
		getGame().turnOver();
		getGame().setGameState(PlayerToGuess.class);
	}

}
