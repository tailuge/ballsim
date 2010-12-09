package org.java.game.inarow;

import static org.java.game.inarow.InARow.RED;
import static org.java.game.inarow.InARow.YELLOW;

import org.java.game.Board;
import org.java.game.Game;
import org.java.game.GamePositionEvaluator;
import org.java.game.GameScore;
import org.java.game.Piece;
import org.java.game.Position;
import org.java.game.PositionBean;


public class InARowPositionEvaluator implements GamePositionEvaluator {

	private int numberInARowRequired = 4;
	
	public InARowPositionEvaluator(int n) {
		numberInARowRequired = n;
	}
	
	@Override
	public GameScore evaluate(Game game) {

		Board board = game.getBoard();
		if (game.isPlayerOneInPlayer()) {
			if (isWinFor(RED, board)) {
				return GameScore.win();
			} else if (isWinFor(YELLOW, board)) {
				return GameScore.loss();
			}
		} else {
			if (isWinFor(YELLOW, board)) {
				return GameScore.win();
			} else if (isWinFor(RED, board)) {
				return GameScore.loss();
			}
		}
		//
		if (board.isFull()) {
			return GameScore.draw();
		}
		return GameScore.inPlay(0.0);
	}

	private boolean isWinFor(Piece piece, Board board) {
		final boolean isWinOnHorizontal = isWinOnHorizontal(piece, board);
		final boolean isWinOnVertical = isWinOnVertical(piece, board);
		final boolean isWinDiagonal = isWinOnDiagonal(piece, board);
		
		return isWinOnHorizontal || isWinOnVertical || isWinDiagonal; 
	}

	// Horizontal check
	
	private boolean isWinOnHorizontal(Piece piece, Board board) {
		for (int i=0;i<board.getY();i++) {
			if (isWinOnHorizontal(i,piece,board)) {
				return true;
			}
		}
		return false;
	}

	private boolean isWinOnHorizontal(int row,Piece piece, Board board) {
		int inARowCount = 0;
		for (int i=0;i<board.getX();++i) {
			if (piece == board.getPieceAt(PositionBean.newPosition(i, row))) {
				if (++inARowCount == numberInARowRequired) {
					return true;
				}
			}
			else {
				inARowCount = 0;
			}
		}
		return false;
	}	
	
	// Vertical Check
	
	private boolean isWinOnVertical(Piece piece, Board board) {
		for (int i=0;i<board.getX();i++) {
			if (isWinOnVertical(i,piece,board)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isWinOnVertical(int column,Piece piece, Board board) {
		int inARowCount = 0;
		for (int i=0;i<board.getY();++i) {
			if (piece == board.getPieceAt(PositionBean.newPosition(column,i))) {
				if (++inARowCount == numberInARowRequired) {
					return true;
				}
			}
			else {
				inARowCount = 0;
			}
		}
		return false;
	}	
	

	// Diagonal Check
	
	
	private boolean isWinOnDiagonal(Piece piece, Board board) {
		int x = board.getX();
		int y = board.getY();
		return isWinTopToBottomBackSlash(board, new InARowCheck(piece, numberInARowRequired), x, y)
		||     isWinTopToRightBackSlash(board, new InARowCheck(piece, numberInARowRequired), x, y)
		||     isWinTopToBottomForwardSlash(board, new InARowCheck(piece, numberInARowRequired), x, y)
		||     isWinTopToLeftForwardSlash(board, new InARowCheck(piece, numberInARowRequired), x, y);
	}


	private boolean isWinTopToBottomBackSlash(Board board, InARowCheck check, int x, int y) {
		for (int yi=0;yi<y;++yi) {
			int yindex = yi;
			int xindex = 0;
			check.reset();
			while (xindex < x && yindex < y) {
				Position p = PositionBean.newPosition(xindex++, yindex++);
				Piece piece = board.getPieceAt(p);
				if (check.found(piece)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isWinTopToRightBackSlash(Board board, InARowCheck check, int x, int y) {
		for (int xi=0;xi<x;++xi) {
			int yindex = 0;
			int xindex = xi;
			check.reset();
			while (xindex < x && yindex < y) {
				Position p = PositionBean.newPosition(xindex++, yindex++);
				Piece piece = board.getPieceAt(p);
				if (check.found(piece)) {
					return true;
				}
			}
		}
		return false;
	}


	private boolean isWinTopToBottomForwardSlash(Board board, InARowCheck check, int x, int y) {
		for (int yi=0;yi<y;++yi) {
			int yindex = yi;
			int xindex = x-1;
			check.reset();
			while (xindex >= 0 && yindex < y) {
				Position p = PositionBean.newPosition(xindex--, yindex++);
				Piece piece = board.getPieceAt(p);
				if (check.found(piece)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isWinTopToLeftForwardSlash(Board board, InARowCheck check, int x, int y) {
		for (int xi=x-1;xi>=0;--xi) {
			int yindex = 0;
			int xindex = xi;
			check.reset();
			while (xindex >= 0 && yindex < y) {
				Position p = PositionBean.newPosition(xindex--, yindex++);
				Piece piece = board.getPieceAt(p);
				if (check.found(piece)) {
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * Inner check
	 */
	private static final class InARowCheck {
		private final Piece pieceWanted;
		private int found;
		private final int expected;
		
		private InARowCheck(Piece pieceWanted, int expected) {
			super();
			this.pieceWanted = pieceWanted;
			this.expected = expected;
		}

		public void reset() {
			found = 0;
		}
		
		public boolean found(Piece piece) {
			if (piece == pieceWanted) {
				++found;
				if (found == expected) {
					return true;
				}
			}
			else {
				found = 0;
			}
			return false;
		}
	}
	
	
}