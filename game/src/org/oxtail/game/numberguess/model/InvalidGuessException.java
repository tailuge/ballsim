package org.oxtail.game.numberguess.model;

public class InvalidGuessException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidGuessException(IllegalArgumentException e) {
		super(e.getMessage(),e);
	}
}
