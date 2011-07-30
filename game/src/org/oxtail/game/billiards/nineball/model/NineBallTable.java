package org.oxtail.game.billiards.nineball.model;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardsGameCategory;
import org.oxtail.game.billiards.model.BilliardsTable;

public class NineBallTable extends BilliardsTable {

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
	public BilliardBall getNextExpectedBall() {
		return null;
	}
	
}
