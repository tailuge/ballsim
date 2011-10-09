package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGameState;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;
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
		if (!game.inPlay().equals(getInPlay())) {
			throw new IllegalArgumentException(
					"player inPlay is not the same as Game expects");
		}
		// update the game event
		GameEvent gameEvent = getGameEvent();
		game.setGameEvent(gameEvent.copy());
		//
		notifyWatchersWatching(game.notInPlay(), gameEvent);
		//
		SimplePoolMove shot = eventToSimplePoolMove.apply(getGameEvent());
		log.info("Shot: " + shot);
		SimplePoolGameState state = game.evaluateShot(shot);
		log.info("Game State evaluated as " + state.name());
		state.doMove(this);
	}

	private void notifyWatchersWatching(Player notInPlay, GameEvent event) {
		GameEvent copy = event.copy();
		copy.addAttribute(new GameEventAttribute("state", "watching"));
		notInPlay.onEvent(event);
		getGame().notifyWatchers(event);
	}

	private void notifyStopWatching(Player watcher) {
		GameEventHelper helper = new GameEventHelper(newGameEvent());
		helper.setValue("action", "requestWatchGames");
		helper.setValue("player.alias", watcher.getAlias());
		watcher.getPlayerAttributes().removeAttribute("game.watch.id");
		getStatemachine().execute(helper.getEvent());
	}

	/**
	 * On stop watching we go back to request games
	 */
	private void notifyStopWatching() {
		for (Player player : getGame().getWatchers())
			notifyStopWatching(player);
	}

	/**
	 * Invoked when the player is aiming
	 */
	@Action
	public void aim() {
		Player notInPlay = getGame().notInPlay();
		notInPlay.onEvent(getGameEvent());
	}

	public void notifyMove() {
		SimplePoolGame game = getGame();
		game.inPlay().onEvent(newGameEvent("aiming"));
		notifyWatchersWatching(game.notInPlay(),newGameFoulEvent("viewing"));
	}

	public void notifyGameOver() {
		SimplePoolGame game = getGame();
		PlayerState.LoggedIn.set(game.inPlay(), game.notInPlay());
		getGameHome().deleteGame(game.getId());
		//
		game.inPlay().onEvent(newGameEvent("winner"));
		game.notInPlay().onEvent(newGameEvent("loser"));
		//
		notifyStopWatching();
		notifyGamesInProgressUpdate();
	}

	public void turnOver() {
		getGame().turnOver();
		getGame().setGameState(InPlay.class);
	}

	/**
	 * Handling an unexpected event of logging in, we will abort back to login
	 * stage
	 */
	private void abortGame() {
		SimplePoolGame game = getGame();
		if (game != null) {
			getGameHome().deleteGame(game.getId());
			forceToLogin(game.inPlay());
			forceToLogin(game.notInPlay());
		} else {
			// can happen due to browser refreshes etc.
			forceToLogin(getInPlay());
		}
		notifyGamesInProgressUpdate();
	}

	@Action
	public void login() {
		log.warning("Invalid action login received moving back to loggedin and aborting Game");
		abortGame();
	}

	@Action
	public void requestGame() {
		log.warning("Invalid action requestGame received moving back to loggedin and aborting Game");
		abortGame();
	}

}
