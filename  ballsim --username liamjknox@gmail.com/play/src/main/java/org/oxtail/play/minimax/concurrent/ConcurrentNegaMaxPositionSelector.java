package org.oxtail.play.minimax.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.minimax.NegaMaxPositionEvaluator;

public final class ConcurrentNegaMaxPositionSelector extends
		AbstractConcurrentNegaMaxMoveSelector {

	public ConcurrentNegaMaxPositionSelector(
			NegaMaxPositionEvaluator positionEvaluator, int depth) {
		super(positionEvaluator, depth);
	}

	@Override
	public Move selectBestContinuation(Board board) {

		double max = Double.NEGATIVE_INFINITY;
		Move bestMove = null;
		int color = board.isPlayer1ToPlay() ? -1 : 1;
		Move[] moves = getPositionEvaluator().getMoveGenerator().possibleMoves(
				board);
		ExecutorService service = Executors.newFixedThreadPool(moves.length);
		List<Callable<MoveScore>> tasks = new ArrayList<>();
		for (Move move : moves) {
			tasks.add(new EvalCallable(board, move, color));
		}
		try {
			for (Future<MoveScore> f : service.invokeAll(tasks)) {
				MoveScore moveScore = f.get();
				if (bestMove == null) {
					bestMove = moveScore.getMove();
					continue;
				}
				if (moveScore.getScore() > max) {
					max = moveScore.getScore();
					bestMove = moveScore.getMove();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		service.shutdown();
		return bestMove;
	}

	private final class EvalCallable implements Callable<MoveScore> {

		private Board board;
		private Move move;
		private int color;

		private EvalCallable(Board board, Move move, int color) {
			this.board = board;
			this.move = move;
			this.color = color;
		}

		@Override
		public MoveScore call() throws Exception {
			Board position = board.doMove(move);
			return new MoveScore(eval(position, color), move);
		}

	}

}
