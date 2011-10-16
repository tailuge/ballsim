package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.CommandAction;
import org.oxtail.game.state.GameEventContext;

public class AwaitingGame extends AbstractSimplePoolGameState {

	public AwaitingGame(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	@CommandAction
	public void requestGame() {
		Player player = getInPlay();
		Player opponent = findOpponent();

		if (opponent != null) {
			// someone can play us
			PlayerState.InPlay.set(opponent, player);
			SimplePoolGame game = new SimplePoolGame(player, opponent);
			context.setGame(game);
			//
			initializeGame(player, game);
			//
			getGameHome().store(game);
			notifyGameStarted();
		} else {
			// no one is available, stay waiting
			PlayerState.AwaitingGame.set(player);
			notifyAwaitingGame(player);
		}
	}

	private void initializeGame(Player player, SimplePoolGame game) {
		game.setInPlay(player);
		game.setGameState(InPlay.class);
		GameEvent event = getGameEvent().copy();
		event.addAttribute(new GameEventAttribute("game.table.state", "rack"));
		game.setGameEvent(event);
	}

	private void notifyAwaitingGame(Player player) {
		player.onEvent(newStateEvent("awaitinggame",AwaitingGame.class));
	}

	private void notifyGameStarted() {
		getGame().inPlay().onEvent(gameStartedAnd("aiming"));
		getGame().notInPlay().onEvent(gameStartedAnd("viewing"));
		notifyGamesInProgressUpdate();
	}

	private GameEvent gameStartedAnd(String state) {
		GameEvent event = newGameEvent(state,InPlay.class);
		event.addAttribute(new GameEventAttribute("game.rack.type",
				"SimplePool"));
		event.addAttribute(new GameEventAttribute("game.rack.seed", "1"));
		return event;
	}

	/**
	 * Invalid login event, move back to be logged in
	 */
	@CommandAction
	public void login() {
		log.warning("Invalid action login received moving back to loggedin");
		forceToLogin(getInPlay());
	}

}
