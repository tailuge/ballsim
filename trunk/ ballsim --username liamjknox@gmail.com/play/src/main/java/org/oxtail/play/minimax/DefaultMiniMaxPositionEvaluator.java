package org.oxtail.play.minimax;

import org.oxtail.play.Board;
import org.oxtail.play.MoveGenerator;

public class DefaultMiniMaxPositionEvaluator implements MiniMaxEvaluator{

	@Override
	public double evaluate(Board board, int depth, double alpha, double beta,
			int color) {
		return 0;
	}

	@Override
	public MoveGenerator getMoveGenerator() {
		return null;
	}

	@Override
	public int getEvalCount() {
		return 0;
	}

	
	
}
