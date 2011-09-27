package org.oxtail.game.billiards.simplepool.model;

import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallCategory;
import org.oxtail.game.billiards.model.BilliardBallTableState;
import org.oxtail.game.model.Move;

import com.google.common.collect.Lists;

public class SimplePoolMove extends Move {

	private List<BilliardBall> potted = Lists.newArrayList();
	private BilliardBall cueBall;

	public SimplePoolMove() {
		cueBall = new BilliardBall(SimplePoolBallCategory.CUE_BALL);
		cueBall.setTableState(BilliardBallTableState.OnTable);
	}

	public List<BilliardBall> getPotted() {
		return potted;
	}

	public BilliardBall cueBall() {
		return cueBall;
	}

	public void setBallAsPotted(int ballId) {
		if (ballId == 0) {
			cueBall.setTableState(BilliardBallTableState.Potted);
			return;
		}
		BilliardBallCategory ballCategory = SimplePoolBallCategory.values()[ballId];
		BilliardBall ball = new BilliardBall(ballCategory);
		ball.setTableState(BilliardBallTableState.Potted);
		potted.add(ball);
	}

	public boolean somethingPotted() {
		return potted.size() > 0;
	}

	@Override
	public String toString() {
		return "SimplePoolMove [potted=" + potted + ", cueBall=" + cueBall
				+ "]";
	}

}
