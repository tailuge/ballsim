package org.oxtail.game.billiards.nineball.state;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.state.AbstractStatemachine;

public class NineBallStatemachine extends AbstractStatemachine {

	@Override
	public void execute(GameEvent gameEvent) {
		NineballAction action = NineballAction.valueOf(gameEvent.getAction());
	}

}
