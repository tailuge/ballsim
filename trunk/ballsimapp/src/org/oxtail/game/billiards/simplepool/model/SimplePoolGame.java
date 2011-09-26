package org.oxtail.game.billiards.simplepool.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.GameVersion;
import org.oxtail.game.model.Player;

public class SimplePoolGame extends Game<SimplePoolTable> {

	private static final AtomicInteger INSTANCE_COUNT = new AtomicInteger();

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

	private BilliardBall previousBallState(BilliardBall ball) {
		return table().getBall(ball.getCategory());
	}

	private boolean isPottedByShot(BilliardBall ball) {
		return ball.isPotted() && !previousBallState(ball).isPotted();
	}

	/**
	 * Evaluate the shot against the table and decided how the game proceeds
	 */
	public SimplePoolGameState evaluateShot(SimplePoolMove shot) {
		try {
			if (shot.cueBall().isPotted())
				// whites down so foul
				return SimplePoolGameState.Foul;
			boolean anythingHit = false;
			for (BilliardBall ball : shot.getPotted()) {
				if (isPottedByShot(ball))
					// something has been potted so we continue
					return SimplePoolGameState.TurnContinued;
				if (!anythingHit && ball.isStruckByCueBall())
					anythingHit = true;
			}
			return anythingHit ? SimplePoolGameState.TurnOver
					: SimplePoolGameState.Foul;
		} finally {
			applyShot(shot);
		}
	}

	/**
	 * Update the table with the new game state
	 */
	private void applyShot(SimplePoolMove shot) {
		for (BilliardBall ball : shot.getPotted())
			previousBallState(ball).apply(ball);
	}

}
