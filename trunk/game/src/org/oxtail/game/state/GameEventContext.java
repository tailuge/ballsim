package org.oxtail.game.state;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.PlayerMove;
import org.oxtail.game.model.PlayingSpace;

public class GameEventContext<T extends PlayingSpace> {

	private PlayerMove<PlayingSpace> inplay;
	private Game game;

}
