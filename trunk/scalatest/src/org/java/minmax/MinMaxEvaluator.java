package org.java.minmax;

import org.java.game.BestGameListener;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.GamePositionEvaluator;
import org.java.game.GamePositionGenerator;
import org.java.game.GameScore;
import org.java.game.GameStatus;

/**
 * Basic minmax evaluation
 * 
 */
public final class MinMaxEvaluator implements IMinMaxEvaluator {

	private final GamePositionEvaluator positionEvaluator;

	private final GamePositionGenerator positionGenerator;

	private final BestGameListener gameListener;

	public MinMaxEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator) {
		this(positionEvaluator, positionGenerator, BestGameListener.NOP);
	}

	public MinMaxEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator,
			BestGameListener gameListener) {
		this.positionEvaluator = positionEvaluator;
		this.positionGenerator = positionGenerator;
		this.gameListener = gameListener;
	}

	public double minimax(Game game, int depth) {
		GameScore gameScore = positionEvaluator.evaluate(game);
		if (gameScore.isGameComplete() || depth <= 0) {
			return gameScore.getScore();
		}
		Game bestGame = null;
		double bestScore = Double.NEGATIVE_INFINITY;

		if (game.isPlayerOneInPlayer()) {
			for (Game g : positionGenerator.nextValidPositions(game)) {
				double score = minimax(g, depth - 1);
				if (score > bestScore) {
					bestGame = g;
					bestScore = score;
				}
			}
			gameListener.notifyBestGame(new GameEvaluationResult(bestGame,
					new GameScore(bestScore, GameStatus.Inplay), depth));
		} else {
			bestScore = Double.POSITIVE_INFINITY;
			for (Game g : positionGenerator.nextValidPositions(game)) {
				double score = minimax(g, depth - 1);
				if (score < bestScore) {
					bestGame = g;
					bestScore = score;
				}
			}
			gameListener.notifyBestGame(new GameEvaluationResult(bestGame,
					new GameScore(bestScore, GameStatus.Inplay), depth));
		}
		return bestScore;
	}

}