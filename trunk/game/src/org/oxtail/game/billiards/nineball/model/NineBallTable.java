package org.oxtail.game.billiards.nineball.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallNotFoundException;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

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
	public BilliardBall getNextBallToHit() throws BilliardBallNotFoundException {
		List<BilliardBall> balls = getBallsLeftOnTable();
		if (balls.size() == 0)
			throw new BilliardBallNotFoundException();
		
		Collections.sort(balls, ValueComparator);
		return balls.get(0);
	}

	public BilliardBall getCueBall()
	{
		return cueBall;
	}
	
	protected BilliardBall nineBall() {
		
		try {
			return getBall(NineBallBallCategory.NINE_BALL);
		} catch (BilliardBallNotFoundException e) {
			throw new IllegalArgumentException("no nine ball found.");
		}
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
