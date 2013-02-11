package org.oxtail.play.inarow.ui;

import org.oxtail.play.Board;
import org.oxtail.play.BoardGenerator;
import org.oxtail.play.Move;
import org.oxtail.play.MoveSelector;
import org.oxtail.play.PositionEvaluator;
import org.oxtail.play.PositionEvaluatorHashedDecorator;
import org.oxtail.play.inarow.InARowMoveGeneator;
import org.oxtail.play.inarow.InARowPositionEvaluator;
import org.oxtail.play.minimax.MiniMaxEvaluator;
import org.oxtail.play.minimax.NegaMaxMoveSelector;
import org.oxtail.play.ui.AbstractTextUI;
import org.oxtail.play.ui.CheckerFormatter;
import org.oxtail.play.ui.IllegalMoveException;
import org.oxtail.play.ui.MoveInterpreter;

public class InARowTextUI extends AbstractTextUI {

	private static final int WIDTH = 7;
	private static final int HEIGHT = 6;

	private static final MoveInterpreter MOVE_INTERPRETER = new MoveInterpreter() {

		@Override
		public Move interpretMove(boolean isPlayerOne, String input, Board board)
				throws IllegalMoveException {
			try {
				int move = Integer.parseInt(input);
				if (!(move >= 1 && move <= WIDTH))
					throw new IllegalMoveException("move not in range 1.."
							+ WIDTH + ": " + move);
				int x = move - 1;
				int piece = isPlayerOne ? 1 : -1;
				int y = board.getFreeYForX(x);
				if (y == -1) {
					throw new IllegalMoveException("column full");
				}
				return new Move(x, y, piece);
			} catch (NumberFormatException e) {
				throw new IllegalMoveException("move not in range 1.." + WIDTH
						+ ": " + e.getMessage());
			}
		}
	};

	private static final BoardGenerator BOARD_GENERATOR = new BoardGenerator() {

		@Override
		public Board generate() {
			return new Board(WIDTH, HEIGHT);
		}
	};

	private static final PositionEvaluator POSITION_EVALUATOR = new PositionEvaluatorHashedDecorator(new InARowPositionEvaluator(4));

	private InARowTextUI(PositionEvaluator positionEvaluator, MoveSelector moveSelector) {
		super(POSITION_EVALUATOR, MOVE_INTERPRETER, moveSelector, new CheckerFormatter(),
				BOARD_GENERATOR);
	}

	public static void main(String[] args) throws Exception {
		MiniMaxEvaluator negaMaxPositionEvaluator = new org.oxtail.play.minimax.NegaMaxPositionEvaluator(POSITION_EVALUATOR, new InARowMoveGeneator());
		MoveSelector selector = new NegaMaxMoveSelector(negaMaxPositionEvaluator, 4);
		new InARowTextUI(POSITION_EVALUATOR,selector).run();
	}

	@Override
	protected String getMovePrompt() {
		return "enter move 1.."+WIDTH+"?";
	}

}