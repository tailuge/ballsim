package org.oxtail.play.minimax.concurrent;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.MoveSelector;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;

public abstract class AbstractConcurrentNegaMaxMoveSelector implements
		MoveSelector {

	private NegaMaxPositionEvaluator positionEvaluator;

	private double alpha = Double.NEGATIVE_INFINITY;
	private double beta = Double.POSITIVE_INFINITY;
	private int depth;

	protected AbstractConcurrentNegaMaxMoveSelector(
			NegaMaxPositionEvaluator positionEvaluator, int depth) {
		this.positionEvaluator = positionEvaluator;
		this.depth = depth;
	}

	@Override
	public abstract Move selectBestContinuation(Board board);

	protected final NegaMaxPositionEvaluator getPositionEvaluator() {
		return positionEvaluator;
	}

	protected final double eval(Board position, int color) {
		return -positionEvaluator.evaluate(position, depth, -beta, -alpha,
				color);
	}

	protected final static class MoveScore {
		private final double score;
		private final Move move;

		protected MoveScore(double score, Move move) {
			this.score = score;
			this.move = move;
		}

		public double getScore() {
			return score;
		}

		public Move getMove() {
			return move;
		}

	}

}
