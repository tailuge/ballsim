package org.java.game.tictactoe;

import static org.java.game.tictactoe.TicTacToePositions.BOTTOM_LEFT;
import static org.java.game.tictactoe.TicTacToePositions.BOTTOM_MIDDLE;
import static org.java.game.tictactoe.TicTacToePositions.BOTTOM_RIGHT;
import static org.java.game.tictactoe.TicTacToePositions.MIDDLE_LEFT;
import static org.java.game.tictactoe.TicTacToePositions.MIDDLE_MIDDLE;
import static org.java.game.tictactoe.TicTacToePositions.MIDDLE_RIGHT;
import static org.java.game.tictactoe.TicTacToePositions.TOP_LEFT;
import static org.java.game.tictactoe.TicTacToePositions.TOP_MIDDLE;
import static org.java.game.tictactoe.TicTacToePositions.TOP_RIGHT;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.java.game.BestGameListener;
import org.java.game.Board;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.Piece;
import org.java.game.Player;
import org.java.game.Position;
import org.java.minmax.MinMaxEvaluator;

public class TicTacToe {

	static final Piece NOUGHT = new Piece("0",1);
	static final Piece CROSS = new Piece("X",2);

	public static Game newGame() {
		return newGame(new Player(), new Player());
	}

	private static Piece getPiece(char c) {
		if (c == 'X')
			return CROSS;
		if (c == '0')
			return NOUGHT;
		return Piece.NONE;
	}

	public static Game newGame(String boardString) {
		Board board = Board.newSquareBoard(3);
		char[] c = boardString.toCharArray();
		board.move(getPiece(c[0]), TOP_LEFT);
		board.move(getPiece(c[1]), TOP_MIDDLE);
		board.move(getPiece(c[2]), TOP_RIGHT);
		board.move(getPiece(c[3]), MIDDLE_LEFT);
		board.move(getPiece(c[4]), MIDDLE_MIDDLE);
		board.move(getPiece(c[5]), MIDDLE_RIGHT);
		board.move(getPiece(c[6]), BOTTOM_LEFT);
		board.move(getPiece(c[7]), BOTTOM_MIDDLE);
		board.move(getPiece(c[8]), BOTTOM_RIGHT);
		return Game.newGame(new Player(), new Player(), board);
	}

	public static Game newGame(Player player1, Player player2) {
		Board board = Board.newSquareBoard(3);
		return Game.newGame(player1, player2, board);
	}

	public static void main(String[] args) throws Exception {
		Game game = TicTacToe.newGame();
		GameListener gameListener = new GameListener();
//		AlphaBetaEvaluator evaluator = new AlphaBetaEvaluator(
//				new TicTacToePositionEvaluator(),
//				new TicTacToePositionGenerator(), gameListener);
		MinMaxEvaluator evaluator = new MinMaxEvaluator(
		new TicTacToePositionEvaluator(),
		new TicTacToePositionGenerator(), gameListener);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		int depth = 8;

		while (true) {
			evaluator.minimax(game, depth);
			game = gameListener.getGame();
			System.out.println(game);
			String cmd = reader.readLine().trim();
			if (cmd.toLowerCase().trim().equals("q")) {
				System.exit(1);
			}
			int move = Integer.parseInt(cmd);
			Position position = TicTacToePositions.getPosition(move);
			game = game.move(CROSS, position);
			System.out.println(game);
		}
	}

	private static class GameListener implements BestGameListener {
		private Game game;

		@Override
		public void notifyBestGame(GameEvaluationResult result) {
			if (result.getGame() != null) {
				game = result.getGame();
			}
		}

		public Game getGame() {
			return game;
		}
	}

}
