package org.java.game.inarow;

import java.util.ArrayList;
import java.util.List;

import org.java.game.Game;
import org.java.game.GamePositionGenerator;
import org.java.game.IBoard;
import org.java.game.Piece;
import org.java.game.Position;
import org.java.game.PositionBean;

public final class InARowPositionGenerator implements GamePositionGenerator {

	@Override
	public Iterable<Game> nextValidPositions(Game game) {

		Piece piece = InARow.YELLOW;

		if (game.isPlayerOneInPlayer()) {
			piece = InARow.RED;
		}
		List<Game> games = new ArrayList<Game>();
		IBoard board = game.getBoard();
		int x = board.getX();
		int y = board.getY();

		for (int i = 0; i < x; ++i) {
			Game newGame = getGameForMove(i, y, piece, game);
			if (newGame != null) {
				games.add(newGame);
			}
		}
		return games;
	}

	private Game getGameForMove(int x, int y, Piece piece, Game game) {
		IBoard board = game.getBoard();
		for (int j = y - 1; j >= 0; --j) {
			Position position = PositionBean.newPosition(x, j);
			if (!board.hasPieceAt(position)) {
				return game.move(piece, position);
			}
		}
		return null;
	}
}
