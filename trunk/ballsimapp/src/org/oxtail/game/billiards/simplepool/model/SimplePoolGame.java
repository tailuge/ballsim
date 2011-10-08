package org.oxtail.game.billiards.simplepool.model;

import static org.oxtail.game.billiards.simplepool.model.SimplePoolGameState.GameOver;
import static org.oxtail.game.billiards.simplepool.model.SimplePoolGameState.TurnContinued;
import static org.oxtail.game.billiards.simplepool.model.SimplePoolGameState.TurnOver;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.GameVersion;
import org.oxtail.game.model.Player;

import com.google.common.base.Function;

public class SimplePoolGame extends Game<SimplePoolTable> {

	private final static AtomicInteger INSTANCE_COUNT = new AtomicInteger();

	public static Function<Game<?>, String> toDescription = new Function<Game<?>, String>() {
		@Override
		public String apply(Game<?> game) {
			return game.getPlayer(0).getAlias() + " vs. " + game.getPlayer(1).getAlias();
		}

	};

	public SimplePoolGame(Player player1, Player player2) {
		super(new SimplePoolTable(), player1, player2);
		newGame();
	}

	private void newGame() {
		setVersion(new GameVersion(String.valueOf(INSTANCE_COUNT
				.incrementAndGet()), new Date()));

	}

	public Player player1() {
		return getPlayer(0);
	}

	public Player player2() {
		return getPlayer(1);
	}

	public Player notInPlay() {
		return player1() == inPlay() ? player2() : player1();
	}

	/**
	 * Called when the current players turn is over
	 */
	public final void turnOver() {
		if (inPlay() == player1())
			setInPlay(player2());
		else if (inPlay() == player2())
			setInPlay(player1());
		else
			throw new IllegalStateException("Can`t swap players for game! "
					+ this);
	}

	/**
	 * Game is over when all balls are down
	 */
	public boolean isGameOver() {
		return !getCurrentPlayingSpace().isBallsLeftOnTable();
	}

	private SimplePoolTable table() {
		return getCurrentPlayingSpace();
	}

	private void applyShot(SimplePoolMove shot) {
		for (BilliardBall potted : shot.getPotted())
			table().pot(potted);
	}

	/**
	 * Evaluate the shot against the table and decided how the game proceeds
	 */
	public SimplePoolGameState evaluateShot(SimplePoolMove shot) {
		applyShot(shot);
		SimplePoolGameState gameState = TurnOver;
		if (shot.somethingPotted())
			gameState = table().isBallsLeftOnTable() ? TurnContinued : GameOver;
		return shot.isFoul() ? gameState.forFoul() : gameState;
	}

	public static void resetGameCount() {
		INSTANCE_COUNT.set(0);
	}
}
