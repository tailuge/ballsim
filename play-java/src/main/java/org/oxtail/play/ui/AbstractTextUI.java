package org.oxtail.play.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.oxtail.play.Board;
import org.oxtail.play.BoardGenerator;
import org.oxtail.play.Move;
import org.oxtail.play.MoveSelector;
import org.oxtail.play.PositionEvaluation;
import org.oxtail.play.PositionEvaluator;

public abstract class AbstractTextUI {

	private AbstractPlayState state;
	private final PositionEvaluator positionEvaluator;
	private final MoveInterpreter moveInterpreter;
	private final MoveSelector moveSelector;
	private final BoardFormatter boardFormatter;
	private final BoardGenerator boardGenerator;

	protected AbstractTextUI(PositionEvaluator positionEvaluator,
			MoveInterpreter moveInterpreter, MoveSelector moveSelector,
			BoardFormatter boardFormatter, BoardGenerator boardGenerator) {
		this.state = new Welcome();
		this.positionEvaluator = positionEvaluator;
		this.moveInterpreter = moveInterpreter;
		this.moveSelector = moveSelector;
		this.boardFormatter = boardFormatter;
		this.boardGenerator = boardGenerator;
	}

	public void run() throws IOException {
		print("hello");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in))) {
			while (true) {
				print(state.prompt());
				String cmd = state.readInput(reader);
				if ("Q".equals(cmd)|| "q".equals(cmd))
					break;
				state = state.execute(cmd);
			}
			print("goodbye");
		}
	}

	private void printBoard(Board board) {
		print(boardFormatter.format(board));
	}

	protected static void print(String message) {
		System.err.println(message);
	}

	private class Welcome extends AbstractPlayState {

		@Override
		public String prompt() {
			return "player 1 or 2?";
		}

		@Override
		public AbstractPlayState execute(String cmd) {
			int i = Integer.parseInt(cmd);
			Board board = boardGenerator.generate();
			printBoard(board);
			if (i == 1) {
				return new HumanToPlay(board, true);
			}
			return new ComputerToPlay(board, true);
		}

		@Override
		protected String readInput(BufferedReader in) throws IOException {
			return in.readLine();
		}

	}

	private abstract class PlayState extends AbstractPlayState {

		private final boolean isPlayerOne;
		private final Board board;

		protected PlayState(Board board, boolean isPlayerOne) {
			this.board = board;
			this.isPlayerOne = isPlayerOne;
		}

		protected final Board doMove(Move move) {
			return board.doMove(move);
		}

		protected final Board getBoard() {
			return board;
		}

		private PositionEvaluation evaluate(Board board) {
			return positionEvaluator.evaluate(board);
		}

		protected final AbstractPlayState checkForGameOver(Board board,
				AbstractPlayState continuation) {
			PositionEvaluation evaluation = evaluate(board);
			if (!evaluation.isGameOver())
				return continuation;
			print(evaluation.getGameState().name());
			return new Welcome();
		}

		protected final boolean otherPlayer() {
			return !isPlayerOne;
		}

		protected final boolean isPlayerOne() {
			return isPlayerOne;
		}

		protected final MoveInterpreter getMoveInterpreter() {
			return moveInterpreter;
		}

		protected final MoveSelector getMoveSelector() {
			return moveSelector;
		}

	}

	protected abstract String getMovePrompt();
	
	private class HumanToPlay extends PlayState {

		private HumanToPlay(Board board, boolean isPlayerOne) {
			super(board, isPlayerOne);
		}

		@Override
		public String prompt() {
			return getMovePrompt();
		}

		@Override
		public String readInput(BufferedReader in) throws IOException {
			return in.readLine();
		}

		@Override
		public AbstractPlayState execute(String cmd) {
			try {
				Move move = getMoveInterpreter().interpretMove(isPlayerOne(),
						cmd,getBoard());
				Board board = doMove(move);
				printBoard(board);
				return checkForGameOver(board, new ComputerToPlay(board,
						otherPlayer()));
			} catch (IllegalMoveException e) {
				print("error: "+e.getMessage());
				return this;
			}

		}

	}

	private class ComputerToPlay extends PlayState {

		private ComputerToPlay(Board board, boolean isPlayerOne) {
			super(board, isPlayerOne);
		}

		@Override
		public String prompt() {
			return "computer thinking...";
		}

		@Override
		protected String readInput(BufferedReader reader) throws IOException {
			return null;
		}

		@Override
		public AbstractPlayState execute(String cmd) {
			long start = System.currentTimeMillis();
			Move move = getMoveSelector().selectBestContinuation(getBoard());
			print(System.currentTimeMillis()-start+"ms");
			if (move == null) {
				throw new AssertionError("Failed to determine move!");
			}
			Board board = doMove(move);
			printBoard(board);
			return checkForGameOver(board,
					new HumanToPlay(board, otherPlayer()));
		}

	}

}
