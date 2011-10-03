package org.oxtail.game.billiards.simplepool.home;

import org.oxtail.game.home.PlayerNotFoundException;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
import org.oxtail.game.model.Player;

/**
 * An {@link InMemoryGameHome} where player are created on the fly
 */
public class CreatePlayerInMemoryHome extends InMemoryGameHome {

	@Override
	public Player findPlayer(String playerAlias) {
		try {
			return super.findPlayer(playerAlias);
		} catch (PlayerNotFoundException e) {
			Player player = new Player(playerAlias);
			player.setState("LoggedIn");
			storePlayer(player);
			return player;
		}
	}

}
