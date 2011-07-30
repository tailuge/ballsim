package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.GameEventContext;

public class ShotTaken extends AbstractNineBallState {

	public ShotTaken(GameEventContext<NineBallTable> context) {
		super(context);
	}

}
