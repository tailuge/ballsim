package org.oxtail.play.minimax.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.MoveGenerator;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;
import org.oxtail.play.minimax.MiniMaxEvaluator;

public final class NegaMaxPositionEvaluator implements MiniMaxEvaluator {

	private final PositionEvaluator positionEvaluator;
	private final MoveGenerator moveGenerator;
	private final ExecutorService executorService = Executors
			.newFixedThreadPool(8);

	private org.oxtail.play.minimax.NegaMaxPositionEvaluator linearEvaluator;

	private AtomicInteger evalCount = new AtomicInteger();

	public NegaMaxPositionEvaluator(PositionEvaluator positionEvaluator,
			MoveGenerator moveGenerator) {
		this.positionEvaluator = positionEvaluator;
		this.moveGenerator = moveGenerator;
		linearEvaluator = new org.oxtail.play.minimax.NegaMaxPositionEvaluator(
				positionEvaluator, moveGenerator);
	}

	@Override
	public double evaluate(Board board, int depth, double alpha, double beta,
			int color) {
		evalCount.incrementAndGet();
		PositionEvaluation evaluation = positionEvaluator.evaluate(board);
		double value = evaluation.getScore();
		if (depth == 0 || evaluation.isGameOver()) {
			return color * value;
		}
		Move[] moves = moveGenerator.possibleMoves(board);
		alpha = -evaluate(board.doMove(moves[0]), depth - 1, -beta, -alpha,
				-color);

		EvalTask task = new EvalTask(alpha, beta, depth, color, board, moves);
		try {
			return executorService.submit(task).get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PositionEvaluator getPositionEvaluator() {
		return positionEvaluator;
	}

	public MoveGenerator getMoveGenerator() {
		return moveGenerator;
	}

	public int getEvalCount() {
		try {
			return evalCount.get() + linearEvaluator.getEvalCount();
		} finally {
			evalCount.set(0);
		}
	}

	private class EvalTask implements Callable<Double> {

		private double alpha;
		private double beta;
		private int depth;
		private int color;
		private Board board;
		private Move[] moves;

		private EvalTask(double alpha, double beta, int depth, int color,
				Board board, Move[] moves) {
			super();
			this.alpha = alpha;
			this.beta = beta;
			this.depth = depth;
			this.color = color;
			this.board = board;
			this.moves = moves;
		}

		@Override
		public Double call() throws Exception {
			System.out.println(Thread.currentThread().getName() + " task");
			try {
				double value = alpha;
				for (int i = 0; i < moves.length; i++) {
					Board newPosition = board.doMove(moves[i]);
					value = -linearEvaluator.evaluate(newPosition, depth - 1,
							-beta, -alpha, -color);
					if (value >= beta) {
						return value;
					}
					if (value >= alpha)
						alpha = value;

				}
				return alpha;
			} finally {
				System.out.println(Thread.currentThread().getName() + " end task");
			}
		}
	}

}
