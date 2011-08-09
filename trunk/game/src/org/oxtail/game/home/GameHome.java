package org.oxtail.game.home;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;

/**
 * Persistence access for Game and related entities
 * 
 * @author liam knox
 */
public interface GameHome {

	void storePlayer(Player player);

	void findPlayer(String playerAlias);

	Game<? extends PlayingSpace> findGame(String gameId);

	void store(Game<? extends PlayingSpace> game);
}
