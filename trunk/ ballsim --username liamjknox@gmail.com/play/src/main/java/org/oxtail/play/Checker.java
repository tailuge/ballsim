package org.oxtail.play;

/**
 * Represents a piece in a checker (black/white) based game
 */
public enum Checker {

	WHITE(1, 'O'), BLACK(-1, 'X'), NULL(0, '.');

	private Checker(int intValue, char charValue) {
		this.intValue = intValue;
		this.charValue = charValue;
	}

	private final int intValue;
	private final char charValue;

	public static Checker fromInt(int i) {
		if (i == NULL.intValue)
			return NULL;
		if (i == WHITE.intValue)
			return WHITE;
		if (i == BLACK.intValue)
			return BLACK;
		throw new IllegalArgumentException(i + "?");
	}

	public static Checker fromChar(char i) {
		if (i == NULL.charValue)
			return NULL;
		if (i == WHITE.charValue)
			return WHITE;
		if (i == BLACK.charValue)
			return BLACK;
		throw new IllegalArgumentException(i + "?");
	}

	public static Checker forPlayer(boolean player1InPlayer) {
		return player1InPlayer ? WHITE : BLACK;
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
