package org.java.minmax;

import org.java.game.BestGameListener;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.GamePositionEvaluator;
import org.java.game.GamePositionGenerator;
import org.java.game.GameScore;

/**
 * Basic minmax evaluation
 * 
 */
public class MinMaxEvaluator {

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
			gameListener.notifyBestGame(new GameEvaluationResult(game, gameScore, depth));
			return gameScore.getScore();
		}
		double bestScore = Double.NEGATIVE_INFINITY;
		Game bestGame = null;
		for (Game g : positionGenerator.nextValidPositions(game)) {
			double score = Math.max(bestScore, (-minimax(g, depth - 1)));
			if (score > bestScore) {
				bestGame = g;
				bestScore = score;
			}
		}
		gameListener.notifyBestGame(new GameEvaluationResult(bestGame, gameScore, depth));
		return bestScore;
	}

}
