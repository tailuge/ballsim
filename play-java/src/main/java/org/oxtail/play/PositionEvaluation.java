package org.oxtail.play;

public final class PositionEvaluation {

	private final double score;
	private final GameState gameState;
	private final Board board;

	public PositionEvaluation(double score, GameState gameState, Board board) {
		this.score = score;
		this.gameState = gameState;
		this.board = board;
	}

	public double getScore() {
		return score;
	}

	public boolean isGameOver() {
		return gameState.isGameOver();
	}

	public Board getBoard() {
		return board;
	}

	public static PositionEvaluation player1Win(Board board) {
		return new PositionEvaluation(Double.POSITIVE_INFINITY,
				GameState.PLAYER1_WIN, board);
	}

	public static PositionEvaluation player2Win(Board board) {
		return new PositionEvaluation(Double.NEGATIVE_INFINITY,
				GameState.PLAYER2_WIN, board);
	}

	public static PositionEvaluation draw(Board board) {
		return new PositionEvaluation(0.0, GameState.DRAW, board);
	}

	public static PositionEvaluation inPlay(Board board, double score) {
		return new PositionEvaluation(score, GameState.IN_PLAY, board);
	}

	public GameState getGameState() {
		return gameState;
	}

}
