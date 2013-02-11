package org.oxtail.play.minimax;

import org.oxtail.play.Board;
import org.oxtail.play.MoveGenerator;

public interface MiniMaxEvaluator {

	double evaluate(Board board, int depth, double alpha, double beta, int color);
	
	MoveGenerator getMoveGenerator();

	int getEvalCount();
}

