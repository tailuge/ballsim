package org.oxtail.game.state;

import org.motion.ballsimapp.shared.GameEvent;

public interface GameStatemachine {

	public void execute(GameEvent gameEvent);

}
