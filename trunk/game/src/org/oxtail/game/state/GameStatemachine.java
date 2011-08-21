package org.oxtail.game.state;

import org.oxtail.game.event.GameEvent;

public interface GameStatemachine {

	public void execute(GameEvent gameEvent);

}
