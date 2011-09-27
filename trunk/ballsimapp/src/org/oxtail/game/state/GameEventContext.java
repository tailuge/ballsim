package org.oxtail.game.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;

import com.google.common.base.Predicate;

/**
 * Binds the core components of the Game and current Move and Player for access
 * via the Statemachine
 * 
 * @author liam knox
 */
public class GameEventContext<G extends Game<S>, M extends Move, S extends PlayingSpace> {

	private GameEvent gameEvent;
	private G game;
	private M move;
	private Player inPlay;
	private Player notInPlay;
	private GameHome gameHome;
	private GameStatemachine statemachine;

	public G getGame() {
		return game;
	}

	public void setGame(G game) {
		this.game = game;
	}

	public M getMove() {
		return move;
	}

	public void setMove(M move) {
		this.move = move;
	}

	public Player getInPlay() {
		return assertSet(inPlay, "inPlay");
	}

	public GameHome getGameHome() {
		return gameHome;
	}

	public void setGameHome(GameHome home) {
		this.gameHome = home;
	}

	public void setInPlay(Player inPlay) {
		this.inPlay = inPlay;
	}

	public Player getNotInPlay() {
		return notInPlay;
	}

	public void setNotInPlay(Player notInPlay) {
		this.notInPlay = notInPlay;
	}

	public Iterable<Player> getOthers() {
		return gameHome.findPlayers(new Predicate<Player>() {

			@Override
			public boolean apply(Player p) {
				return !p.equals(inPlay) && !p.equals(notInPlay);
			}
		});
	}

	protected GameStatemachine getStatemachine() {
		return statemachine;
	}

	public void setStatemachine(GameStatemachine statemachine) {
		this.statemachine = statemachine;
	}

	public GameEvent getGameEvent() {
		return gameEvent;
	}

	public void setGameEvent(GameEvent gameEvent) {
		this.gameEvent = gameEvent;
	}

	/**
	 * Asserts the a value is set and returns it
	 */
	private <T> T assertSet(T value, String name) {
		if (value == null)
			throw new NullPointerException(name + " not set on context");
		return value;
	}
}
