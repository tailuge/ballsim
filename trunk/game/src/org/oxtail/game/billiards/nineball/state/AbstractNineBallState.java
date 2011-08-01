package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.model.Game;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;

/**
 * Base state for Nine Ball
 * 
 * @author liam knox
 */
public class AbstractNineBallState extends AbstractGameState<NineBallTable> {

	public AbstractNineBallState(GameEventContext<NineBallTable> context) {
		super(context);
	}

	/**
	 * When a vanilla shot is played
	 */
	protected void shotTaken() {
		throw new UnsupportedOperationException();
	}

	/**
	 * When the player opts to pick up post foul
	 */
	protected void ballPickedUp() {
		throw new UnsupportedOperationException();
	}

	/**
	 * When the player puts in the other post foul
	 */
	protected void playerPutIn() {
		throw new UnsupportedOperationException();
	}

	/**
	 * If we have definitely committed a foul in any state i.e. chinned a ball
	 * off the table
	 */
	protected final boolean isDefiniteFoul() {
		return false;
	}

	protected boolean isNineBallPotted() {
		return getTable().isNineBallPotted();
	}

	protected boolean isCueBallOnTable() {
		return getTable().isCueBallOnTable();
	}

	protected boolean isCueBallPotted() {
		return getTable().isCueBallPotted();
	}

	
	private NineBallTable getTable() {
		return getGame().getCurrentPlayingSpace();
	}

	protected Game<NineBallTable> getGame() {
		return getContext().getGame();
	}

	/**
	 * Player in play wins
	 */
	protected final void doInPlayPlayerWins() {
		getGame().setState(new GameOver(getContext()));
	}
}
