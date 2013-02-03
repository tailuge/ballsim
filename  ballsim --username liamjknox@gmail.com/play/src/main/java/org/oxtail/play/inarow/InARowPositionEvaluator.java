package org.oxtail.play.inarow;

import org.oxtail.play.Board;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public class InARowPositionEvaluator implements PositionEvaluator {

	private int size;

	public InARowPositionEvaluator(int size) {
		this.size = size;
	}

	@Override
	public PositionEvaluation evaluate(Board board) {
		double player1Score = evaluate(board, 1);
		if (player1Score == Double.POSITIVE_INFINITY) {
			return PositionEvaluation.player1Win(board);
		}
		double player2Score = evaluate(board, -1);
		if (player2Score == Double.POSITIVE_INFINITY) {
			return PositionEvaluation.player2Win(board);
		}
		if (board.isFull())
			return PositionEvaluation.draw(board);
		return PositionEvaluation.inPlay(board, player1Score - player2Score);
	}

	private double evaluate(Board board, int match) {

		double score = 0.0;

		for (int y = 0; y < board.height(); ++y)
			for (int x = 0; x < board.width(); ++x) {

				int piece = board.getPiece(x, y);
				if (piece != match)
					continue;

				int cnt = horizontalInARow(board, x, y, match) + 1;
				score += weight(cnt);

				if (winBy(cnt)) {
					return Double.POSITIVE_INFINITY;
				}

				cnt = verticalInARow(board, x, y, match) + 1;
				score += weight(cnt);

				if (winBy(cnt)) {
					return Double.POSITIVE_INFINITY;
				}

				cnt = forwardDiagonalInARow(board, x, y, match) + 1;

				score += weight(cnt);
				if (winBy(cnt)) {
					return Double.POSITIVE_INFINITY;
				}
				cnt = backwardDiagonalInARow(board, x, y, match) + 1;
				score += weight(cnt);

				if (winBy(cnt)) {
					return Double.POSITIVE_INFINITY;
				}
			}
		return score;
	}

	private double weight(int score) {
		return score ^ 3;
	}

	private boolean winBy(int cnt) {
		return cnt >= size;
	}

	private int horizontalInARow(Board board, int x, int y, int match) {
		return inARow(board, x, y, match, -1, 0, 0)
				+ inARow(board, x, y, match, 1, 0, 0);
	}

	private int verticalInARow(Board board, int x, int y, int match) {
		return inARow(board, x, y, match, 0, -1, 0)
				+ inARow(board, x, y, match, 0, 1, 0);
	}

	private int forwardDiagonalInARow(Board board, int x, int y, int match) {
		return inARow(board, x, y, match, -1, -1, 0)
				+ inARow(board, x, y, match, 1, 1, 0);
	}

	private int backwardDiagonalInARow(Board board, int x, int y, int match) {
		return inARow(board, x, y, match, -1, 1, 0)
				+ inARow(board, x, y, match, 1, -1, 0);
	}

	private int inARow(Board board, int x, int y, int match, int xinc,
			int yinc, int cnt) {
		x += xinc;
		y += yinc;
		return board.samePieceAt(x, y, (byte) match) ? inARow(board, x, y,
				match, xinc, yinc, ++cnt) : cnt;
	}

	public int getSize() {
		return size;
	}

}
