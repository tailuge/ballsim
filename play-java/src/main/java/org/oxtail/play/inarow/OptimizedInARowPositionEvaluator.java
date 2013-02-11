package org.oxtail.play.inarow;

import java.util.List;

import org.oxtail.play.Board;
import org.oxtail.play.Indexes;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public final class OptimizedInARowPositionEvaluator implements
		PositionEvaluator {

	private final List<Indexes> indexes;

	public OptimizedInARowPositionEvaluator() {
		indexes = new IndexBuilder().buildIndexs();
	}

	@Override
	public PositionEvaluation evaluate(Board board) {
		double player1Score = evaluate(board, (byte) 1);
		if (player1Score == Double.POSITIVE_INFINITY) {
			return PositionEvaluation.player1Win(board);
		}
		double player2Score = evaluate(board, (byte) -1);
		if (player2Score == Double.POSITIVE_INFINITY) {
			return PositionEvaluation.player2Win(board);
		}
		if (board.isFull())
			return PositionEvaluation.draw(board);
		return PositionEvaluation.inPlay(board, player1Score - player2Score);
	}

	private double evaluate(Board board, byte match) {
		double score = 0;
		for (Indexes index : indexes) {
			int max = board.maxMatchCount(index, match);
			if (max == 4)
				return Double.POSITIVE_INFINITY;
			score += weight(max);
		}
		return score;
	}

	private double weight(int score) {
		return score ^ 3;
	}

}
