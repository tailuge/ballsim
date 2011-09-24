package org.oxtail.game.billiards.nineball.model;

import org.oxtail.game.billiards.nineball.state.NewRack;
import org.oxtail.game.model.Game;
import org.oxtail.game.model.Player;
import org.oxtail.game.model.StateId;

public class NineBallGame extends Game<NineBallTable> {

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
		if (inPlay() == getPlayerOne())
			setInPlay(getPlayerTwo());
		else if (inPlay() == getPlayerTwo())
			setInPlay(getPlayerOne());
		else
			throw new IllegalStateException("Can`t swap players for game! "
					+ this);
	}

	/**
	 * We need to apply the move to the game at this point to update it
	 */
	public void applyMove(NineBallMove move) {
		// TODO
	}

	
}
