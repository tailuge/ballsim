package org.java.game;

import static org.java.util.Assert.*;

public class Board {

	private final int x;
	private final int y;

	private final Piece[][] board;

	private Board(int x, int y) {
		assertValidBoardDimenstions(x, y);
		this.x = x;
		this.y = y;
		this.board = new Piece[y][x];
		clearBoard();
	}

	private void clearBoard() {
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				board[i][j] = Piece.NONE;
			}
		}
	}

	private Board(Board copyFrom) {
		assertValidBoardDimenstions(copyFrom.x, copyFrom.y);
		this.x = copyFrom.x;
		this.y = copyFrom.y;
		this.board = new Piece[y][x];
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				this.board[i][j] = copyFrom.board[i][j];
			}
		}
	}

	public static Board newSquareBoard(int size) {
		return new Board(size, size);
	}

	private void assertValidBoardDimenstions(int x, int y) {
		assertGreaterThanZero(x, "invalid width");
		assertGreaterThanZero(y, "invalid height");
	}

	private void assertOnBoard(int piecePlace, int boardRange, Position position) {
		if (piecePlace >= boardRange) {
			throw new IllegalArgumentException(position + " is off the board!");
		}
	}

	private void assertOnBoard(Position position) {
		assertOnBoard(position.getX(), x, position);
		assertOnBoard(position.getY(), y, position);
	}

	public void move(Piece piece, Position position) {
		assertOnBoard(position);
		board[position.getY()][position.getX()] = piece;
	}

	public boolean hasPieceAt(Position position) {
		return getPieceAt(position) != Piece.NONE;
	}

	public Piece getPieceAt(Position position) {
		assertOnBoard(position);
		return board[position.getY()][position.getX()];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Piece[] row : board) {
			for (Piece p : row) {
				sb.append(p.getName());

			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Board copy() {
		return new Board(this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean piecesAreAll(Piece piece, Position... positions) {
		for (Position p : positions) {
			Piece boardPiece = getPieceAt(p);
			if (!boardPiece.equals(piece)) {
				return false;
			}
		}
		return true;
	}

	public boolean isFull() {
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				if (board[i][j] == Piece.NONE) {
					return false;
				}
			}
		}
		return true;
	}

}
