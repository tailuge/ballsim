package org.java.minmax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.java.game.BestGameListener;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.GamePositionEvaluator;
import org.java.game.GamePositionGenerator;
import org.java.game.GameScore;

public final class ConcurrentMinMaxEvaluator implements IMinMaxEvaluator {

	private final GamePositionEvaluator positionEvaluator;

	private final GamePositionGenerator positionGenerator;

	private final BestGameListener gameListener;

	private final ExecutorService executor;

	private boolean firstCall = true;
	
	public ConcurrentMinMaxEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator, ExecutorService executor) {
		this(positionEvaluator, positionGenerator, BestGameListener.NOP,
				executor);
	}

	public ConcurrentMinMaxEvaluator(GamePositionEvaluator positionEvaluator,
			GamePositionGenerator positionGenerator,
			BestGameListener gameListener, ExecutorService executor) {
		this.positionEvaluator = positionEvaluator;
		this.positionGenerator = positionGenerator;
		this.gameListener = gameListener;
		this.executor = executor;
	}

	public double minimax(Game game, int depth) {

		GameScore gameScore = positionEvaluator.evaluate(game);

		if (gameScore.isGameComplete() || depth <= 0) {
			gameListener.notifyBestGame(new GameEvaluationResult(game,
					gameScore, depth));
			return gameScore.getScore();
		}
		if (firstCall) {
			firstCall = false;
			return parallelMinMax(game, depth, gameScore);
		} else {
			return serialMinMax(game, depth, gameScore);
		}
	}

	private double serialMinMax(Game game, int depth, GameScore gameScore) {
		double bestScore = Double.NEGATIVE_INFINITY;
		Game bestGame = null;
		for (Game g : positionGenerator.nextValidPositions(game)) {
			double score = -minimax(g, depth - 1);
			if (score > bestScore) {
				bestGame = g;
				bestScore = score;
			}
		}
		gameListener.notifyBestGame(new GameEvaluationResult(bestGame,
				gameScore, depth));
		return bestScore;

	}

	private double parallelMinMax(Game game, int depth, GameScore gameScore) {
		double bestScore = Double.NEGATIVE_INFINITY;
		Game bestGame = null;
		List<Future<GS>> futures = new ArrayList<Future<GS>>();

		for (Game g : positionGenerator.nextValidPositions(game)) {
			futures.add(executor.submit(new GameEvaluate(g, depth - 1)));
		}
		for (Future<GS> future : futures) {
			try {
				GS gs = future.get();
				if (gs.score > bestScore) {
					bestGame = gs.g;
					bestScore = gs.score;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		gameListener.notifyBestGame(new GameEvaluationResult(bestGame,
				gameScore, depth));
		return bestScore;
	}

	public class GameEvaluate implements Callable<GS> {

		private Game g;
		private int depth;

		private GameEvaluate(Game g, int depth) {
			this.g = g;
			this.depth = depth;
		}

		@Override
		public GS call() throws Exception {
			double score = -minimax(g, depth - 1);
			return new GS(g, score);
		}

	}

	private static class GS {
		private Game g;
		private double score;

		private GS(Game g, double score) {
			this.g = g;
			this.score = score;
		}
	}
}
