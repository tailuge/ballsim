package org.oxtail.play;

/**
 * Very simple 2 player board
 */
public final class Board {

	private final byte[][] board;

	private boolean player1ToPlay = true;

	public Board(int x, int y) {
		this.board = new byte[x][y];
	}

	public Board doMove(Move move) {
		Board newBoard = copy();
		newBoard.player1ToPlay = !player1ToPlay;
		newBoard.board[move.getX()][move.getY()] = move.getPiece();
		return newBoard;
	}

	public boolean isPlayer1ToPlay() {
		return player1ToPlay;
	}

	public boolean isFull() {
		for (int y = 0; y < height(); ++y)
			for (int x = 0; x < width(); ++x)
				if (!hasPiece(x, y))
					return false;
		return true;
	}

	public byte getPiece(int x, int y) {
		return board[x][y];
	}

	public boolean onBoard(int x, int y) {
		return inRange(x, width()) && inRange(y, height());
	}

	private boolean inRange(int coordinate, int range) {
		return coordinate >= 0 && coordinate < range;
	}

	public int width() {
		return board[0].length;
	}

	public int height() {
		return board.length;
	}

	public boolean hasPiece(int x, int y) {
		return getPiece(x, y) != 0;
	}

	@Override
	public String toString() {
		return toString(PLAIN_FORMAT);
	}

	private Board copy() {
		Board copy = new Board(width(), height());
		for (int i = 0; i < width(); ++i) {
			System.arraycopy(board[i], 0, copy.board[i], 0, height());
		}
		copy.player1ToPlay = player1ToPlay;
		return copy;
	}

	public void apply(BoardFunction function) {
		for (int j = 0; j < height(); ++j)
			for (int i = 0; i < width(); ++i)
				function.apply(this, i, j);
	}

	public <T> T forPiece(int x, int y, PieceFunction<T> function) {
		return function.forPiece(getPiece(x, y));
	}

	public boolean samePieceAt(int x, int y, byte piece) {
		return onBoard(x, y) && getPiece(x, y) == piece;
	}

	public String toString(PieceFunction<String> pieceFunction) {
		return new ToStringFunction(this, pieceFunction).toString();
	}

	private static final PieceFunction<String> PLAIN_FORMAT = new PieceFunction<String>() {
		@Override
		public String forPiece(byte piece) {
			return String.valueOf(piece);
		}
	};

	private static final class ToStringFunction implements BoardFunction {

		private final StringBuilder sb = new StringBuilder();
		private int y = 0;
		private final PieceFunction<String> format;

		public ToStringFunction(Board board, PieceFunction<String> format) {
			this.format = format;
			board.apply(this);

		}

		@Override
		public void apply(Board board, int x, int y) {
			if (y > this.y) {
				sb.append("\n");
				this.y = y;
			}
			sb.append(format.forPiece(board.getPiece(x, y)));
		}

		public String toString() {
			return sb.toString();
		}
	}
}
