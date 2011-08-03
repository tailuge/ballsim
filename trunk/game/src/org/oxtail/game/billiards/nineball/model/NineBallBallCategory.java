package org.oxtail.game.billiards.nineball.model;

import java.util.List;

import org.oxtail.game.billiards.model.BilliardBallCategory;
import org.oxtail.game.billiards.model.BilliardBallId;
import org.oxtail.game.billiards.model.BilliardsGameCategory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Nine Ball specific billiard ball category 
 * 
 * @author liam knox
 */
public enum NineBallBallCategory implements BilliardBallCategory {

	CUE_BALL(BilliardBallId.CUE), //
	ONE_BALL(BilliardBallId.ONE), //
	TWO_BALL(BilliardBallId.TWO), //
	THREE_BALL(BilliardBallId.THREE), //
	FOUR_BALL(BilliardBallId.FOUR), //
	FIVE_BALL(BilliardBallId.FIVE), //
	SIX_BALL(BilliardBallId.SIX), //
	SEVEN_BALL(BilliardBallId.SEVEN), //
	EIGHT_BALL(BilliardBallId.EIGHT), //
	NINE_BALL(BilliardBallId.NINE);

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

	/**
	 * Return all ball types bar the cue ball
	 */
	public static List<NineBallBallCategory> valueBalls() {
		return Lists.newArrayList(Iterables.filter(
				Lists.newArrayList(values()), CueBallFilter));
	}

	private static final Predicate<NineBallBallCategory> CueBallFilter = new Predicate<NineBallBallCategory>() {

		@Override
		public boolean apply(NineBallBallCategory category) {
			return category != NineBallBallCategory.CUE_BALL;
		}

	};
}
