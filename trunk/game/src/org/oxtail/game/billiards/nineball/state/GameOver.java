package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallGame;
import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.GameEventContext;

public class GameOver extends AbstractNineBallState {

	public GameOver(GameEventContext<NineBallTable,NineBallGame> context) {
		super(context);
	}

	
}
