package org.java.game;

import static org.java.util.Assert.assertGreaterThanOrEqualToZero;

public final class FastBoard implements IBoard {

	private static PieceFactory pieceFactory;
	
	private final int x;
	private final int y;

	private final short[][] board;

	private FastBoard(int x, int y) {
		assertValidBoardDimenstions(x, y);
		this.x = x;
		this.y = y;
		this.board = new short[y][x];
	}
	
	private FastBoard(FastBoard copyFrom) {
		this(copyFrom.x,copyFrom.y);
		for (int i = 0; i < y; ++i) {
			for (int j = 0; j < x; ++j) {
				this.board[i][j] = copyFrom.board[i][j];
			}
		}
	}

	public static IBoard newSquareBoard(int size) {
		return new FastBoard(size, size);
	}

	public static IBoard newBoard(int x, int y) {
		return new FastBoard(x, y);
	}

	private void assertValidX(int i) {
		assertGreaterThanOrEqualToZero(i, "invalid x position");
	}
	
	private void assertValidY(int i) {
		assertGreaterThanOrEqualToZero(i, "invalid y position");
	}
	
	private void assertValidBoardDimenstions(int x, int y) {
		assertValidX(x);
		assertValidY(y);
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
		board[position.getY()][position.getX()] = piece.getId();
	}

	public boolean hasPieceAt(Position position) {
		return board[position.getY()][position.getX()] != 0;
	}

	public Piece getPieceAt(Position position) {
		assertOnBoard(position);
		return pieceFactory.getPiece(board[position.getY()][position.getX()]);
	}

	public Piece getPieceAt(int x, int y) {
		assertValidBoardDimenstions(x,y);			
		return pieceFactory.getPiece(board[x][y]);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (short[] row : board) {
			for (short p : row) {
				sb.append(pieceFactory.getPiece(p));

			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public IBoard copy() {
		return new FastBoard(this);
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
				if (board[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Useful in stack based Games like InARow
	 */
	public int getFirstEmptyVerticalPosition(int xindex) {
		for (int i=y-1;i>=0;--i) {
			if (board[i][xindex] == 0) {
				return i;
			}
		}
		return -1;
	}

	public static void setPieceFactory(PieceFactory p) {
		pieceFactory = p;
	}
	
	
}
