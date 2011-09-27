package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGameState;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the game in play, with one player to guess (generically
 * 'move')
 */
public class InPlay extends AbstractSimplePoolGameState {

	private GameEventToSimplePoolMove eventToSimplePoolMove = new GameEventToSimplePoolMove();

	public InPlay(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	/**
	 * Invoked when the player takes a shot
	 */
	@Action
	public void shot() {
		SimplePoolGame game = getGame();
		notifyNonPlayerWatching(game, getGameEvent().copy());
		SimplePoolMove shot = eventToSimplePoolMove.apply(getGameEvent());
		log.info("Shot: " + shot);
		SimplePoolGameState state = game.evaluateShot(shot);
		log.info("Game State evaluated as " + state.name());
		state.doMove(this);
	}

	private void notifyNonPlayerWatching(SimplePoolGame game, GameEvent event) {
		Player notInPlay = game.notInPlay();
		event.addAttribute(new GameEventAttribute("state", "watching"));
		notInPlay.onEvent(event);
	}

	/**
	 * Invoked when the player is aiming
	 */
	@Action
	public void aim() {
		Player notInPlay = getGame().notInPlay();
		notInPlay.onEvent(getGameEvent());
	}

	public void notifyFoul() {
		SimplePoolGame game = getGame();
		game.inPlay().onEvent(newGameFoulEvent("aiming"));
		game.notInPlay().onEvent(newGameFoulEvent("viewing"));
	}

	public void notifyMove() {
		SimplePoolGame game = getGame();
		game.inPlay().onEvent(newGameEvent("aiming"));
		game.notInPlay().onEvent(newGameEvent("viewing"));
	}

	public void notifyGameOver() {
		SimplePoolGame game = getGame();
		PlayerState.LoggedIn.set(game.inPlay(), game.notInPlay());
		getGameHome().deleteGame(game.getId());
		game.inPlay().onEvent(newGameEvent("winner"));
		game.notInPlay().onEvent(newGameEvent("loser"));

	}

	public void turnOver() {
		getGame().turnOver();
		getGame().setGameState(InPlay.class);
	}

	@Action
	public void chat() {
		// TODO
	}
}
