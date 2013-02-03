package org.oxtail.play;

public final class Move {

	private final int x;
	private final int y;
	private final byte piece;

	public Move(int x, int y, int piece) {
		this.x = x;
		this.y = y;
		this.piece = (byte)piece;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public byte getPiece() {
		return piece;
	}

}
