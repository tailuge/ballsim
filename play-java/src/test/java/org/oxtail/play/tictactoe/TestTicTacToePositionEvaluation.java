package org.oxtail.play.tictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oxtail.play.Board;
import org.oxtail.play.minimax.NegaMaxMoveSelector;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;
import org.oxtail.play.ui.CheckerFormatter;

//@formatter:off
public class TestTicTacToePositionEvaluation {

	private NegaMaxPositionEvaluator evaluator;
	private TicTacToeBoardLoader boardLoader;
	private NegaMaxMoveSelector positionSelector;
	private CheckerFormatter formatter = new CheckerFormatter();
	
	@Before
	public void before() {
		evaluator = new NegaMaxPositionEvaluator(
				new TicTacToePositionEvaluator(),
				new TicTacToeMoveGenerator());
		boardLoader = new TicTacToeBoardLoader();
		positionSelector = new NegaMaxMoveSelector(evaluator,9);
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
		assertEvaluation("..."+ 
	                     "..."+ 
				         "...", 0.0);
	}

	@Test
	public void testForceDrawFromMove2() {
		assertEvaluation("X.."+ 
	                     ".O."+ 
				         "...", 0.0);
	}

	
	@Test
	public void testForceWinForNought() {
		assertEvaluation(".X."+ 
	                     ".O."+ 
				         "...", Double.POSITIVE_INFINITY);
	}

	@Test
	public void testForceWinForNought2() {
		assertEvaluation("..."+ 
	                     ".O."+ 
				         ".X.", Double.POSITIVE_INFINITY);
	}

	@Test
	public void testForceWinForNought3() {
		assertEvaluation("OX."+ 
	                     "..."+ 
				         "...", Double.POSITIVE_INFINITY);
	}

	
	@Test
	public void testForceWinForCross() {
		assertEvaluation("..X"+ 
	                     "OXO"+ 
				         "...", Double.NEGATIVE_INFINITY);
	}


	private void showSelection(Board board) {
		System.out.println(formatter.format(board));
		Board bestBoard = board.doMove(positionSelector.selectBestContinuation(board));
		System.out.println(formatter.format(bestBoard));
	}
	
	@Test
	public void testPositionSelection() {
		Board board = boardLoader.loadFromString("..."+ 
	                                             ".O."+ 
				                                 ".X.");
		showSelection(board);
	}


	@Test
	public void testPositionSelection2() {
		Board board = boardLoader.loadFromString("..."+ 
	                                             "..."+ 
				                                 "...");
		showSelection(board);
	}

	@Test
	public void testPositionSelection3() {
		Board board = boardLoader.loadFromString("..."+ 
	                                             ".O."+ 
				                                 "...");
		showSelection(board);
	}

	@Test
	public void testPositionSelection4() {
		Board board = boardLoader.loadFromString("O.."+ 
	                                             "..."+ 
				                                 "...");
		showSelection(board);
	}

	
}
//@formatter:on
