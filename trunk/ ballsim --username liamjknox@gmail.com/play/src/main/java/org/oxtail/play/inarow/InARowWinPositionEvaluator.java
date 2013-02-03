package org.oxtail.play.inarow;

import org.oxtail.play.Board;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public class InARowWinPositionEvaluator implements PositionEvaluator {

	private int size;

	public InARowWinPositionEvaluator(int size) {
		this.size = size;
	}

	@Override
	public PositionEvaluation evaluate(Board board) {

		boolean player1InPlay = board.isPlayer1ToPlay();
		int match = player1InPlay ? 1 : -1;
		for (int y = 0; y < board.height(); ++y)
			for (int x = 0; x < board.width(); ++x)

				if (winBy(horizontalInARow(board, x, y, match))
						|| winBy(verticalInARow(board, x, y, match))
						|| winBy(forwardDiagonalInARow(board, x, y, match))
						|| winBy(backwardDiagonalInARow(board, x, y, match))) {
					return player1InPlay ? PositionEvaluation.player2Win(board)
							: PositionEvaluation.player1Win(board);
				}

		if (board.isFull()) {
			return PositionEvaluation.draw(board);
		}
		return PositionEvaluation.inPlay(board, 0);
	}

	private boolean winBy(int cnt) {
		return cnt >= size;
	}

	private int horizontalInARow(Board board, int x, int y, int match) {
		return 1 + inARow(board, x, y, match, -1, 0, 0)
				+ inARow(board, x, y, match, 1, 0, 0);
	}

	private int verticalInARow(Board board, int x, int y, int match) {
		return 1 + inARow(board, x, y, match, 0, -1, 0)
				+ inARow(board, x, y, match, 0, 1, 0);
	}

	private int forwardDiagonalInARow(Board board, int x, int y, int match) {
		return 1 + inARow(board, x, y, match, -1, -1, 0)
				+ inARow(board, x, y, match, 1, 1, 0);
	}

	private int backwardDiagonalInARow(Board board, int x, int y, int match) {
		return 1 + inARow(board, x, y, match, -1, 1, 0)
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
