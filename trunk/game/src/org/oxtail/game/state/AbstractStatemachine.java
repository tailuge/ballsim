package org.oxtail.game.state;

import org.oxtail.game.event.GameEvent;

public abstract class AbstractStatemachine {

	public abstract void execute(GameEvent gameEvent);

}
