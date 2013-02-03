package org.oxtail.play.inarow;

import java.util.ArrayList;
import java.util.List;

import org.oxtail.play.Board;
import org.oxtail.play.Move;
import org.oxtail.play.MoveGenerator;

public final class InARowMoveGeneator implements MoveGenerator {

	@Override
	public Move[] possibleMoves(Board board) {
		final List<Move> moves = new ArrayList<>();
		int piece = board.isPlayer1ToPlay() ? 1 : -1;
		for (int x = 0; x < board.width(); ++x) {
			int y = board.getFreeYForX(x);
			if (y != -1)
				moves.add(new Move(x, y, piece));
		}
		if (moves.isEmpty()) throw new AssertionError("No moves");
		return moves.toArray(new Move[0]);
	}

}
