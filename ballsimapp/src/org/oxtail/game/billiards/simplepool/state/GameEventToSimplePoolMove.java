package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.server.event.GameEventHelper;

import com.google.common.base.Function;

/**
 * Translate a {@link GameEvent} into a {@link SimplePoolMove}
 */
public class GameEventToSimplePoolMove implements
		Function<GameEvent, SimplePoolMove> {

	@Override
	public SimplePoolMove apply(GameEvent event) {
		GameEventHelper helper = new GameEventHelper(event);
		SimplePoolMove move = new SimplePoolMove();
		String ballsPotted = helper.getString("game.shot.ballspotted");
		if (!ballsPotted.isEmpty()) {
			for (String ball : ballsPotted.split(",")) {
				move.setBallAsPotted(Integer.valueOf(ball));
			}
		}
		return move;
	}

}
