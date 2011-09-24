package org.oxtail.game.billiards.simplepool.state;

import org.oxtail.game.billiards.simplepool.model.SimplePoolMove;
import org.oxtail.game.event.GameEvent;
import org.oxtail.game.model.Player;
import org.oxtail.game.server.event.GameEventHelper;

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
		GameEventHelper helper = new GameEventHelper(event);
		SimplePoolMove move = new SimplePoolMove(player);
		for (int i = 1; i < 10; ++i) {
			String ballState = helper.getString("shot.ball." + i + ".state");
		}
		return move;
	}

}
