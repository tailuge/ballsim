package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallGame;
import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;

/**
 * Base state for Nine Ball
 * 
 * @author liam knox
 */
public class AbstractNineBallState extends
		AbstractGameState<NineBallTable, NineBallGame> {

	public AbstractNineBallState(
			GameEventContext<NineBallTable, NineBallGame> context) {
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

	protected final boolean isCueBallPotted() {
		return getTable().isCueBallPotted();
	}

	protected final boolean isAnyBallPotted() {
		return getTable().isAnyBallPotted();
	}

	private NineBallTable getTable() {
		return getGame().getCurrentPlayingSpace();
	}

	protected final NineBallGame getGame() {
		return getContext().getGame();
	}

	private void setState(AbstractNineBallState state) {
		getGame().setStateId(state.getStateId());
	}

	/**
	 * Player in play wins
	 */
	protected final void doInPlayPlayerWins() {
		setState(new GameOver(getContext()));
	}

	protected final void doFoul() {
		getGame().turnOver();
		setState(new Foul(getContext()));
	}


	/**
	 * continue the break with same player
	 */
	protected final void doContinueBreak() {
		setState(new ShotTaken(getContext()));
	}

	/**
	 * change the player in turn and continue
	 */
	protected final void doPlayerTurnChange() {
		getGame().turnOver();
		setState(new ShotTaken(getContext()));
	}

}
