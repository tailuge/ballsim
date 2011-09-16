package org.oxtail.game.numberguess.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

public class GameStart extends AbstractNumberGuessGameState {

	public GameStart(
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context) {
		super(context);
	}

	@Action
	public void startGame() {
		Player player = getInPlay();
		Player opponent = findOpponent();
		
		if (opponent != null) {
			opponent.setState(PlayerState.InPlay.name());
			player.setState(PlayerState.InPlay.name());
			NumberGuessGame game = new NumberGuessGame(5, new NumberGuessBoard(0, 10),player,opponent);
			getGameHome().store(game);
			game.setGameState(PlayerToGuess.class);
			notifyGameStarted(game);
		}
		else {
			notifyAwaitingGame(player);
		}
	}
	
	private void notifyAwaitingGame(Player player) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "awaitinggame"));
		player.onEvent(event);
	}

	private void notifyGameStarted(NumberGuessGame game) {
		GameEvent event = new GameEvent();
		event.addAttribute(new GameEventAttribute("state", "gamestarted"));
		event.addAttribute(new GameEventAttribute("player.inplay",game.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay",game.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("game.id", game.getVersion().getId()));
		event.addAttribute(new GameEventAttribute("game.board", game.getCurrentPlayingSpace().toString()));
		game.notify(event);
	}
}
