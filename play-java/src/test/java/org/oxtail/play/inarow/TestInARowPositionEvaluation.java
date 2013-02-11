package org.oxtail.play.inarow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oxtail.play.Board;
import org.oxtail.play.io.CheckerBoardLoader;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;

//@formatter:off
public class TestInARowPositionEvaluation {

    private static final String sample = ".OXX..."+
    									 ".XOO..."+
    									 "OOXX..."+
    									 "OXOXX.."+
    									 "XXOOO.."+
    									 "OOXOXX.";
    
    
	private NegaMaxPositionEvaluator evaluator;
	private CheckerBoardLoader boardLoader;
	
	@Before
	public void before() {
		evaluator = new NegaMaxPositionEvaluator(
				new InARowPositionEvaluator(4),
				new InARowMoveGeneator());
		boardLoader = new CheckerBoardLoader(7,6);
	}
    
	private double evaluate(Board board) {
		return evaluator.evaluate(board, 9, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 1);
	}

	private void assertEvaluation(Board board, double value) {
		Assert.assertEquals(value, evaluate(board), 0.1);
	}

	private void assertEvaluation(String board, double value) {
		assertEvaluation(boardLoader.loadFromString(board), value);
	}

	@Test
	public void testAssumedDrawFromStart() {
		assertEvaluation(sample, 0.0);
	}

	
}
//@formatter:on
