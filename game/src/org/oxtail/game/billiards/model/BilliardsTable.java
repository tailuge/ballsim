package org.oxtail.game.billiards.model;

import java.util.Collections;
import java.util.List;

import org.oxtail.game.model.PlayingSpace;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public abstract class BilliardsTable extends PlayingSpace {

	private final BilliardsGameCategory category;

	private final List<BilliardBall> balls;

	private static final Predicate<BilliardBall> BallsOnTable = new Predicate<BilliardBall>() {

		@Override
		public boolean apply(BilliardBall ball) {
			return ball.getTableState() == BilliardBallTableState.OnTable;
		}

	};

	protected BilliardsTable(List<BilliardBall> balls,
			BilliardsGameCategory category) {
		this.balls = Collections.unmodifiableList(balls);
		this.category = category;
	}

	protected abstract void rack();

	protected final Iterable<BilliardBall> balls() {
		return balls;
	}

	@Override
	public String toString() {
		return "BilliardsTable [category=" + category + ", balls=" + balls
				+ "]";
	}

	/**
	 * Returns balls left on the table
	 */
	public List<BilliardBall> getBallsLeftOnTable() {
		return Lists.newArrayList(Iterables.filter(balls(), BallsOnTable));
	}

	/**
	 * Returns if any balls are left on the table to pot
	 */
	public boolean isBallsLeftOnTable() {
		return !getBallsLeftOnTable().isEmpty();
	}
	
	public BilliardBall getBall(BilliardBallCategory category) {
		for(BilliardBall ball : balls) {
			if(ball.getCategory().getGameCategory() == category.getGameCategory()
					&& ball.getCategory().getBallCategory() == category.getBallCategory())
				return ball;
		}
		throw new IllegalArgumentException("no ball found for "+category);
	}
}
