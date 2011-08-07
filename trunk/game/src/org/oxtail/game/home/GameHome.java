package org.oxtail.game.home;

import org.oxtail.game.model.Player;

public interface GameHome {

	void storePlayer(Player player);

	void lookupPlayer(String playerAlias);

}
