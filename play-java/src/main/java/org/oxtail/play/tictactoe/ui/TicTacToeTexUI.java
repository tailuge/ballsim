package org.oxtail.play.tictactoe.ui;

import org.oxtail.play.Board;
import org.oxtail.play.BoardGenerator;
import org.oxtail.play.Move;
import org.oxtail.play.minimax.NegaMaxMoveSelector;
import org.oxtail.play.tictactoe.TicTacToeMoveGenerator;
import org.oxtail.play.tictactoe.TicTacToePositionEvaluator;
import org.oxtail.play.ui.AbstractTextUI;
import org.oxtail.play.ui.CheckerFormatter;
import org.oxtail.play.ui.IllegalMoveException;
import org.oxtail.play.ui.MoveInterpreter;

public class TicTacToeTexUI extends AbstractTextUI {

	private static final MoveInterpreter MOVE_INTERPRETER = new MoveInterpreter() {

		@Override
		public Move interpretMove(boolean isPlayerOne, String input, Board board)
				throws IllegalMoveException {
			try {
				int move = Integer.parseInt(input);
				if (!(move >= 1 && move <= 9))
					throw new IllegalMoveException("move not in range 1..9: "
							+ move);
				--move;
				int y = move / 3;
				int x = move % 3;
				int piece = isPlayerOne ? 1 : -1;
				if (board.hasPiece(x, y)) {
					throw new IllegalMoveException("move already made");
				}
				return new Move(x, y, piece);
			} catch (NumberFormatException e) {
				throw new IllegalMoveException("move not in range 1..9"
						+ e.getMessage());
			}
		}
	};

	private static final BoardGenerator BOARD_GENERATOR = new BoardGenerator() {

		@Override
		public Board generate() {
			return new Board(3, 3);
		}
	};

	private static final TicTacToePositionEvaluator POSITION_EVALUATOR = new TicTacToePositionEvaluator();

	
	
	private TicTacToeTexUI(int depth) {
		super(POSITION_EVALUATOR, MOVE_INTERPRETER, new NegaMaxMoveSelector(
				new org.oxtail.play.minimax.NegaMaxPositionEvaluator(POSITION_EVALUATOR,
						new TicTacToeMoveGenerator()), depth),
				new CheckerFormatter(), BOARD_GENERATOR);
	}

	public static void main(String[] args) throws Exception {
		new TicTacToeTexUI(9).run();
	}

	@Override
	protected String getMovePrompt() {
		return "enter move 1..9?";
	}

	
	
}
