package org.oxtail.game.numberguess.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.model.NumberGuessBoard;
import org.oxtail.game.numberguess.model.NumberGuessGame;
import org.oxtail.game.numberguess.model.NumberGuessMove;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.GameEventContext;
import org.oxtail.game.state.GameStatemachine;
import org.oxtail.game.state.StateActionExecutor;
import org.oxtail.game.state.StateFactory;

public class NumberGuessGameStatemachine implements GameStatemachine {

	private GameHome gameHome;

	private StateFactory stateFactory;

	private StateActionExecutor executor;

	public NumberGuessGameStatemachine(GameHome gameHome,
			StateFactory stateFactory, StateActionExecutor executor) {
		this.gameHome = gameHome;
		this.stateFactory = stateFactory;
		this.executor = executor;
	}

	private void autoLogin(Player player) {
		if (PlayerState.valueOf(player.getState()) == PlayerState.LoggedOut)
			player.setState(PlayerState.LoggedIn.name());

	}

	@Override
	public void execute(GameEvent gameEvent) {
		GameEventHelper event = new GameEventHelper(gameEvent);
		Player player = gameHome.findPlayer(event.getString("player.alias"));
		autoLogin(player);
		if (gameEvent.hasAttribute("game.id")) {
			NumberGuessGame game = (NumberGuessGame) gameHome.findGame(event
					.getString("game.id"));
			String numberGuess = event.getString("guess");
			String action = event.getString("action");
			NumberGuessMove move = new NumberGuessMove(player,
					Integer.parseInt(numberGuess));
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context = new GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard>();
			context.setGame(game);
			context.setMove(move);
			context.setGameHome(gameHome);
			executor.execute(
					stateFactory.createState(game.getStateId(), context),
					action);
		} else { // player not in game we assume
			GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context = new GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard>();
			context.setInPlay(player);
			context.setGameHome(gameHome);
			new GameStart(context).startGame();
		}
	}

}
