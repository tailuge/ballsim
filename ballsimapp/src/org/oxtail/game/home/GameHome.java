package org.oxtail.game.home;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;

import com.google.common.base.Predicate;

/**
 * Persistence access for Game and related entities
 * 
 * @author liam knox
 */
public interface GameHome {

	void storePlayer(Player player);

	Player findPlayer(String playerAlias);

	Game<? extends PlayingSpace> findGame(String gameId);

	void store(Game<? extends PlayingSpace> game);

	Iterable<Player> findPlayers(Predicate<Player> playerFilter);

	void deleteGame(String gameId);

	Iterable<Game<?>> findGames(Predicate<Game<?>> gameFilter);

	static class Util {

		public static void storePlayers(GameHome home, Player... players) {
			for (Player player : players)
				home.storePlayer(player);
		}
	}
}
