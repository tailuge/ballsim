package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallGame;
import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.GameEventContext;

/**
 * Initial state before the Game starts
 * 
 * @author liam knox
 */
public class NewRack extends AbstractNineBallState {

	public NewRack(GameEventContext<NineBallTable,NineBallGame> context) {
		super(context);
	}

	/**
	 * Foul logic would be different from normal shot. i.e. valid to hit all
	 * balls, though need to 3 balls to hit cushion
	 */
	@Override
	protected void shotTaken() {
		if (isStraightWinOffBreak())
			doInPlayPlayerWins();
		else if (isFoul())
			doFoul();
		else if (isAnyBallPotted())
			doContinueBreak();
		else
			doPlayerTurnChange();
	}

	private boolean isFoul() {
		return isCueBallPotted() || !isNextBallHitFirst();
	}

	private boolean isStraightWinOffBreak() {
		return isNineBallPotted() && isCueBallOnTable() && isNextBallHitFirst();
	}

}
