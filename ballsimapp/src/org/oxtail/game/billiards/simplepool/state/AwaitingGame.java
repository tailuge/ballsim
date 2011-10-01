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
public class AwaitingGame extends AbstractSimplePoolGameState {

	public AwaitingGame(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
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
			// no one is available, stay waiting
			PlayerState.AwaitingGame.set(player);
			notifyAwaitingGame(player);
		}
	}

	private void notifyAwaitingGame(Player player) {
		player.onEvent(event("state=awaitinggame"));
	}

	private void notifyGameStarted() {
		getGame().inPlay().onEvent(gameStartedAnd("aiming"));
		getGame().notInPlay().onEvent(gameStartedAnd("viewing"));
	}

	private GameEvent gameStartedAnd(String state) {
		GameEvent event = newGameEvent(state);
		event.addAttribute(new GameEventAttribute("game.rack.type",
				"9Ball"));
		event.addAttribute(new GameEventAttribute("game.rack.seed", "1"));
		return event;
	}
}
