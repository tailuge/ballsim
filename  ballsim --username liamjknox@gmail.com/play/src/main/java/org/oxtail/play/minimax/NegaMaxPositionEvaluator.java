package org.oxtail.play.minimax;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.MoveGenerator;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public final class NegaMaxPositionEvaluator {

	private final PositionEvaluator positionEvaluator;
	private final MoveGenerator moveGenerator;

	public NegaMaxPositionEvaluator(PositionEvaluator positionEvaluator,
			MoveGenerator moveGenerator) {
		this.positionEvaluator = positionEvaluator;
		this.moveGenerator = moveGenerator;
	}

	public double evaluate(Board board, int depth, double alpha, double beta,
			int color) {
		PositionEvaluation evaluation = positionEvaluator.evaluate(board);
		double value = evaluation.getScore();
		if (depth == 0 || evaluation.isGameOver()) {
			return color * evaluation.getScore();
		}
		for (Move move : moveGenerator.possibleMoves(board)) {
			
			value = -evaluate(board.doMove(move), depth - 1, -beta,
					-alpha, -color);
			if (value >= beta)
				return value;
			if (value >= alpha)
				alpha = value;

		}
		return alpha;
	}

	public PositionEvaluator getPositionEvaluator() {
		return positionEvaluator;
	}

	public MoveGenerator getMoveGenerator() {
		return moveGenerator;
	}

}
