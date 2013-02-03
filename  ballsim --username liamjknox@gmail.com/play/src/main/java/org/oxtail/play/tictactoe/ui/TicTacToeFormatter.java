package org.oxtail.play.tictactoe.ui;

import org.oxtail.play.Board;
import org.oxtail.play.PieceFunction;
import org.oxtail.play.tictactoe.TicTacToeSquare;
import org.oxtail.play.ui.BoardFormatter;

public class TicTacToeFormatter implements PieceFunction<String>, BoardFormatter {

	@Override
	public String forPiece(byte piece) {
		return TicTacToeSquare.fromInt(piece).toString();
	}

	public String format(Board board) {
		String s = board.toString(this);
		return s;
	}

}
