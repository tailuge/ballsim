package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallGame;
import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.GameEventContext;

public class ShotTaken extends AbstractNineBallState {

	public ShotTaken(GameEventContext<NineBallTable,NineBallGame> context) {
		super(context);
	}

}
