package org.oxtail.game.billiards.nineball.model;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.model.PlayerMove;

public class NineBallMoveEvaluator {

	private PlayerMove<NineBallTable> playerMove;
	
	public NineBallMoveEvaluator(PlayerMove<NineBallTable> playerMove) {
		this.playerMove = playerMove;
	}

	
	public boolean isBallHitValid()
	{
		BilliardBall target = playerMove.getBeforeMoveState().getNextBallToHit();
		BilliardBall struckByCueBall = playerMove.getAfterMoveState().getBallStruckByCueBall();
		
		return (target.getCategory().getBallCategory() == struckByCueBall.getCategory().getBallCategory());
	}
}
