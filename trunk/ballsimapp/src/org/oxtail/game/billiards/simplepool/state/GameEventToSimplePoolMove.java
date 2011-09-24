package org.oxtail.game.billiards.simplepool.state;

import org.motion.ballsimapp.shared.GameEvent;
import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.model.Player;

import com.google.common.base.Function;

/**
 * Translate a {@link GameEvent} into a {@link SimplePoolMove}
 */
public class GameEventToSimplePoolMove implements
		Function<GameEvent, SimplePoolMove> {

	private final Player player;

	public GameEventToSimplePoolMove(Player player) {
		this.player = player;
	}

	@Override
	public SimplePoolMove apply(GameEvent event) {
		SimplePoolMove move = new SimplePoolMove(player);
		return move;
	}

}
