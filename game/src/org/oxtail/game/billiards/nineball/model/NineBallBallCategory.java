package org.oxtail.game.billiards.nineball.model;

import org.oxtail.game.billiards.model.BilliardBallCategory;
import org.oxtail.game.billiards.model.BilliardBallId;
import org.oxtail.game.billiards.model.BilliardsGameCategory;

/**
 * Nine Ball specific billiard ball category TODO interface to make this
 * polymorphic to the ball, table etc
 * 
 * @author liam knox
 */
public enum NineBallBallCategory implements BilliardBallCategory {

	ONE_BALL(BilliardBallId.ONE), TWO_BALL(BilliardBallId.TWO), THREE_BALL(
			BilliardBallId.THREE), FOUR_BALL(BilliardBallId.FOUR), FIVE_BALL(
			BilliardBallId.FIVE), SIX_BALL(BilliardBallId.SIX), SEVEN_BALL(
			BilliardBallId.SEVEN), EIGHT_BALL(BilliardBallId.EIGHT), NINE_BALL(
			BilliardBallId.NINE), ;

	private BilliardBallId ballCategory;

	private NineBallBallCategory(BilliardBallId ballCategory) {
		this.ballCategory = ballCategory;
	}

	public BilliardBallId getBallCategory() {
		return ballCategory;
	}

	public BilliardsGameCategory getGameCategory() {
		return BilliardsGameCategory.NineBall;
	}

	
}
