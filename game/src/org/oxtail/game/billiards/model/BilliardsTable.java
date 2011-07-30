package org.oxtail.game.billiards.model;

import java.util.Collections;
import java.util.List;

import org.oxtail.game.model.PlayingSpace;

public abstract class BilliardsTable extends PlayingSpace {

	private BilliardsGameCategory category;

	private final List<BilliardBall> balls;

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

}
