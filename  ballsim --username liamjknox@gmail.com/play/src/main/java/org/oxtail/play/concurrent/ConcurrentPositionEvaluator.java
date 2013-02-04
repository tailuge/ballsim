package org.oxtail.play.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.oxtail.play.Board;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public class ConcurrentPositionEvaluator implements PositionEvaluator {

	private final ExecutorService executorService;
	private final PositionEvaluator evaluator;

	public ConcurrentPositionEvaluator(ExecutorService executorService,
			PositionEvaluator evaluator) {
		this.executorService = executorService;
		this.evaluator = evaluator;
	}


	@Override
	public PositionEvaluation evaluate(final Board board) {
		Callable<PositionEvaluation> task = new Callable<PositionEvaluation>() {

			@Override
			public PositionEvaluation call() throws Exception {
				return evaluator.evaluate(board);
			}

		};
		Future<PositionEvaluation> future = executorService.submit(task);
		try {
			return future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
