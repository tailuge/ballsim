package org.oxtail.game.billiards.nineball.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

public class NineBallTable extends BilliardsTable {

	private static final Comparator<BilliardBall> ValueComparator = new Comparator<BilliardBall>() {
		@Override
		public int compare(BilliardBall o1, BilliardBall o2) {
			return o1.getTableState().ordinal() -o2.getTableState().ordinal();
		}
	};
	
	public NineBallTable() {
		super(BilliardBall.create(NineBallBallCategory.values()), BilliardsGameCategory.NineBall);
	}
	
	@Override
	protected void rack() {
		for(BilliardBall ball : balls())
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
	
}
