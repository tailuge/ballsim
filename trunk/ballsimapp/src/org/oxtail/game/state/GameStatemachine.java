package org.oxtail.game.state;

import org.motion.ballsimapp.shared.GameEvent;

public interface GameStatemachine {

	void execute(GameEvent gameEvent);

}
