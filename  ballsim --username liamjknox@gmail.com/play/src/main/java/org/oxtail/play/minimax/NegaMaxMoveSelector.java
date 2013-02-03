package org.oxtail.play.minimax;

import org.oxtail.play.MoveSelector;
import org.oxtail.play.Board;
import org.oxtail.play.Move;

public class NegaMaxMoveSelector implements MoveSelector {

	private NegaMaxPositionEvaluator positionEvaluator;

	private double alpha = Double.NEGATIVE_INFINITY;
	private double beta = Double.POSITIVE_INFINITY;
	private int depth = 9;

	public NegaMaxMoveSelector(NegaMaxPositionEvaluator positionEvaluator,
			int depth) {
		this.positionEvaluator = positionEvaluator;
		this.depth = depth;
	}

	private double eval1(Board board, int color) {
		return -positionEvaluator.evaluate(board, depth, -beta, -alpha, color);
	}

	@Override
	public Move selectBestContinuation(Board board) {

		double max = Double.NEGATIVE_INFINITY;
		Move bestMove = null;
		int color = board.isPlayer1ToPlay() ? -1 : 1;

		for (Move move : positionEvaluator.getMoveGenerator().possibleMoves(
				board)) {
			Board position = board.doMove(move);
			double v = eval1(position, color);
			if (bestMove == null) {
				bestMove = move;
				continue;
			}
			if (v > max) {
				max = v;
				bestMove = move;
			}
		}
		return bestMove;
	}

}
