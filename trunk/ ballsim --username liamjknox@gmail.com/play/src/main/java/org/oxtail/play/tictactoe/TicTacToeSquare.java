package org.oxtail.play.tictactoe;

public enum TicTacToeSquare {

	NOUGHT(1, 'O'), CROSS(-1, 'X'), NULL(0, '.');

	private TicTacToeSquare(int intValue, char charValue) {
		this.intValue = intValue;
		this.charValue = charValue;
	}

	private final int intValue;
	private final char charValue;

	public static TicTacToeSquare fromInt(int i) {
		if (i == NULL.intValue)
			return NULL;
		if (i == NOUGHT.intValue)
			return NOUGHT;
		if (i == CROSS.intValue)
			return CROSS;
		throw new IllegalArgumentException(i + "?");
	}

	public static TicTacToeSquare fromChar(char i) {
		if (i == NULL.charValue)
			return NULL;
		if (i == NOUGHT.charValue)
			return NOUGHT;
		if (i == CROSS.charValue)
			return CROSS;
		throw new IllegalArgumentException(i + "?");
	}

	public static TicTacToeSquare forPlayer(boolean player1InPlayer) {
		return player1InPlayer ? NOUGHT : CROSS;
	}

	public int getIntValue() {
		return intValue;
	}

	public byte getByteValue() {
		return (byte) intValue;
	}

	public char getCharValue() {
		return charValue;
	}

	@Override
	public String toString() {
		return String.valueOf(getCharValue());
	}
}
