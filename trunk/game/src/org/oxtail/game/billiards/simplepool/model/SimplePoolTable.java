package org.oxtail.game.billiards.simplepool.model;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

/**
 * Board for a simple number guess game i.e. numbers from 0 to 9
 * 
 * @author liam knox
 */
public class SimplePoolTable extends BilliardsTable {

	private final BilliardBall cueBall;

	public SimplePoolTable() {
		super(BilliardBall.create(SimplePoolBallCategory.valueBalls()),
				BilliardsGameCategory.SimplePool);
		cueBall = new BilliardBall(SimplePoolBallCategory.CUE_BALL);
	}

	@Override
	protected void rack() {
		for (BilliardBall ball : balls())
			ball.rack();
	}

	public BilliardBall getCueBall() {
		return cueBall;
	}

}
