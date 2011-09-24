package org.oxtail.game.home;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;

import com.google.common.base.Predicate;

public abstract class ForwardingGameHome implements GameHome {

	protected abstract GameHome delegate();

	@Override
	public void storePlayer(Player player) {
		delegate().storePlayer(player);
	}

	@Override
	public Player findPlayer(String playerAlias) {
		return delegate().findPlayer(playerAlias);
	}

	@Override
	public Game<? extends PlayingSpace> findGame(String gameId) {
		return delegate().findGame(gameId);
	}

	@Override
	public void store(Game<? extends PlayingSpace> game) {
		delegate().store(game);
	}

	@Override
	public Iterable<Player> findPlayers(Predicate<Player> playerFilter) {
		return delegate().findPlayers(playerFilter);
	}

	@Override
	public void deleteGame(String gameId) {
		delegate().deleteGame(gameId);
	}

	@Override
	public Iterable<Game<?>> findGames(Predicate<Game<?>> gameFilter) {
		return delegate().findGames(gameFilter);
	}

}