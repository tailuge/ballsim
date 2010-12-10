package org.java.game;

public class PieceFactory {

	private final Piece[] pieces;
	
	public PieceFactory(Piece... pieces) {
		this.pieces = pieces;
	}
	
	public Piece getPiece(short pieceId) {
		if(pieceId>pieces.length) {
			throw new IllegalArgumentException("no such piece for id "+pieceId);
		}
		return pieces[pieceId];
	}
	
	
}
