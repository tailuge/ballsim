package org.oxtail.play;

/**
 * Evaluate a board a produce some numerical score of the position
 */
public interface PositionEvaluator {
	
	PositionEvaluation evaluate(Board board);

}
