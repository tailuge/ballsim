package org.oxtail.play.demo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;

public class TestDemoPositionEvaluation {

	private NegaMaxPositionEvaluator evaluator;
	private Board board = new Board(1, 1);

	@Before
	public void setUp() {

		board = board.doMove(new Move(0, 0, (int) 'A'));
		evaluator = new NegaMaxPositionEvaluator(new DemoMoveGenerator(),
				new DemoMoveGenerator());
	}

	@Test
	public void testCorrectEval() {
		Assert.assertEquals(evaluator.evaluate(board, 4,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1),3.0,0.1);
	}

}
