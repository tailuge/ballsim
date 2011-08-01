package org.oxtail.game.billiards.nineball.model;

import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;

public class NineBallGame extends Game<NineBallTable>{

	public final Player getPlayerOne() {
		return getPlayer(0);
	}
	
	public final Player getPlayerTwo() {
		return getPlayer(1);
	}
	
	/**
	 * Called when the current players turn is over
	 */
	public final void turnOver() {
		if(getInPlay() == getPlayerOne())
			setInPlay(getPlayerTwo());
		else if(getInPlay() == getPlayerTwo())
			setInPlay(getPlayerOne());
		else 
			throw new IllegalStateException("Can`t swap players for game! "+this);
	}
	
}
