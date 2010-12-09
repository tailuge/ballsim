package org.java.game.inarow;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.java.game.BestGameListener;
import org.java.game.Board;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.Piece;
import org.java.game.Player;
import org.java.game.PositionBean;
import org.java.minmax.MinMaxEvaluator;
import org.java.util.ProcessingThread;

public class InARow {

	public static final Piece RED = new Piece("0");
	public static final Piece YELLOW = new Piece("X");

	private static Piece getPiece(char c) {
		if (c == 'X')
			return YELLOW;
		if (c == '0')
			return RED;
		return Piece.NONE;
	}

	public static Game newGame(String content, int x, int y) {
		Board board = Board.newBoard(x,y);
		int cnt = 0;
		for (int i=0;i<y;i++) {
			for (int j=0;j<x;j++) {
				char c = content.charAt(cnt++);
				board.move(getPiece(c),PositionBean.newPosition(j, i));
			}
		}
		return Game.newGame(new Player(),new Player(), board);
	}

	public static Game newGame(int x, int y) {
		Board board = Board.newBoard(x,y);
		return Game.newGame(new Player(),new Player(), board);
	}

	public static void main(String[] args) throws Exception {
		
		int x = 4;
		
		int y = 4;
		
		int inARowRequired = 3;
		
		Game game = InARow.newGame(x,y);
		GameListener gameListener = new GameListener();
		MinMaxEvaluator evaluator = new MinMaxEvaluator(
				new InARowPositionEvaluator(inARowRequired),
				new InARowPositionGenerator(), gameListener);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		int depth = 20;

		ProcessingThread processingThread = new ProcessingThread();
		processingThread.start();
		
		System.out.println("Welcome to Merkel!, Use numbers to select the column you want to play (0..9)");
		
		System.out.println(game.getBoard());
		System.out.println();
		
		while (true) {
			processingThread.setProcessing(true);
			evaluator.minimax(game, depth);
			processingThread.setProcessing(false);
			
			game = gameListener.getGame();
			System.out.println();
			System.out.println(game);
			checkGameOver(gameListener.getGameEvalutaionResult());
			
			String cmd = reader.readLine().trim();
			if (cmd.toLowerCase().trim().equals("q")) {
				System.exit(1);
			}
			int move = Integer.parseInt(cmd);
			
			int yindex = game.getBoard().getFirstEmptyVerticalPosition(move);
			if (yindex != -1) {
				game = game.move(YELLOW, PositionBean.newPosition(move, yindex));
				checkGameOver(gameListener.getGameEvalutaionResult());
			}
			else {
				System.err.println("invalid move");
			}
			
			System.out.println(game);
			
		}
	}

	public static void checkGameOver(GameEvaluationResult result) {
		if (result.getScore().isWinOrLoss()) {
			System.out.println("Game Over!");
			System.exit(1);
		}
	}
	
	
	private static class GameListener implements BestGameListener {
		private GameEvaluationResult result;

		@Override
		public void notifyBestGame(GameEvaluationResult result) {
			this.result = result;
			
		}

		public Game getGame() {
			return result.getGame();
		}
		
		public GameEvaluationResult getGameEvalutaionResult() {
			return result;
		}
	}


}
