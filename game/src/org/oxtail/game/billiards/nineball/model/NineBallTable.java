package org.oxtail.game.billiards.nineball.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallCategory;
import org.oxtail.game.billiards.model.BilliardBallId;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class NineBallTable extends BilliardsTable {

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

}
