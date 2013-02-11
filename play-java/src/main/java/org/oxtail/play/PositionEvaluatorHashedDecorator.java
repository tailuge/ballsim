package org.oxtail.play;

import java.util.concurrent.ConcurrentHashMap;

public final class PositionEvaluatorHashedDecorator implements PositionEvaluator {

	private final ConcurrentHashMap<Board, PositionEvaluation> positionHashMap = new ConcurrentHashMap<>();
	private final PositionEvaluator positionEvaluator;

	public PositionEvaluatorHashedDecorator(PositionEvaluator positionEvaluator) {
		this.positionEvaluator = positionEvaluator;
	}

	@Override
	public PositionEvaluation evaluate(Board board) {
		if (positionHashMap.containsKey(board)) {
			System.err.println("found match");
			return positionHashMap.get(board);
		} else {
			PositionEvaluation evaluation = positionEvaluator.evaluate(board);
			positionHashMap.put(board, evaluation);
			return evaluation;
		}
	}

}
