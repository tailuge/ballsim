package org.oxtail.play.inarow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.oxtail.play.Board;
import org.oxtail.play.PositionEvaluator;
import org.oxtail.play.io.CheckerBoardLoader;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;

//@formatter:off
public class TestInARowPositionEvaluation {

	private static final String sample = ".OXX..." + ".XOO..." + "OOXX..."
			+ "OXOXX.." + "XXOOO.." + "OOXOXX.";

	private static final String empty = "......." + "......." + "......."
			+ "......." + "......." + ".......";

	private static final String B1 = "......." + "......." + "......."
			+ "......." + "O......" + "O......";

	private NegaMaxPositionEvaluator evaluator;
	private CheckerBoardLoader boardLoader;
	private PositionEvaluator positionEvaluator = new InARowPositionEvaluator(4);

	@Before
	public void before() {
		evaluator = new NegaMaxPositionEvaluator(positionEvaluator,
				new InARowMoveGeneator());
		boardLoader = new CheckerBoardLoader(7, 6);
	}

	private double evaluate(Board board) {
		return evaluator.evaluate(board, 13, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 1);
	}

	private void assertEvaluation(Board board, double value) {
		Assert.assertEquals(value, evaluate(board), 0.1);
	}

	private void assertEvaluation(String board, double value) {
		assertEvaluation(boardLoader.loadFromString(board), value);
	}

	@Ignore
	@Test
	public void positionEval() {
		System.out.println(positionEvaluator.evaluate(
				boardLoader.loadFromString(B1)).getScore());
	}

	// @Ignore
	@Test
	public void testEvalFromStart() {
		long start = System.currentTimeMillis();
		try {
			assertEvaluation(empty, 0.0);
		} finally {
			long end = System.currentTimeMillis();
			System.err.println(end - start);
		}
	}

}
// @formatter:on
