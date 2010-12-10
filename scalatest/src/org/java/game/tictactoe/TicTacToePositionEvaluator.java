package org.java.game.tictactoe;

import static org.java.game.tictactoe.TicTacToePositions.BottomRow;
import static org.java.game.tictactoe.TicTacToePositions.DiagonalOne;
import static org.java.game.tictactoe.TicTacToePositions.DiagonalTwo;
import static org.java.game.tictactoe.TicTacToePositions.LeftColumn;
import static org.java.game.tictactoe.TicTacToePositions.MiddleColumn;
import static org.java.game.tictactoe.TicTacToePositions.MiddleRow;
import static org.java.game.tictactoe.TicTacToePositions.RightColumn;
import static org.java.game.tictactoe.TicTacToePositions.TopRow;

import org.java.game.Game;
import org.java.game.GamePositionEvaluator;
import org.java.game.GameScore;
import org.java.game.IBoard;
import org.java.game.Piece;

public class TicTacToePositionEvaluator implements GamePositionEvaluator {

	@Override
	public GameScore evaluate(Game game) {

		IBoard board = game.getBoard();

		if (isWinFor(TicTacToe.NOUGHT, board)) {
			return GameScore.win();
		} else if (isWinFor(TicTacToe.CROSS, board)) {
			return GameScore.loss();
		}
		//
		if (board.isFull()) {
			return GameScore.draw();
		}
		return GameScore.inPlay(0.0);
	}

	private boolean isWinFor(Piece piece, IBoard board) {
		return isWinOnColumns(piece, board) || isWinOnDiagonals(piece, board)
				|| isWinOnRows(piece, board);
	}

	private boolean isWinOnColumns(Piece piece, IBoard board) {
		return board.piecesAreAll(piece, LeftColumn())
				|| board.piecesAreAll(piece, RightColumn())
				|| board.piecesAreAll(piece, MiddleColumn());
	}

	private boolean isWinOnRows(Piece piece, IBoard board) {
		return board.piecesAreAll(piece, TopRow())
				|| board.piecesAreAll(piece, BottomRow())
				|| board.piecesAreAll(piece, MiddleRow());
	}

	private boolean isWinOnDiagonals(Piece piece, IBoard board) {
		return board.piecesAreAll(piece, DiagonalOne())
				|| board.piecesAreAll(piece, DiagonalTwo());
	}

}
