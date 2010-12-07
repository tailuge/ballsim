package org.java.game;

public class GameScore {


	private double score;

	private GameStatus status;

	public GameScore(double score, GameStatus status) {
		this.score = score;
		this.status = status;
	}

	public double getScore() {
		return score;
	}

	public GameStatus getStatus() {
		return status;
	}

	public static final GameScore win() {
		return new GameScore(1.0, GameStatus.Win);
	}
	
	public static final GameScore loss() {
		return new GameScore(-1.0, GameStatus.Loss);
	}
	
	public static final GameScore draw() {
		return new GameScore(0.0, GameStatus.Draw);
	}
	
	public static final GameScore inPlay(double score) {
		return new GameScore(score, GameStatus.Inplay);
	}
	
	public boolean isGameComplete() {
		return status != GameStatus.Inplay;
	}

	public String toString() {
		return "score="+score+",status="+status.name();
	}
}
