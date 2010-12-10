package org.java.game.tictactoe;

import java.util.ArrayList;
import java.util.List;

import org.java.game.Game;
import org.java.game.GamePositionGenerator;
import org.java.game.IBoard;
import org.java.game.Piece;
import org.java.game.Position;
import org.java.game.PositionBean;

public class TicTacToePositionGenerator implements GamePositionGenerator {

	@Override
	public Iterable<Game> nextValidPositions(Game game) {
		Piece piece = TicTacToe.NOUGHT;
		if (!game.isPlayerOneInPlayer()) {
			piece = TicTacToe.CROSS;
		}
		List<Game> games = new ArrayList<Game>();
		IBoard board = game.getBoard();
		for (int i = 0; i < board.getX(); i++) {
			for (int j = 0; j < board.getY(); j++) {
				Position position = PositionBean.newPosition(i, j);
				if (!board.hasPieceAt(position)) {
					games.add(game.move(piece, position));
				}
			}
		}
		return games;
	}

}
