package org.oxtail.play;

/**
 * Very simple 2 player board
 */
public final class Board {

	private final byte[][] board;

	private boolean player1ToPlay = true;

	public Board(int x, int y) {
		this.board = new byte[y][x];
	}

	public Board doMove(Move move) {
		Board newBoard = copy();
		newBoard.player1ToPlay = !player1ToPlay;
		newBoard.board[move.getY()][move.getX()] = move.getPiece();
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

	public int getFreeYForX(int x) {
		for (int y = 0; y < height(); ++y)
			if (!hasPiece(x, y)) {
				return y;
			}
		return -1;
	}

	public byte getPiece(int x, int y) {
		return board[y][x];
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

	public int maxMatchCount(Indexes indexes, int match) {
		int cnt = 0;
		int max = 0;
		for (org.oxtail.play.Indexes.Index index : indexes.getIndexes()) {
			byte piece = board[index.getY()][index.getX()];
			if (match == piece) {
				++cnt;
				if (max < cnt)
					max = cnt;
			} else {
				cnt = 0;
			}
		}
		return max;
	}

	private Board copy() {
		Board copy = new Board(width(), height());
		for (int i = 0; i < height(); ++i) {
			System.arraycopy(board[i], 0, copy.board[i], 0, width());
		}
		copy.player1ToPlay = player1ToPlay;
		return copy;
	}

	public void apply(BoardFunction function) {
		for (int y = height() - 1; y >= 0; --y)
			for (int x = 0; x < width(); ++x)
				function.apply(this, x, y);
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
			if (y != this.y) {
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
