package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallGame;
import org.oxtail.game.billiards.nineball.model.NineBallMove;
import org.oxtail.game.billiards.nineball.model.NineBallMoveEvaluator;
import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;

/**
 * Base state for Nine Ball
 * 
 * @author liam knox
 */
public class AbstractNineBallState extends
		AbstractGameState<NineBallGame, NineBallMove,NineBallTable> {

	private NineBallMoveEvaluator evaluator;
	
	public AbstractNineBallState(
			GameEventContext<NineBallGame, NineBallMove,NineBallTable> context) {
		super(context);
		evaluator = new NineBallMoveEvaluator(context.getGame().getCurrentPlayingSpace(), context.getMove());
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
		return context.getGame();
	}

	private void setState(AbstractNineBallState state) {
		getGame().setStateId(state.getStateId());
	}

	/**
	 * Player in play wins
	 */
	protected final void doInPlayPlayerWins() {
		setState(new GameOver(context));
	}

	protected final void doFoul() {
		getGame().turnOver();
		setState(new Foul(context));
	}


	/**
	 * continue the break with same player
	 */
	protected final void doContinueBreak() {
		setState(new ShotTaken(context));
	}

	/**
	 * change the player in turn and continue
	 */
	protected final void doPlayerTurnChange() {
		getGame().turnOver();
		setState(new ShotTaken(context));
	}

	protected NineBallMoveEvaluator getNineBallMoveEvaluator() {
		return evaluator;
	}

	@Override
	protected void afterStateExecution() {
		getGame().applyMove(context.getMove());
	}
}
