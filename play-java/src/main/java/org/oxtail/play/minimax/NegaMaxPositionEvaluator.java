package org.oxtail.play.minimax;

import java.util.concurrent.atomic.AtomicInteger;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.MoveGenerator;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public final class NegaMaxPositionEvaluator implements MiniMaxEvaluator {

	private final PositionEvaluator positionEvaluator;
	private final MoveGenerator moveGenerator;

	private AtomicInteger evalCount = new AtomicInteger();

	public NegaMaxPositionEvaluator(PositionEvaluator positionEvaluator,
			MoveGenerator moveGenerator) {
		this.positionEvaluator = positionEvaluator;
		this.moveGenerator = moveGenerator;
	}

	@Override
	public double evaluate(Board board, int depth, double alpha, double beta,
			int color) {
		evalCount.set(0);
		return doEvaluate(board, depth, alpha, beta, color);
	}
	
	private double doEvaluate(Board board, int depth, double alpha, double beta,
			int color) {
		PositionEvaluation evaluation = positionEvaluator.evaluate(board);
		double value = evaluation.getScore();
		if (depth == 0 || evaluation.isGameOver()) {
			return color * value;
		}
		for (Move move : moveGenerator.possibleMoves(board)) {
			Board newPosition = board.doMove(move);
			value = -doEvaluate(newPosition, depth - 1, -beta, -alpha, -color);

			if (value >= beta) {
				return value;
			}
			if (value > alpha)
				alpha = value;

		}
		return alpha;
	}

	public PositionEvaluator getPositionEvaluator() {
		return positionEvaluator;
	}

	@Override
	public MoveGenerator getMoveGenerator() {
		return moveGenerator;
	}

	@Override
	public int getEvalCount() {
		return evalCount.get();
	}

}
