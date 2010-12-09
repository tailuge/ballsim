package org.java.game;

public class GameEvaluationResult {

	private final Game game;
	private final GameScore score;
	private final int depth;

	public GameEvaluationResult(Game game, GameScore score, int depth) {
		this.game = game;
		this.score = score;
		this.depth = depth;
	}

	public GameScore getScore() {
		return score;
	}
	
	public Game getGame() {
		return game;
	}

	public String toString() {
		return "depth=" + depth + ",score=" + score + "\n" + game;
	}
}
