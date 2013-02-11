package org.oxtail.play.tictactoe;

import java.util.ArrayList;
import java.util.List;

import org.oxtail.play.Board;
import org.oxtail.play.BoardFunction;
import org.oxtail.play.Checker;
import org.oxtail.play.Move;
import org.oxtail.play.MoveGenerator;

public final class TicTacToeMoveGenerator implements MoveGenerator {
	
    @Override
	public Move[] possibleMoves(Board board) {
    	Checker square = Checker.forPlayer(board.isPlayer1ToPlay()); 
	    GeneratorFunction function = new GeneratorFunction(square);
		board.apply(function);
		return function.getMoves();
	}

	private static class GeneratorFunction implements BoardFunction {

		private final List<Move> moves = new ArrayList<>();
		private final byte piece;

		public GeneratorFunction(Checker square) {
			this.piece = square.getByteValue();
		}

		@Override
		public void apply(Board board, int x, int y) {
			if (!board.hasPiece(x, y))
				moves.add(new Move(x, y, piece));
		}

		public Move[] getMoves() {
			return moves.toArray(new Move[0]);
		}
	}

}
