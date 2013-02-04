package org.oxtail.play.minimax.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;

public class ForkJoinNegaMaxPositionSelector extends
		AbstractConcurrentNegaMaxMoveSelector {

	public ForkJoinNegaMaxPositionSelector(
			NegaMaxPositionEvaluator positionEvaluator, int depth) {
		super(positionEvaluator, depth);
	}

	@Override
	public Move selectBestContinuation(Board board) {
		try {
			return new ForkJoinPool(7).submit(new EvalMoveTask(board)).get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private class EvalMoveTask extends RecursiveTask<Move> {

		private static final long serialVersionUID = 1L;

		private Board board;

		private EvalMoveTask(Board board) {
			this.board = board;
		}

		@Override
		protected Move compute() {
			int color = board.isPlayer1ToPlay() ? -1 : 1;
			List<RecursiveTask<MoveScore>> forks = new LinkedList<>();
			for (Move move : getPositionEvaluator().getMoveGenerator()
					.possibleMoves(board)) {
				EvalTask task = new EvalTask(move, board, color);
				forks.add(task);
				task.fork();
			}
			double max = Double.NEGATIVE_INFINITY;
			Move bestMove = null;
			for (RecursiveTask<MoveScore> task : forks) {
				MoveScore moveScore = task.join();
				if (bestMove == null) {
					bestMove = moveScore.getMove();
					continue;
				}
				if (moveScore.getScore() > max) {
					max = moveScore.getScore();
					bestMove = moveScore.getMove();
				}
			}
			return bestMove;
		}

	}

	private class EvalTask extends RecursiveTask<MoveScore> {

		private static final long serialVersionUID = 1L;

		private Move move;
		private Board board;
		private int color;

		private EvalTask(Move move, Board board, int color) {
			this.move = move;
			this.board = board;
			this.color = color;
		}

		@Override
		protected MoveScore compute() {
			Board position = board.doMove(move);
			return new MoveScore(eval(position, color), move);
		}

	}

}
