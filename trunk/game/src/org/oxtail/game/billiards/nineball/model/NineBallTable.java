package org.oxtail.game.billiards.nineball.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallNotFoundException;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

public final class NineBallTable extends BilliardsTable {

	private static final Comparator<BilliardBall> PotOrderComparator = new Comparator<BilliardBall>() {
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
	public BilliardBall getNextBallToHit() throws BilliardBallNotFoundException {
		List<BilliardBall> balls = getBallsLeftOnTable();
		if (balls.size() == 0)
			throw new BilliardBallNotFoundException();

		Collections.sort(balls, PotOrderComparator);
		return balls.get(0);
	}

	public BilliardBall cueBall() {
		return cueBall;
	}

	public BilliardBall oneBall() {
		return getBall(NineBallBallCategory.ONE_BALL);
	}

	public BilliardBall nineBall() {
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

}
