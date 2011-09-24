package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.billiards.nineball.model.NineBallGame;
import org.oxtail.game.billiards.nineball.model.NineBallMove;
import org.oxtail.game.billiards.nineball.model.NineBallTable;
import org.oxtail.game.state.GameEventContext;

public class Foul extends AbstractNineBallState{

	public Foul(GameEventContext<NineBallGame,NineBallMove,NineBallTable> context) {
		super(context);
	}
	
}
