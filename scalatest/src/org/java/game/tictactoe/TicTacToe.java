package org.java.game.tictactoe;

import static org.java.game.tictactoe.TicTacToePositions.*;
import org.java.game.Board;
import org.java.game.Game;
import org.java.game.Piece;
import org.java.game.Player;

public class TicTacToe {

	static final Piece NOUGHT = new Piece("0");
	static final Piece CROSS = new Piece("X");
	
	public static Game newGame() {
		return newGame(new Player(),new Player());
	}
	
	private static Piece getPiece(char c) {
		if (c == 'X') return CROSS;
		if (c == '0') return NOUGHT;
		return Piece.NONE;
	}
	
	public static Game newGame(String boardString) {
		Board board = Board.newSquareBoard(3);
		char[] c = boardString.toCharArray();
		board.move(getPiece(c[0]),TOP_LEFT);
		board.move(getPiece(c[1]),TOP_MIDDLE);
		board.move(getPiece(c[2]),TOP_RIGHT);
		board.move(getPiece(c[3]),MIDDLE_LEFT);
		board.move(getPiece(c[4]),MIDDLE_MIDDLE);
		board.move(getPiece(c[5]),MIDDLE_RIGHT);
		board.move(getPiece(c[6]),BOTTOM_LEFT);
		board.move(getPiece(c[7]),BOTTOM_MIDDLE);
		board.move(getPiece(c[8]),BOTTOM_RIGHT);
		return Game.newGame(new Player(), new Player(), board);
	}
	
	public static Game newGame(Player player1, Player player2) {
		Board board = Board.newSquareBoard(3);
		return Game.newGame(player1, player2, board);
	}
}
