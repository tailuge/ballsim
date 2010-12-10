package org.java.game;


public abstract class AbstractBoard implements IBoard {

	protected final int x;
	
	protected final int y;
	
	
	protected abstract void setPieceAt(int x, int y, Piece piece);
	
	protected AbstractBoard(int x, int y) {
		this.x = x;
		this.y = y;
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

	public final void move(Piece piece, Position position) {
		assertOnBoard(position);
		setPieceAt(position.getY(), position.getX(), piece);
	}

	public final boolean hasPieceAt(Position position) {
		return getPieceAt(position) != Piece.NONE;
	}

	public final Piece getPieceAt(Position position) { 
		return getPieceAt(position.getX(),position.getY());
	}

	public abstract Piece getPieceAt(int x, int y);
	
	
	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public final boolean piecesAreAll(Piece piece, Position... positions) {
		for (Position p : positions) {
			Piece boardPiece = getPieceAt(p);
			if (!boardPiece.equals(piece)) {
				return false;
			}
		}
		return true;
	}

	public final boolean isFull() {
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				if (getPieceAt(i, j) == Piece.NONE) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Useful in stack based Games like InARow
	 */
	public final int getFirstEmptyVerticalPosition(int xindex) {
		for (int i=y-1;i>=0;--i) {
			if (getPieceAt(xindex,i) == Piece.NONE) {
				return i;
			}
		}
		return -1;
	}

	
}
