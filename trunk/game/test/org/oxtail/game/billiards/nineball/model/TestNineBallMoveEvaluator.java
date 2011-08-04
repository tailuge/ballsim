package org.oxtail.game.billiards.nineball.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oxtail.game.billiards.model.BilliardBallNotFoundException;
import org.oxtail.game.billiards.model.BilliardBallTableState;

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

	@Test
	public void testIsBallHitValidWrongBall() throws BilliardBallNotFoundException {
		move.setFirstBallStruckByCueBall(table.getBall(NineBallBallCategory.TWO_BALL));
		assertFalse(evaluator.isBallHitValid());
	}

	@Test
	public void testIsBallHitValidSecondBall() throws BilliardBallNotFoundException {
		table.getBall(NineBallBallCategory.ONE_BALL).setTableState(BilliardBallTableState.OffTable);
		move.setFirstBallStruckByCueBall(table.getBall(NineBallBallCategory.TWO_BALL));
		assertTrue(evaluator.isBallHitValid());
	}

	@Test
	public void testIsBallHitValidNone() throws BilliardBallNotFoundException {
		assertFalse(evaluator.isBallHitValid());
	}

}
