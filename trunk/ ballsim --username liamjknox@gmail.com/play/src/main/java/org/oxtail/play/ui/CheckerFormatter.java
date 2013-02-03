package org.oxtail.play.ui;

import org.oxtail.play.Board;
import org.oxtail.play.Checker;
import org.oxtail.play.PieceFunction;

public class CheckerFormatter implements PieceFunction<String>, BoardFormatter {

	@Override
	public String forPiece(byte piece) {
		return Checker.fromInt(piece).toString();
	}

	public String format(Board board) {
		String s = board.toString(this);
		return s;
	}

}
