package org.oxtail.game.billiards.nineball.model;

import org.oxtail.game.billiards.model.BilliardBallCategory;
import org.oxtail.game.billiards.model.BilliardsGameCategory;

/**
 * Nine Ball specific billiard ball category
 * TODO interface to make this polymorphic to the ball, table etc
 * @author liam knox
 */
public enum NineBallBallCategory {

	ONE_BALL(BilliardBallCategory.ONE);

	private BilliardBallCategory ballCategory;

	private NineBallBallCategory(BilliardBallCategory ballCategory) {
		this.ballCategory = ballCategory;
	}

	public BilliardBallCategory getBallCategory() {
		return ballCategory;
	}

	public BilliardsGameCategory getGameCategory() {
		return BilliardsGameCategory.NineBall;
	}

}
