package org.oxtail.game.billiards.nineball.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public final class NineBallTable extends BilliardsTable {

	private static final Comparator<BilliardBall> ValueComparator = new Comparator<BilliardBall>() {
		@Override
		public int compare(BilliardBall o1, BilliardBall o2) {
			return o1.getTableState().ordinal() - o2.getTableState().ordinal();
		}
	};

	private final BilliardBall cueBall;

	public NineBallTable() {
		super(BilliardBall.create(NineBallBallCategory.valueBalls()),
				BilliardsGameCategory.NineBall);
		cueBall = new BilliardBall(NineBallBallCategory.CUE_BALL);
	}

	@Override
	protected void rack() {
		for (BilliardBall ball : balls())
			ball.rack();
	}

	/**
	 * return the next ball expected to be hit
	 */
	public BilliardBall getNextBallToHit() {
		List<BilliardBall> balls = getBallsLeftOnTable();
		Collections.sort(balls, ValueComparator);
		return balls.get(0);
	}

	protected BilliardBall nineBall() {
		return getBall(NineBallBallCategory.NINE_BALL);
	}

	public boolean isNineBallPotted() {
		return nineBall().isPotted();
	}

	public boolean isCueBallOnTable() {
		return cueBall.isOnTable();
	}

	public boolean isCueBallPotted() {
		return cueBall.isPotted();
	}
	
	public BilliardBall getBallStruckByCueBall() {
		List<BilliardBall> struck = Lists.newArrayList(Iterables.filter(
				getBallsLeftOnTable(), StruckByCueBallFilter));
		
		if (struck.size() != 1)
			throw new IllegalStateException("No ball struck by cue ball");

		return struck.get(0);		
	}

	private static final Predicate<BilliardBall> StruckByCueBallFilter = new Predicate<BilliardBall>() {

		@Override
		public boolean apply(BilliardBall ball) {
			return ball.isStruckByCueBall();
		}

	};
}
