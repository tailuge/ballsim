package org.java.game.chess;

import java.util.ArrayList;
import java.util.List;

import org.java.game.Position;

public class Rook extends ChessPiece {

	public Rook() {
		super("Rook");
	}

	/**
	 * Adds in square we can move to. If we hit our piece we stop. If we hit
	 * opponent this is a possible take so we add this position and stop If we
	 * hit a free square we continue
	 */
	private boolean addedAll(Position newPosition, List<Position> positions) {
		if (board.hasPieceAt(newPosition)) {
			ChessPiece chessPiece = getPieceAt(newPosition);
			// based on collision with our own side
			if (isSameSide(chessPiece)) {
				return true;
			}
			// based on a take
			positions.add(newPosition);
			return true;
		}
		return false;
	}

	@Override
	public Iterable<Position> getPossibleMoves() {
		List<Position> positions = new ArrayList<Position>();
		Position position = getPosition();

		int x = position.getX();
		int y = position.getX();

		int i = position.getX();

		// cover X moves
		while (i++ < BOARD_SIZE) {
			Position newPosition = ChessPositions.get(i, y);
			if (addedAll(newPosition, positions)) {
				break;
			}
		}

		while (i-- >= 0) {
			Position newPosition = ChessPositions.get(i, y);
			if (addedAll(newPosition, positions)) {
				break;
			}
		}

		int j = position.getY();

		// cover Y moves
		while (j++ < BOARD_SIZE) {
			Position newPosition = ChessPositions.get(x, j);
			if (addedAll(newPosition, positions)) {
				break;
			}
		}

		while (j-- >= 0) {
			Position newPosition = ChessPositions.get(x, j);
			if (addedAll(newPosition, positions)) {
				break;
			}
		}

		return positions;
	}

}
