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

/**
 * [gamestart] <-{start}-> [playertoguess] <-{move}-> [gameover]
 */
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
		final GameEventHelper event = new GameEventHelper(gameEvent);
		Player player = gameHome.findPlayer(event.getString("player.alias"));
		autoLogin(player);
		String action = event.getString("action");
		GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context = newContext(gameEvent);
		//
		if (event.hasValue("game.id")) {
			// assume we are in a game 
			NumberGuessGame game = (NumberGuessGame) gameHome.findGame(event
					.getString("game.id"));
			String numberGuess = event.getString("move");
			NumberGuessMove move = new NumberGuessMove(player,
					Integer.parseInt(numberGuess));
			context.setGame(game);
			context.setMove(move);
			executor.execute(
					stateFactory.createState(game.getStateId(), context),
					action);
		} else { // player not in game we assume
			context.setInPlay(player);
			executor.execute(new GameStart(context), action);
		}
	}

	private GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> newContext(
			GameEvent gameEvent) {
		GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard> context = new GameEventContext<NumberGuessGame, NumberGuessMove, NumberGuessBoard>();
		context.setGameHome(gameHome);
		context.setGameEvent(gameEvent);
		return context;
	}

}
