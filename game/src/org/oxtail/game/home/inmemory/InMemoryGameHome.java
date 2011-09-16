package org.oxtail.game.home.inmemory;

import java.util.Map;

import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.GameNotFoundException;
import org.oxtail.game.home.PlayerNotFoundException;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

public class InMemoryGameHome implements GameHome {

	private Map<String, Player> players = Maps.newHashMap();
	private Map<String, Game<?>> games = Maps.newHashMap();

	@Override
	public void storePlayer(Player player) {
		players.put(player.getAlias(), player);
	}

	@Override
	public Player findPlayer(String playerAlias) {
		if (players.containsKey(playerAlias))
			return players.get(playerAlias);
		throw new PlayerNotFoundException();
	}

	@Override
	public Game<? extends PlayingSpace> findGame(String gameId) {
		if (games.containsKey(gameId))
			return games.get(gameId);
		throw new GameNotFoundException();
	}

	@Override
	public void store(Game<? extends PlayingSpace> game) {
		games.put(game.getId(), game);
	}

	@Override
	public Iterable<Player> findPlayers(Predicate<Player> playerFilter) {
		return Iterables.filter(players.values(), playerFilter);
	}

	@Override
	public void deleteGame(String id) {
		games.remove(id);
	}

}
