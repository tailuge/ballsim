package org.oxtail.game.billiards.simplepool.model;

import java.util.List;

import org.oxtail.game.billiards.model.BilliardBall;
import org.oxtail.game.model.Move;
import org.oxtail.game.model.Player;

import com.google.common.collect.Lists;

public class SimplePoolMove extends Move {

	private List<BilliardBall> balls = Lists.newArrayList();
	private BilliardBall cueBall;
	
	public SimplePoolMove(Player player) {
		super(player);
	}

	public List<BilliardBall> getBalls() {
		return balls;
	}
	
	public BilliardBall cueBall() { return cueBall; }

	
}
