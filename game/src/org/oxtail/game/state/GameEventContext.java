package org.oxtail.game.state;

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

	private G game;
	private M move;
	private Player inPlay;
	private Player against;
	private GameHome home;

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
		return inPlay;
	}

	public GameHome getHome() {
		return home;
	}

	public void setHome(GameHome home) {
		this.home = home;
	}

	public void setInPlay(Player inPlay) {
		this.inPlay = inPlay;
	}

	public Iterable<Player> getOthers() {
		return home.findPlayers(new Predicate<Player>() {

			@Override
			public boolean apply(Player p) {
				return !p.equals(inPlay);
			}
		});
	}
}
