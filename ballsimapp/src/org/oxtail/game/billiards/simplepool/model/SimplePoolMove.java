package org.oxtail.game.billiards.simplepool.model;

import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.billiards.model.BilliardBallCategory;
import org.oxtail.game.billiards.model.BilliardBallTableState;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.Player;

import com.google.common.collect.Lists;

public class SimplePoolMove extends Move {

	private List<BilliardBall> potted = Lists.newArrayList();
	private BilliardBall cueBall;

	public SimplePoolMove(Player player) {
		super(player);
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
		BilliardBallCategory ballCategory = SimplePoolBallCategory.values()[ballId];
		BilliardBall ball = new BilliardBall(ballCategory);
		ball.setTableState(BilliardBallTableState.Potted);
		potted.add(ball);
	}
}
