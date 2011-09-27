package org.oxtail.game.billiards.nineball.model;

import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.model.Move;

/**
 * A Move in Nine Ball, in this case mostly taking a Shot
 * <p>
 * Recorded data of what happend after the player performs a shot
 * 
 * @author liam knox
 */
public class NineBallMove extends Move {

	private BilliardBall firstBallStruckWithCue;
	private BilliardBall firstBallStruckByCueBall;
	private List<BilliardBall> ballsPottedInOrder;
	private List<BilliardBall> ballsWhichCollidedWithCushions;

	public NineBallMove() {
		super();
	}

	public BilliardBall getFirstBallStruckWithCue() {
		return firstBallStruckWithCue;
	}

	public void setFirstBallStruckWithCue(BilliardBall firstBallStruckWithCue) {
		this.firstBallStruckWithCue = firstBallStruckWithCue;
	}

	public BilliardBall getFirstBallStruckByCueBall() {
		return firstBallStruckByCueBall;
	}

	public void setFirstBallStruckByCueBall(
			BilliardBall firstBallStruckByCueBall) {
		this.firstBallStruckByCueBall = firstBallStruckByCueBall;
	}

	public List<BilliardBall> getBallsPottedInOrder() {
		return ballsPottedInOrder;
	}

	public void setBallsPottedInOrder(List<BilliardBall> ballsPottedInOrder) {
		this.ballsPottedInOrder = ballsPottedInOrder;
	}

	public List<BilliardBall> getBallsWhichCollidedWithCushions() {
		return ballsWhichCollidedWithCushions;
	}

	public void setBallsWhichCollidedWithCushions(
			List<BilliardBall> ballsWhichCollidedWithCushions) {
		this.ballsWhichCollidedWithCushions = ballsWhichCollidedWithCushions;
	}

	public boolean isAnyBallHitByCueBall() {
		return firstBallStruckByCueBall != null;
	}

}
