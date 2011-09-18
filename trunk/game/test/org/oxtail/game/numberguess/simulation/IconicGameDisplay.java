package org.oxtail.game.numberguess.simulation;

import java.util.Map;

import com.google.common.collect.Maps;

public class IconicGameDisplay implements GameDisplay {

	private Map<String, NumberGuessGameView> views = Maps.newConcurrentMap();

	@Override
	public synchronized void notifyGameStart(String gameDecription,
			String gameId) {
		NumberGuessGameView view = new NumberGuessGameView(gameDecription);
		views.put(gameId, view);
	}

	@Override
	public synchronized void notifyGuess(String player, int guess, String gameId) {
		getView(gameId).notifyGuess(guess, player);
	}

	@Override
	public synchronized void notifyGameOver(String gameId) {

		NumberGuessGameView view = getView(gameId);
		if (view != null) {
			view.dispose();
			views.remove(gameId);
		}
	}

	private NumberGuessGameView getView(String gameId) {
		return views.get(gameId);
	}

}
