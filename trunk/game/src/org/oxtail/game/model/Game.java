package org.oxtail.game.model;

import java.util.List;

/**
 * Top level Game, A unique instance of a games
 *
 */
public abstract class Game<T extends PlayingSpace> {

	private List<Player> players;
	private GameVersion version;
	private T currentPlayingSpace;


}