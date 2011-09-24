package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGameState;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.billiards.simplepool.model.SimplePoolTable;
import org.oxtail.game.state.Action;
import org.oxtail.game.state.GameEventContext;

/**
 * State representing the game in play, with one player to guess (generically
 * 'move')
 */
public class InPlay extends AbstractSimplePoolGameState {

	public InPlay(
			GameEventContext<SimplePoolGame, SimplePoolMove, SimplePoolTable> context) {
		super(context);
	}

	@Action
	public void shot() {
		SimplePoolGame game = getGame();
		SimplePoolMove shot = getMove();
		SimplePoolGameState state = game.evaluateShot(shot);
		state.doMove(this);
	}

	@Action
	public void aim() {
		// TODO
	}

	public void notifyMove() {
		SimplePoolGame game = getGame();
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

	public void notifyGameOver() {
		SimplePoolGame game = getGame();
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

	public void turnOver() {
		getGame().turnOver();
		getGame().setGameState(InPlay.class);
	}

	@Action
	public void chat() {
		GameEvent event = context.getGameEvent();
		String chatTo = getGame().notInPlay().getAlias();
		String chatFrom = getGame().inPlay().getAlias();
		event.addAttribute(new GameEventAttribute("state", "inplay"));
		event.addAttribute(new GameEventAttribute("player.inplay", getGame()
				.inPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("player.notinplay", getGame()
				.notInPlay().getAlias()));
		event.addAttribute(new GameEventAttribute("chat.from", chatFrom));
		event.addAttribute(new GameEventAttribute("chat.to", chatTo));
		event.addAttribute(new GameEventAttribute("game.id", getGame()
				.getVersion().getId()));
		getGame().notInPlay().onEvent(event);
	}
}
