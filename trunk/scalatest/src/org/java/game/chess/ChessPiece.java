package org.java.game.chess;

import org.java.game.Board;
import org.java.game.Piece;
import org.java.game.Position;
import org.java.game.PositionAwarePiece;

public abstract class ChessPiece extends Piece implements PositionAwarePiece {

	public static final ChessPiece NONE = new ChessPiece("NONE") {
		@Override
		public Iterable<Position> getPossibleMoves() {
			throw new UnsupportedOperationException();
		}
	};
	
	private boolean isWhite;
	
	protected static final int BOARD_SIZE = 8;
	
	private Position position;

	private double value;
	
	protected Board board;
	
	protected ChessPiece(String name) {
		super(name);
	}
	
	public abstract Iterable<Position> getPossibleMoves();

	@Override
	public void setPostion(Position postion) {
		this.position = postion;
	}
	
	protected Position getPosition() {
		return position;
	}
	
	protected ChessPiece getPieceAt(Position position) {
		return (ChessPiece)board.getPieceAt(position);
	}
	
	public boolean isSameSide(ChessPiece piece) {
		return piece != NONE && this != NONE && piece.isWhite == isWhite;
	}

	public double getValue() {
		return value;
	}
}
