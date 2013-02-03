package org.oxtail.play.tictactoe;

import org.oxtail.play.Board;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public class TicTacToePositionEvaluator implements PositionEvaluator {

	@Override
	public PositionEvaluation evaluate(Board board) {

		double player1Score = evaluate(board, (byte) 1);
		double player2Score = evaluate(board, (byte) -1);

		if (player1Score == Double.POSITIVE_INFINITY)
			return PositionEvaluation.player1Win(board);
		if (player2Score == Double.NEGATIVE_INFINITY)
			return PositionEvaluation.player2Win(board);
		if (gameOverByDraw(board))
			return PositionEvaluation.draw(board);
		return PositionEvaluation.inPlay(board, 0.0);
	}

	private boolean gameOverByDraw(Board board) {
			for (int y = 0; y < board.height(); ++y)
				for (int x = 0; x < board.width(); ++x)
					if (!board.hasPiece(x, y))
						return false;
			return true;
	}

	private double evaluate(Board board, byte piece) {
		if (evaluateX(board, piece) || evaluateY(board, piece)
				|| evaluateZ(board, piece)) {
			return piece == -1 ? Double.NEGATIVE_INFINITY
					: Double.POSITIVE_INFINITY;
		}
		return 0.0;
	}

	private boolean evaluateX(Board board, byte piece) {
		return (board.samePieceAt(0, 0, piece)
				&& board.samePieceAt(1, 0, piece) && board.samePieceAt(2, 0,
				piece))
				|| (board.samePieceAt(0, 1, piece)
						&& board.samePieceAt(1, 1, piece) && board.samePieceAt(
						2, 1, piece))
				|| (board.samePieceAt(0, 2, piece)
						&& board.samePieceAt(1, 2, piece) && board.samePieceAt(
						2, 2, piece));
	}

	private boolean evaluateY(Board board, byte piece) {
		return (board.samePieceAt(0, 0, piece)
				&& board.samePieceAt(0, 1, piece) && board.samePieceAt(0, 2,
				piece))
				|| (board.samePieceAt(1, 0, piece)
						&& board.samePieceAt(1, 1, piece) && board.samePieceAt(
						1, 2, piece))
				|| (board.samePieceAt(2, 0, piece)
						&& board.samePieceAt(2, 1, piece) && board.samePieceAt(
						2, 2, piece));
	}

	private boolean evaluateZ(Board board, byte piece) {
		return (board.samePieceAt(0, 0, piece)
				&& board.samePieceAt(1, 1, piece) && board.samePieceAt(2, 2,
				piece))
				|| (board.samePieceAt(0, 2, piece)
						&& board.samePieceAt(1, 1, piece) && board.samePieceAt(
						2, 0, piece));
	}

}
