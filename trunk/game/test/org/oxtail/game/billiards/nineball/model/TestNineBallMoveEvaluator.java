package org.oxtail.game.billiards.nineball.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oxtail.game.billiards.model.BilliardBallNotFoundException;

public class TestNineBallMoveEvaluator {

	private NineBallTable table;
	private NineBallMove move;
	private NineBallMoveEvaluator evaluator;
	
	@Before
	public void setUp() throws Exception {
		table = new NineBallTable();
		move = new NineBallMove();
		evaluator = new NineBallMoveEvaluator(table, move);
	}

	@Test
	public void testNineBallMoveEvaluator() {
		assertNotNull(evaluator);
	}

	@Test
	public void testIsBallHitValid() throws BilliardBallNotFoundException {
		move.setFirstBallStruckByCueBall(table.getBall(NineBallBallCategory.ONE_BALL));
		assertTrue(evaluator.isBallHitValid());
	}

}
