package org.java.minmax;

import org.java.game.Game;
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

	public MinMaxEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator) {
		this.positionEvaluator = positionEvaluator;
		this.positionGenerator = positionGenerator;
	}

	public double minimax(Game game, int depth) {
		GameScore gameScore = positionEvaluator.evaluate(game);
		if (gameScore.isGameComplete() || depth <= 0) {
			return gameScore.getScore();
		}
		double bestScore = Double.NEGATIVE_INFINITY;
		@SuppressWarnings("unused")
		Game bestGame = null; 
		for (Game g : positionGenerator.nextValidPositions(game)) {
			double score = Math.max(bestScore,(-minimax(g, depth-1))); 
			if (score>bestScore) {
				bestGame = g;
				bestScore = score;
			}
		}
		return bestScore;
	}
	

}
