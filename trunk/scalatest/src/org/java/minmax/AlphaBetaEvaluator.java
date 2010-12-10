package org.java.minmax;

import org.java.game.BestGameListener;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.GamePositionEvaluator;
import org.java.game.GamePositionGenerator;
import org.java.game.GameScore;

public class AlphaBetaEvaluator {

	private final GamePositionEvaluator positionEvaluator;

	private final GamePositionGenerator positionGenerator;

	private final BestGameListener gameListener;

	public AlphaBetaEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator) {
		this(positionEvaluator, positionGenerator, BestGameListener.NOP);
	}

	public AlphaBetaEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator,
			BestGameListener gameListener) {
		this.positionEvaluator = positionEvaluator;
		this.positionGenerator = positionGenerator;
		this.gameListener = gameListener;
	}

	public double alphaBeta(Game game, int depth, double alpha, double beta,
			boolean isPlayerOne) {
		GameScore gameScore = positionEvaluator.evaluate(game);
		if (gameScore.isGameComplete() || depth <= 0) {
			gameListener.notifyBestGame(new GameEvaluationResult(game,
					gameScore, depth));
			return gameScore.getScore();
		}
		Game bestGame = null;

		if (isPlayerOne) {
			double bestScore = alpha;
			for (Game g : positionGenerator.nextValidPositions(game)) {
				double score = alphaBeta(g, depth - 1, bestScore, beta,
						!isPlayerOne);
				if (score > bestScore) {
					bestScore = score;
					bestGame = g;
				}
				if (bestScore > beta) {
					bestScore = beta;
					break;
				}
			}
			gameListener.notifyBestGame(new GameEvaluationResult(bestGame,
					gameScore, depth));
			return bestScore;
		} else {
			double bestScore = beta;
			for (Game g : positionGenerator.nextValidPositions(game)) {
				double score = alphaBeta(g, depth - 1, alpha, bestScore,
						!isPlayerOne);
				if (score < bestScore) {
					bestScore = score;
					bestGame = g;
				}
				if (bestScore < alpha) {
					bestScore = alpha;
					break;
				}
			}
			gameListener.notifyBestGame(new GameEvaluationResult(bestGame,
					gameScore, depth));
			return bestScore;
		}
	}

}
